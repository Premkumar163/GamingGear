//package com.GamingGear.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.GamingGear.dto.ForgotPasswordRequest;
//import com.GamingGear.service.ForgotPasswordService;
//
//@RestController
//@RequestMapping("auth")
//public class ForgotPasswordController {
//	
//	@Autowired
// private ForgotPasswordService forgotPasswordService;
//	
//	// ================= SEND MAGIC LINK =================
//    @PostMapping("/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
//        forgotPasswordService.ForgotPassword(request);
//        return ResponseEntity.ok("OTP / Magic link sent to email");
//    }
//	
//    
//    @GetMapping("/reset-password/validate")
//    public ResponseEntity<String> validateToken(@RequestParam String token) {
//
//        boolean isValid = forgotPasswordService.validateToken(token);
//
//        if (!isValid) {
//            return ResponseEntity
//                    .badRequest()
//                    .body("Invalid or expired token");
//        }
//
//        return ResponseEntity.ok("Token is valid");
//    }
//
//
//}


package com.GamingGear.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GamingGear.dto.ForgotPasswordRequest;
import com.GamingGear.dto.ResetPasswordRequest;
import com.GamingGear.service.ForgotPasswordService;

@RestController
@RequestMapping("/auth")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    // ================= SEND MAGIC LINK =================
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        forgotPasswordService.ForgotPassword(request);
        return ResponseEntity.ok("OTP / Magic link sent to email");
    }

    // ================= VALIDATE TOKEN =================
    @GetMapping("/reset-password/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {

        try {
            boolean isValid = forgotPasswordService.validateToken(token);

            if (!isValid) {
                return ResponseEntity.status(403).body("Invalid or expired token");
            }

            return ResponseEntity.ok("Token is valid");

        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
 // ================= RESET =================
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        forgotPasswordService.resetPassword(request);
        return ResponseEntity.ok("Password reset successful");
    }
}
