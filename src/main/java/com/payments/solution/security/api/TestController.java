package com.payments.solution.security.api;

import javax.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> test() {

        return ResponseEntity.ok("Authenticated");
    }
}
