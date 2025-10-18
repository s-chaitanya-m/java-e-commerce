package chaitanya.shinde.store.controllers;

import chaitanya.shinde.store.dtos.LoginRequest;
import chaitanya.shinde.store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

//        var user = userRepository.findByEmail(request.getEmail()).orElse(null);
//        if (user == null)
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User Not Found!."));
//
//        if (!passwordEncoder.matches(request.getPassword(),user.getPassword() ))
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Password Incorrect!."));

        return ResponseEntity.ok("Login success");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
