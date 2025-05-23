package com.jee.ebanking_backend.services;

import com.jee.ebanking_backend.dtos.JwtAuthenticationResponse;
import com.jee.ebanking_backend.dtos.RefreshTokenRequest;
import com.jee.ebanking_backend.dtos.SignInRequest;
import com.jee.ebanking_backend.entities.User;
import com.jee.ebanking_backend.enums.Role;
import com.jee.ebanking_backend.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }


    public UserDetailsService userDetailsService(){
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public User register(User user) {
        var existUser = userRepository.findByEmail(user.getEmail());
        if (existUser != null) {
            throw new IllegalArgumentException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }



    public JwtAuthenticationResponse signin(SignInRequest signinRequest) {
        System.out.println("zebi :: " + signinRequest.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword())
        );


        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("Invalid Username or Password"));
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }


    public void saveUser(User user) {
        var existUser = userRepository.findByEmail(user.getUsername());
        if (existUser != null){
            userRepository.save(user);
        }

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String email = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(email).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user) && user!=null){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }


}
