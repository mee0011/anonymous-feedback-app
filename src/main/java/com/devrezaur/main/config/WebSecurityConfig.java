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

                        .requestMatchers(
                                "/", "/home", "/search", "/login", "/register",
                                "/css/**", "/js/**", "/h2-console/**"
                        ).permitAll()


                        .requestMatchers("/feedback", "/feedback/{feedbackId}/edit", "/user-dashboard").hasRole("USER")


                        .requestMatchers("/feedback/{feedbackId}/delete").hasAnyRole("ADMIN", "USER")


                        .requestMatchers("/admin/**").hasRole("ADMIN")


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