package com.example

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.*

@RestController
class DatabaseStatusController {

    @PersistenceContext
    def EntityManager entityManager;

    @GetMapping("/database-status")
    def ResponseEntity<String> getDatabaseStatus() {
        try {
            entityManager.createNativeQuery("SELECT 1").getResultList();
            return ResponseEntity.ok("Baza danych jest aktywna");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error checking database status: " + e.getMessage());
        }
    }
}

