package com.aethercubix.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

      @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // permite todo sin autenticación
            )
            .csrf(csrf -> csrf.disable()) // desactiva CSRF para facilitar pruebas durante desarrollo
            .formLogin(form -> form.disable()); // desactiva login

        return http.build();
    }



    
}
