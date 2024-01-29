package com.example

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HealthCheckController {

    @GetMapping("/")
    def ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Spring Boot API jest aktywne");
    }
}
