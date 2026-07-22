package com.acunsoz.ecommerce_backend.controller;

import com.acunsoz.ecommerce_backend.model.dto.*;
import com.acunsoz.ecommerce_backend.service.AuthenticationService;
import com.acunsoz.ecommerce_backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/api/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Sunucu 10 Numara Çalışıyor!");
    }

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

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        // 1. Token'ın içinden kullanıcı adını çıkar
        String username = jwtService.extractUserName(refreshToken);

        if (username != null) {
            // 2. Veritabanından kullanıcı detaylarını yükle
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 3. Refresh token'ın hala geçerli olup olmadığını kontrol et
            if (jwtService.isTokenValid(refreshToken, userDetails)) {

                // 4. SİHİRLİ AN: Yeni bir Access Token üret (İstersek refresh'i de yenileyebiliriz)
                String newAccessToken = jwtService.generateToken(userDetails);

                // 5. Flutter'a yeni token'ları fırlat
                return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken));
            }
        }

        // Eğer token sahteyse veya süresi dolmuşsa (7 gün geçmişse)
        return ResponseEntity.status(401).body("Refresh token geçersiz veya süresi dolmuş. Lütfen tekrar giriş yapın.");
    }


}
