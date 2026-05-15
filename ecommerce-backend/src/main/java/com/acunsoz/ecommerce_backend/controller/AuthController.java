package com.acunsoz.ecommerce_backend.controller;

import com.acunsoz.ecommerce_backend.model.dto.AuthResponse;
import com.acunsoz.ecommerce_backend.model.dto.LoginRequest;
import com.acunsoz.ecommerce_backend.model.dto.RegisterRequest;
import com.acunsoz.ecommerce_backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {

        return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {

        return ResponseEntity.ok(authenticationService.login(request));

    }

}
