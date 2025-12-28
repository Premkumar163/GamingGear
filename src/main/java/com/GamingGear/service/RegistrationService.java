//package com.GamingGear.service;
//
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.GamingGear.Exception.OtpValidationException;
//import com.GamingGear.model.Forminputs;
//import com.GamingGear.repository.GamingRepository;
//import com.GamingGear.util.EmailServicereg;
//import com.GamingGear.util.OTPUtil;
//import com.GamingGear.util.PhoneService;
//
//@Service
//public class RegistrationService {
//	
//	@Autowired
//	private GamingRepository gamingRepository;
//	
//	@Autowired
//	private EmailServicereg emailService;
//	
//	@Autowired
//	private RedisTemplate<String, String> redisTemplate;
//	
//	@Autowired
//	private PhoneService phoneService;
//	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
//	public void registerUser(Forminputs forminputs) {
//		//Encoding password before saving
//		
//		forminputs.setPass(passwordEncoder.encode(forminputs.getPass()));
//		
//		forminputs.setStatus("PENDING");
//		gamingRepository.save(forminputs);
//		
//		//Generate OTPs
//		String emailOtp = OTPUtil.generateOTP();
//		String phoneOtp = OTPUtil.generateOTP();
//		
//		String formattedPhone = forminputs.getMobileno().startsWith("+") ? forminputs.getMobileno() : "+91" +forminputs.getMobileno();
//	
//	
//		// Store in Redis TTL 15 min
//		redisTemplate.opsForValue().set("email_otp:" + forminputs.getEmail(), emailOtp, 15, TimeUnit.MINUTES);
//		redisTemplate.opsForValue().set("phone_otp:" + formattedPhone, phoneOtp, 15, TimeUnit.MINUTES);
//
//		
//		//Send OTPs
//		
//		emailService.sendEmail(forminputs.getEmail(), "Verify your email OTP" , "Your OTP" + emailOtp);
//		phoneService.sendOTP(formattedPhone, phoneOtp);
//		
//		  System.out.println("Stored Email OTP key: email_otp:" + forminputs.getEmail() + ", OTP: " + emailOtp);
//		System.out.println("Phone Otp Stored in radis : "+phoneOtp + "for key : phone_otp:"+formattedPhone);
//		
//		
//		System.out.println("Email OTP: " + emailOtp);
//		System.out.println("Phone OTP: " + phoneOtp);
//
//		
//	}
//	
//	  public boolean verifyEmailOTP(String email, String otp) {
//		  
//		  String key = "email_otp:"+email;
//		  String redisOtp = redisTemplate.opsForValue().get(key);
//		  if (redisOtp !=null && redisOtp.equals(otp) ) {
//			redisTemplate.delete(key);
//			Forminputs forminputs = gamingRepository.findByEmail(email).orElseThrow();
//			forminputs.setEmailVerified(true);
//			activateIfReady(forminputs);
//			gamingRepository.save(forminputs);
//			
//			  System.out.println("radis email otp"+redisOtp);
//			return true;
//		}
//		  
//		  
//		  return false;
//		
//		
//
//	}
//	  
//	  public boolean verifyPhoneOTP(String phone, String otp) {
//
//	        String formattedPhone = phone.startsWith("+") ? phone : "+91" + phone;
//	        String key = "phone_otp:" + formattedPhone;
//	        String redisOtp = redisTemplate.opsForValue().get(key);
//
//	        if (redisOtp != null && redisOtp.equals(otp)) {
//	            redisTemplate.delete(key);
//
//	            Forminputs forminputs = gamingRepository.findByEmail(formattedPhone).orElseThrow();
//	            forminputs.setPhoneVerified(true);
//	            activateIfReady(forminputs);
//	            gamingRepository.save(forminputs);
//	            
//	            System.out.println("radis phone otp"+redisOtp);
//
//	            return true;
//	        }
//	        return false;
//	    }
//	  
//	  private void activateIfReady(Forminputs forminputs) {
//		
//		  if (forminputs.isEmailVerified() && forminputs.isPhoneVerified()) {
//			  forminputs.setStatus("ACTIVE");
//		}
//	}
//	  
//	  
//
//}


package com.GamingGear.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.GamingGear.Exception.OtpValidationException;
import com.GamingGear.model.Forminputs;
import com.GamingGear.repository.GamingRepository;
import com.GamingGear.util.EmailServicereg;
import com.GamingGear.util.OTPUtil;
import com.GamingGear.util.PhoneService;

@Service
public class RegistrationService {

    @Autowired
    private GamingRepository gamingRepository;

    @Autowired
    private EmailServicereg emailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= REGISTER USER =================
    public void registerUser(Forminputs forminputs) {

        // Normalize phone number
        String formattedPhone = normalizePhone(forminputs.getMobileno());
        forminputs.setMobileno(formattedPhone);

        // Encode password
        forminputs.setPass(passwordEncoder.encode(forminputs.getPass()));

        // Initial status
        forminputs.setStatus("PENDING");
        forminputs.setEmailVerified(false);
        forminputs.setPhoneVerified(false);

        gamingRepository.save(forminputs);

        // Generate OTPs
        String emailOtp = OTPUtil.generateOTP();
        String phoneOtp = OTPUtil.generateOTP();

        // Store OTPs in Redis (15 min TTL)
        redisTemplate.opsForValue()
                .set("email_otp:" + forminputs.getEmail(), emailOtp, 30, TimeUnit.MINUTES);

        
        
        
        redisTemplate.opsForValue()
                .set("phone_otp:" + formattedPhone, phoneOtp, 30, TimeUnit.MINUTES);

        // Send OTPs
        emailService.sendEmail(
                forminputs.getEmail(),
                "Verify your email OTP",
                "Your OTP is: " + emailOtp
        );

        phoneService.sendOTP(formattedPhone, phoneOtp);

        // Debug logs
        System.out.println("EMAIL OTP STORED → email_otp:" + forminputs.getEmail() + " = " + emailOtp);
        System.out.println("PHONE OTP STORED → phone_otp:" + formattedPhone + " = " + phoneOtp);
    }

    // ================= VERIFY EMAIL OTP =================
    public boolean verifyEmailOTP(String email, String otp) {

        email = email == null ? null : email.trim();
        otp = otp == null ? null : otp.trim();

        String key = "email_otp:" + email;
        String redisOtp = redisTemplate.opsForValue().get(key);

        System.out.println(
            "VERIFY EMAIL OTP → key=" + key +
            ", redisOtp=" + redisOtp +
            ", inputOtp=" + otp
        );

        if (redisOtp == null) {
            throw new OtpValidationException("Email OTP expired or not found");
        }

        if (!redisOtp.trim().equals(otp)) {
            throw new OtpValidationException("Invalid Email OTP");
        }

        redisTemplate.delete(key);

        Forminputs user = gamingRepository.findByEmail(email)
                .orElseThrow(() -> new OtpValidationException("User not found"));

        user.setEmailVerified(true);
        activateIfReady(user);
        gamingRepository.save(user);

        return true;
    }


    // ================= VERIFY PHONE OTP =================
    public boolean verifyPhoneOTP(String phone, String otp) {

        // ✅ Trim input to remove extra spaces
        phone = phone == null ? null : phone.trim();
        otp = otp == null ? null : otp.trim();

        String formattedPhone = normalizePhone(phone);
        String key = "phone_otp:" + formattedPhone;

        String redisOtp = redisTemplate.opsForValue().get(key);


String testRead = redisTemplate.opsForValue().get(key);
System.out.println("REDIS WRITE TEST → " + key + " = " + testRead);
        
        System.out.println(
            "VERIFY PHONE OTP → key=" + key +
            ", redisOtp=" + redisOtp +
            ", inputOtp=" + otp
        );

        if (redisOtp == null) {
            throw new OtpValidationException("Phone OTP expired or not found");
        }

        if (!redisOtp.trim().equals(otp)) {
            throw new OtpValidationException("Invalid Phone OTP");
        }

        redisTemplate.delete(key);

        Forminputs user = gamingRepository.findByMobileno(formattedPhone)
                .orElseThrow(() -> new OtpValidationException("User not found for phone"));

        user.setPhoneVerified(true);
        activateIfReady(user);
        gamingRepository.save(user);

        return true;
    }

    
    private void activateIfReady(Forminputs user) {

        if (user.isEmailVerified() && user.isPhoneVerified()) {
            user.setStatus("ACTIVE");
        }
        else if (user.isEmailVerified()) {
            user.setStatus("EMAIL_VERIFIED");
        }
        else if (user.isPhoneVerified()) {
            user.setStatus("PHONE_VERIFIED");
        }
        else {
            user.setStatus("PENDING");
        }
    }

    

    // ================= ACTIVATE ACCOUNT =================
//    private void activateIfReady(Forminputs user) {
//        if (user.isEmailVerified() && user.isPhoneVerified()) {
//            user.setStatus("ACTIVE");
//        }
//    }

   //  ================= PHONE NORMALIZER =================
    private String normalizePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new OtpValidationException("Phone number is required");
        }
        return phone.startsWith("+") ? phone : "+91" + phone;
  }
}
