package com.devansh.Medical.Invertory.Management.config;

import com.devansh.Medical.Invertory.Management.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth-> auth
                .requestMatchers("/public/**").permitAll()
//                .requestMatchers("/user/**").hasRole("USER")
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated())
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/admin/**").permitAll()
                .anyRequest().permitAll())
                .csrf(c->c.disable())
                .httpBasic(Customizer.withDefaults())
        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
