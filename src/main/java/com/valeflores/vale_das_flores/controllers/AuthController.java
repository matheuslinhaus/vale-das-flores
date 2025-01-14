package com.valeflores.vale_das_flores.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valeflores.vale_das_flores.config.JwtUtil;
import com.valeflores.vale_das_flores.dto.AuthResponseDTO;
import com.valeflores.vale_das_flores.dto.LoginRequestDTO;
import com.valeflores.vale_das_flores.dto.UserCreateDTO;
import com.valeflores.vale_das_flores.entities.User;
import com.valeflores.vale_das_flores.mappers.UserMapper;
import com.valeflores.vale_das_flores.services.LoginAttemptService;
import com.valeflores.vale_das_flores.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/auth/")
public class AuthController {

	@Autowired
	private UserService service;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@Operation(summary = "New User", description = "Create a new user", tags = "User", responses = {
			@ApiResponse(responseCode = "201", description = "Successfully inserted", content = @Content(mediaType = "application/json", schema = @Schema(example = ""))) })
	@PostMapping(value = "/register")
	public ResponseEntity<AuthResponseDTO> insert(@RequestBody UserCreateDTO userCreateDTO) {
		User user = UserMapper.toEntity(userCreateDTO);
		user = service.insert(user);
		String token = jwtUtil.generateToken(user.getEmail());
		return ResponseEntity.ok(new AuthResponseDTO(token));
	}

	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		if (loginAttemptService.isBlocked(loginRequestDTO.getEmail())) {
			return ResponseEntity.status(429).body("Too many login attempts. Please try again later.");
		}

		boolean isAuthenticated = service.authenticateUser(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

		if (isAuthenticated) {
			String token = jwtUtil.generateToken(loginRequestDTO.getEmail());
			loginAttemptService.loginSucceeded(loginRequestDTO.getEmail());
			return ResponseEntity.ok(new AuthResponseDTO(token));
		}

		loginAttemptService.loginFailed(loginRequestDTO.getEmail());
		return ResponseEntity.status(401).body("Unauthorized");
	}
}