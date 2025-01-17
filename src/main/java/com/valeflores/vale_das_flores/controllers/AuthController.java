package com.valeflores.vale_das_flores.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

	@Autowired
	private MessageSource messageSource;

	@Operation(summary = "New User", description = "Create a new user", tags = "User", responses = {
			@ApiResponse(responseCode = "201", description = "Successfully inserted", content = @Content(mediaType = "application/json", schema = @Schema(example = ""))) })
	@PostMapping(value = "/register")
	public ResponseEntity<AuthResponseDTO> insert(@RequestBody UserCreateDTO userCreateDTO) {
		User user = UserMapper.toEntity(userCreateDTO);
		user = service.insert(user);
		String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());
		Locale locale = LocaleContextHolder.getLocale();
		return ResponseEntity.status(201)
				.body(new AuthResponseDTO(token, messageSource.getMessage("auth.register.successful", null, locale)));
	}

	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		Locale locale = LocaleContextHolder.getLocale();
		loginAttemptService.isBlocked(loginRequestDTO.getEmail());

		if (service.authenticateUser(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())) {
			User user = service.findByEmail(loginRequestDTO.getEmail());
			String token = jwtUtil.generateToken(loginRequestDTO.getEmail(), user.getRole().toString());
			loginAttemptService.loginSucceeded(loginRequestDTO.getEmail());
			return ResponseEntity
					.ok(new AuthResponseDTO(token, messageSource.getMessage("auth.login.successful", null, locale)));
		}

		loginAttemptService.loginFailed(loginRequestDTO.getEmail());
		return ResponseEntity.status(401)
				.body(new AuthResponseDTO(null, messageSource.getMessage("auth.login.failed", null, locale)));
	}
}