package com.jee.ebanking_backend.web;

import com.jee.ebanking_backend.dtos.JwtAuthenticationResponse;
import com.jee.ebanking_backend.dtos.RefreshTokenRequest;
import com.jee.ebanking_backend.dtos.SignInRequest;
import com.jee.ebanking_backend.entities.User;
import com.jee.ebanking_backend.repositories.UserRepository;
import com.jee.ebanking_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
        JwtAuthenticationResponse response = userService.signin(signInRequest);
        System.out.println("Response: " +response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        JwtAuthenticationResponse response = userService.refreshToken(refreshTokenRequest);
        if (response == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        return ResponseEntity.ok(user);
    }
}
