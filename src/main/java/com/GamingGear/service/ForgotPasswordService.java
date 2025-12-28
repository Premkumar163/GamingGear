////package com.GamingGear.service;
////
////import java.util.UUID;
////import java.util.concurrent.TimeUnit;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.redis.core.RedisTemplate;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.stereotype.Service;
////
////import com.GamingGear.dto.ForgotPasswordRequest;
////import com.GamingGear.repository.GamingRepository;
////import com.GamingGear.util.EmailServicereg;
////
////@Service
////public class ForgotPasswordService {
////
////	@Autowired
////	private GamingRepository gamingRepository;
////	
////	@Autowired
////	private RedisTemplate<String, String> redisTemplate;
////	
////	@Autowired
////	private EmailServicereg emailService;
////	
////	@Autowired
////	private PasswordEncoder passwordEncoder;
////	
////	
////	//Step 1 Send Magic link in user
////	public void ForgotPassword(ForgotPasswordRequest request) {
////
////	    String email = request.getEmail().trim();
////
////	    gamingRepository.findByEmail(email)
////	        .ifPresent(user -> {
////
////	            String token = UUID.randomUUID().toString();
////	            String redisKey = "reset_token:" + email;
////
////	            redisTemplate.opsForValue()
////	                    .set(redisKey, token, 30, TimeUnit.MINUTES);
////
////	            String resetLink =
////	                    "http://localhost:8080/auth/reset-password?email=" + email +
////	                    "&token=" + token;
////
////	            // ✅ THIS IS THE PART YOU ASKED ABOUT
////	            emailService.sendEmail(
////	                    email,
////	                    "Reset Your Password",
////	                    "Click the link to reset your password:\n" + resetLink
////	            );
////
////	            System.out.println("RESET TOKEN STORED → " + redisKey + " = " + token);
////	        });
////	}
////	public boolean validateToken(String token) {
////
////	    if (token == null) {
////	        throw new RuntimeException("Token cannot be null");
////	    }
////
////	    token = token.trim();  // trim leading/trailing spaces
////
////	    String redisKey = "RESET_PASSWORD_TOKEN:" + token;
////	    
////	    String email = redisTemplate.opsForValue().get(redisKey);
////	    
////	    if (email == null) {
////	        throw new RuntimeException("Invalid or expired reset link");
////	    }
////
////	    return true;   
////	}
////
////	
////}
//
//

package com.GamingGear.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.GamingGear.dto.ForgotPasswordRequest;
import com.GamingGear.dto.ResetPasswordRequest;
import com.GamingGear.model.Forminputs;
import com.GamingGear.repository.GamingRepository;
import com.GamingGear.util.EmailServicereg;

@Service
public class ForgotPasswordService {

    @Autowired
    private GamingRepository gamingRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailServicereg emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Step 1: Send Magic link to user
    public void ForgotPassword(ForgotPasswordRequest request) {

        String email = request.getEmail().trim();

        gamingRepository.findByEmail(email)
                .ifPresent(user -> {

                    String token = UUID.randomUUID().toString();
                    String redisKey = "reset_token:" + token; // store token as key

                    redisTemplate.opsForValue()
                            .set(redisKey, email, 30, TimeUnit.MINUTES); // store email as value

                    String resetLink =
                            "http://localhost:5173/auth/reset-password/validate?token=" + token;

                    emailService.sendEmail(
                            email,
                            "Reset Your Password",
                            "Click the link to reset your password:\n" + resetLink
                    );

                    System.out.println("RESET TOKEN STORED → " + redisKey + " = " + email);
                });
    }

    // Step 2: Validate token
    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token cannot be null or empty");
        }

        token = token.trim();  
        String redisKey = "reset_token:" + token;

        String email = redisTemplate.opsForValue().get(redisKey);

        if (email == null) {
            throw new RuntimeException("Invalid or expired reset link");
        }

        return true;
    }
    
    
    // Step 3: Reset Password
    public void resetPassword(ResetPasswordRequest request) {

        String redisKey = "reset_token:" + request.getToken();
        String email = redisTemplate.opsForValue().get(redisKey);

        if (email == null) {
            throw new RuntimeException("Invalid or expired reset link");
        }

        // Fetch user by email
        Forminputs user = gamingRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update password
        user.setPass(passwordEncoder.encode(request.getNewPassword()));
        gamingRepository.save(user);

        // Remove token from Redis
        redisTemplate.delete(redisKey);
    }

}




