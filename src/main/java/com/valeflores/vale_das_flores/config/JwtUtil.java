package com.valeflores.vale_das_flores.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.valeflores.vale_das_flores.services.exceptions.TokenException;

@Component
public class JwtUtil {
	
	 private String secretKey = "mySecretKey";
	 
	// Método para gerar o token JWT
	    public String generateToken(String username, String role) {
	        Algorithm algorithm = Algorithm.HMAC256(secretKey);  // Usando HMAC256 para assinatura

	        return JWT.create()
	                .withSubject(username)  // O subject é o identificador do usuário
	                .withClaim("role", role)
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
	    
	 // Verifica se o token está expirado ou inválido
	    public boolean isTokenExpired(String token) {
	        try {
	            Date expirationDate = JWT.require(Algorithm.HMAC256(secretKey))
	                    .build()
	                    .verify(token)
	                    .getExpiresAt();
	            
	            return expirationDate.before(new Date());
	        } catch (JWTVerificationException e) {
	            throw new TokenException("Invalid token. Please login again.");
	        }
	    }

	    
	 // Valida o token JWT
	    public boolean validateToken(String token, String username) {
	        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
	    }

}
