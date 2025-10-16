package org.global.Selena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

// --- 1. Main Spring Boot Application Class ---
@SpringBootApplication
@RestController // The main application class also acts as the controller
public class SelenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelenaApplication.class, args);
    }

    // --- 2. Controller Method for /login ---
    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        
        final String EXPECTED_USERNAME = "alice";
        final String EXPECTED_PASSWORD = "secret";

        // Check credentials
        if (EXPECTED_USERNAME.equals(request.username()) && EXPECTED_PASSWORD.equals(request.password())) {
            // Success: Return LoginResponse with a dummy token
            String token = "dummy-auth-token-12345";
            LoginResponse response = new LoginResponse(token);
            // HTTP 200 OK
            return ResponseEntity.ok(response);
        } else {
            // Failure: Return ErrorResponse
            ErrorResponse error = new ErrorResponse("Invalid username or password.");
            // HTTP 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
    
    // --- 3. Data Structures (Records for simplicity) ---
    
    /**
     * Represents the request body for POST /login.
     * Spring will automatically map JSON keys "username" and "password" to these fields.
     */
    public record LoginRequest(String username, String password) {}

    /**
     * Represents the successful response body.
     */
    public record LoginResponse(String token) {}

    /**
     * Represents the error response body.
     */
    public record ErrorResponse(String message) {}
}