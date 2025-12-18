package com.GamingGear.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.GamingGear.dto.GoogleLoginRequest;
import com.GamingGear.dto.LoginRequest;
import com.GamingGear.dto.RegisterRequst;
import com.GamingGear.service.AuthService;
import com.GamingGear.service.GoogleAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Gamingcontroller {
      
	@Autowired
     AuthService authService;  // final + constructor injection
	
	@Autowired
	GoogleAuthService googleAuthService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequst requst) {
        return authService.register(requst);
    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
    	return authService.login(req);
		
	}
    
    @PostMapping("/google")
    public String googleLogin(@RequestBody GoogleLoginRequest req) {
		return googleAuthService.loginWithGoogle(req.getIdToken());
		
	}
    
}
