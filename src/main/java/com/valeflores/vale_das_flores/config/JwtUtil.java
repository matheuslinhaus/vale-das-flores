package com.valeflores.vale_das_flores.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtil {
	
	 private String secretKey = "mySecretKey";
	 
	// Método para gerar o token JWT
	    public String generateToken(String username) {
	        Algorithm algorithm = Algorithm.HMAC256(secretKey);  // Usando HMAC256 para assinatura

	        return JWT.create()
	                .withSubject(username)  // O subject é o identificador do usuário
	                .withIssuedAt(new Date())  // Data de emissão
	                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Expira em 10 horas
	                .sign(algorithm);  // Assinando o token
	    }
	    
	 // Método para extrair o username do token
	    public String extractUsername(String token) {
	        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
	                .build()
	                .verify(token);
	        return decodedJWT.getSubject();
	    }
	    
	 // Verifica se o token está expirado
	    public boolean isTokenExpired(String token) {
	        Date expirationDate = JWT.require(Algorithm.HMAC256(secretKey))
	                .build()
	                .verify(token)
	                .getExpiresAt();
	        return expirationDate.before(new Date());
	    }
	    
	 // Valida o token JWT
	    public boolean validateToken(String token, String username) {
	        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
	    }

}