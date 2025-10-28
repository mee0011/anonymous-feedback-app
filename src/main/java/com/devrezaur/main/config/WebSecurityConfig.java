package com.devrezaur.main.config;

import com.devrezaur.main.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // ... (keep your passwordEncoder() bean) ...
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headerConfigurer -> headerConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(registry -> registry
                        // 1. Public pages (no changes)
                        .requestMatchers(
                                "/", "/home", "/search", "/login", "/register",
                                "/css/**", "/js/**", "/h2-console/**"
                        ).permitAll()

                        // 2. UPDATED: Use hasRole("USER")
                        .requestMatchers("/feedback", "/feedback/{feedbackId}/edit", "/user-dashboard").hasRole("USER")

                        // 3. UPDATED: Use hasAnyRole("ADMIN", "USER")
                        // This allows BOTH users and admins to delete
                        .requestMatchers("/feedback/{feedbackId}/delete").hasAnyRole("ADMIN", "USER")

                        // 4. UPDATED: Use hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 5. All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/login")
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/dashboard", true))
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true"))
                .userDetailsService(customUserDetailsService)
                .build();
    }
}