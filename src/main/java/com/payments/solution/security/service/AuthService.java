package com.payments.solution.security.service;

import com.payments.solution.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateJwt(String username, String password) {

        UserDetails user = userService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, user.getPassword())) {

            return jwtUtil.generateToken(user);
        } else {

            throw new UsernameNotFoundException(username);
        }
    }
}
