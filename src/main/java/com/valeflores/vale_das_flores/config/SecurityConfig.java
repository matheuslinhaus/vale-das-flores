package com.valeflores.vale_das_flores.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable()
	        .headers().frameOptions().disable()
	        .and()
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/h2-console/**").permitAll() 
	            .requestMatchers("/api/admin/**").permitAll()  // Permite acesso especificamente ao endpoint do admin
	            .requestMatchers("/api/**").permitAll() 
	            .anyRequest().authenticated() 
	        );
	    return http.build();
	}
}