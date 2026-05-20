package com.acunsoz.ecommerce_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthtionFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.
                        requestMatchers("/api/auth/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/products").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/products").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/orders/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )

                .sessionManagement(session ->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(jwtAuthtionFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();


    }


}
