package com.GamingGear.service;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.GamingGear.dto.LoginRequest;
import com.GamingGear.dto.RegisterRequst;
import com.GamingGear.model.Forminputs;
import com.GamingGear.repository.GamingRepository;
import com.GamingGear.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	 @Autowired
     GamingRepository gamingRepository;
    @Autowired
	 PasswordEncoder passwordEncoder;

    public String register(RegisterRequst req) {
    	
    	if(gamingRepository.existsByEmail(req.getEmail())) {
    		return "Email already registered";
    	}
                

        Forminputs forminputs = Forminputs.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .pass(passwordEncoder.encode(req.getPass()))
                .mobileno(req.getMobileno())
                .build();

        gamingRepository.save(forminputs);

        return "Register successful";
    }
    public String login(LoginRequest req) {

        Forminputs forminputs = gamingRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!passwordEncoder.matches(req.getPass(), forminputs.getPass())) {
            throw new RuntimeException("Invalid Password");
        }

        return "Login sucessfull";
    }

    
}
