package com.GamingGear.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GamingGear.model.Forminputs;
import com.GamingGear.service.RegistrationService;


@RestController
@RequestMapping("/auth")
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	@PostMapping("/register")
	public String register(@RequestBody Forminputs forminputs) {
		registrationService.registerUser(forminputs);
		return"User registered .verify email & phone OTPs";
		
	}
	
	@PostMapping("/verify-email-otp")
	public String verifyEmail(@RequestParam String email,@RequestParam String otp) {
		boolean success = registrationService.verifyEmailOTP(email, otp);
		return success ? "Email verified" : "Invalid or expired OTP";
		
	}
	
	@PostMapping("/verify-phone-otp")
	public String verifyPhone(@RequestParam String phone,@RequestParam String otp) {
		boolean success = registrationService.verifyPhoneOTP(phone, otp);
		return success ? "Phone Verified" : "Invalid or expired OTP";
	}
	
}
