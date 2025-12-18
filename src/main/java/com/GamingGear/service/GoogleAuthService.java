package com.GamingGear.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.GamingGear.model.Forminputs;
import com.GamingGear.repository.GamingRepository;
import com.GamingGear.security.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

	@Autowired
	GamingRepository gamingRepository;
	
	@Autowired
	JwtUtil jwtUtil;
	
	
	  @Value("${google.client-id}")
	  private String googleClientId;
	  
	  public String loginWithGoogle(String idTokenString) {

	        GoogleIdTokenVerifier verifier =
	            new GoogleIdTokenVerifier.Builder(
	                new NetHttpTransport(),
	                GsonFactory.getDefaultInstance()
	            )
	            .setAudience(Collections.singletonList(googleClientId))
	            .build();

	        GoogleIdToken idToken;
	        try {
	            idToken = verifier.verify(idTokenString);
	        } catch (Exception e) {
	            throw new RuntimeException("Invalid Google token");
	        }

	        if (idToken == null) {
	            throw new RuntimeException("Invalid Google token");
	        }
	        GoogleIdToken.Payload payload = idToken.getPayload();

	        String email = payload.getEmail();
	        boolean emailVerified =
	            Boolean.TRUE.equals(payload.getEmailVerified());

	        if (!emailVerified) {
	            throw new RuntimeException("Email not verified by Google");
	        }

	        Forminputs forminputs = gamingRepository.findByEmail(email)
	            .orElseGet(() -> gamingRepository.save(
	                Forminputs.builder()
	                    .email(email)
	                    .pass("Google_pass")           // or provider="GOOGLE"
	                    .build()
	            ));

	        return jwtUtil.generateToken(forminputs.getEmail());
	    }
}
