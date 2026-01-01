package com.GamingGear.controller;

import com.GamingGear.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
        String credential = body.get("credential");

        if (credential == null || credential.isBlank()) {
            return ResponseEntity.badRequest().body("Missing credential");
        }

        String token = googleAuthService.loginWithGoogle(credential);

        return ResponseEntity.ok(Map.of("token", token));
     
    }
}





//@PostMapping("/google-login")
//public String googleLogin(@RequestBody GoogleLoginRequest req) {
//	return googleAuthService.loginWithGoogle(req.getIdToken());
//	
//}