package com.example.security.config;

import com.example.security.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity

public class SecurityConfig {
    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(userService())
                .passwordEncoder(passwordEncoder());

        http.csrf(AbstractHttpConfigurer :: disable);

        http.formLogin(fl -> fl.loginProcessingUrl("/auth")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/sign-in")
                .defaultSuccessUrl("/" , true)
                .failureUrl("/sign-in?error"));

    http.logout(lg -> lg.logoutUrl("/logout")
            .logoutSuccessUrl("/sign-in"));
    return http.build();
    }
}
