package com.GamingGear.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.GamingGear.dto.LoginRequest;
import com.GamingGear.dto.RegisterRequst;
import com.GamingGear.model.Forminputs;
import com.GamingGear.repository.GamingRepository;
import com.GamingGear.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GamingRepository gamingRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // REGISTER
//    public String register(RegisterRequst req) {
//
//        if (gamingRepository.existsByEmail(req.getEmail())) {
//            return "Email already registered";
//        }
//
//        Forminputs forminputs = Forminputs.builder()
//                .username(req.getUsername())
//                .email(req.getEmail())
//                .pass(passwordEncoder.encode(req.getPass()))
//                .mobileno(req.getMobileno())
//                .build();
//
//        gamingRepository.save(forminputs);
//
//        return "Registered Successfully";
//    }

    // LOGIN
    public String login(LoginRequest req) {

        Forminputs forminputs = gamingRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!passwordEncoder.matches(req.getPass(), forminputs.getPass())) {
            throw new RuntimeException("Invalid Password");
        }

        return jwtUtil.generateToken(forminputs.getEmail());
    }
}
