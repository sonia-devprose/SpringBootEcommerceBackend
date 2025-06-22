package com.ecommerce.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Enables Spring Security's web security features
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api", "/api/**").permitAll() // Allow /api/api paths
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        // Disable CSRF for H2 Console and potentially API POST/PUT/DELETE
                        // For learning, you might want to disable it for all /api/** as well if you're not
                        // handling CSRF tokens in your frontend yet, but this is a security caveat.
                        // For now, just disabling for H2 console is enough for it to work.
                        .ignoringRequestMatchers("/h2-console/**", "/api/**")) // <-- Added /api/** here for easy testing
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // Allow H2 Console to be in a frame
                .httpBasic(withDefaults()); // Use HTTP Basic authentication for any other endpoints (if any)
        return http.build();
    }
}