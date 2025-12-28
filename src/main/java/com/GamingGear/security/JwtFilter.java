package com.GamingGear.security;

import java.io.IOException;

import org.checkerframework.checker.units.qual.g;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.GamingGear.model.Forminputs;
import com.GamingGear.repository.GamingRepository;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//@Component
//@RequiredArgsConstructor
//public class JwtFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final GamingRepository gamingRepository
//    ;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest req,
//            HttpServletResponse res,
//            FilterChain chain
//    ) throws ServletException, IOException {
//
//        String header = req.getHeader("Authorization");
//
//        if (header != null && header.startsWith("Bearer ")) {
//
//            String token = header.substring(7);
//
//            if (jwtUtil.isValid(token)) {
//
//                String email = jwtUtil.extractEmail(token);
//                Forminputs forminputs = gamingRepository.findByEmail(email).orElse(null);
//
//                if (forminputs != null) {
//
//                    UsernamePasswordAuthenticationToken auth =
//                            new UsernamePasswordAuthenticationToken(
//                                    forminputs,
//                                    null,
//                                    null
//                            );
//
//                    SecurityContextHolder
//                            .getContext()
//                            .setAuthentication(auth);
//                }
//            }
//        }
//
//        chain.doFilter(req, res);
//    }
//}


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final GamingRepository gamingRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        String path = req.getRequestURI();
        System.out.println("JWTFilter: Request path = " + path);

        // Skip JWT check for public endpoints
        if (path.contains("/auth/")) {
            System.out.println("JWTFilter: Skipping auth for public endpoint");
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader("Authorization");
        System.out.println("JWTFilter: Authorization header = " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtUtil.isValid(token)) {
                String email = jwtUtil.extractEmail(token);
                System.out.println("JWTFilter: Token valid for email = " + email);

                Forminputs forminputs = gamingRepository.findByEmail(email).orElse(null);

                if (forminputs != null) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(forminputs, null, null);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("JWTFilter: Authentication set in context");
                }
            } else {
                System.out.println("JWTFilter: Invalid token");
            }
        }

        chain.doFilter(req, res);
    }
}