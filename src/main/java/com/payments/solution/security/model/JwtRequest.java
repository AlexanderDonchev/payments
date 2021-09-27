package com.payments.solution.security.model;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;
    private String password;
}
