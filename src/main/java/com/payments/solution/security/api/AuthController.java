package com.payments.solution.security.api;

import com.payments.solution.security.model.JwtRequest;
import com.payments.solution.security.model.JwtResponse;
import com.payments.solution.security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {

        String jwt = authService.generateJwt(jwtRequest.getUsername(), jwtRequest.getPassword());

        return ResponseEntity.ok(JwtResponse.builder()
                .jwt(jwt)
                .build());
    }
}
