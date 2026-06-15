package com.acunsoz.ecommerce_backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
//import java.security.Key;  verifywith ve signwith secretkey bekledikleri için düzelttidi
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    //private static final String SECRET_KEY = "404E635266556A586E327235753878214125442A472D4B6150645367566B5970";

    // Artık şifreyi ve süreleri properties dosyasından güvenle çekiyoruz
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public String generateToken(UserDetails userDetails){

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // Örn: "USER,ADMIN" şeklinde yazar

        return buildToken(userDetails.getUsername(), roles, accessTokenExpiration);
    }
    // 2. UZUN SÜRELİ REFRESH TOKEN (Sadece yeni Access Token almak için kullanılır, rol içermez hafif olmalıdır)
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails.getUsername(), null, refreshTokenExpiration);
    }

    // Ortak Token Üretici Metot
    private String buildToken(String username, String roles, long expiration) {
        var builder = Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey());

        // Refresh token'da rol bilgisine gerek yok, o yüzden kontrol ediyoruz
        if (roles != null) {
            builder.claim("role", roles);
        }

        return builder.compact();
    }

    public String extractUserName(String token){
        return  extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token , UserDetails userDetails)
    {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token,Claims::getExpiration);

    }

    private <T> T extractClaim(String token, Function<Claims , T> claimsResolver) {
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Key donduruyordu SecretKey olarak güncellendi
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
