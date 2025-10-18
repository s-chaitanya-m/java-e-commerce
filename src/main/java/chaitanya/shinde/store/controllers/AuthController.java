package chaitanya.shinde.store.controllers;

import chaitanya.shinde.store.dtos.LoginRequest;
import chaitanya.shinde.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User Not Found!."));

        if (!passwordEncoder.matches(request.getPassword(),user.getPassword() ))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Password Incorrect!."));

        return ResponseEntity.ok("Login success");
    }
}
