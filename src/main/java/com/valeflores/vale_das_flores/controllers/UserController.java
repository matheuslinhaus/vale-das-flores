package com.valeflores.vale_das_flores.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valeflores.vale_das_flores.config.JwtUtil;
import com.valeflores.vale_das_flores.dto.AuthResponseDTO;
import com.valeflores.vale_das_flores.dto.UpdatePasswordDTO;
import com.valeflores.vale_das_flores.dto.UserResponseDTO;
import com.valeflores.vale_das_flores.dto.UserUpdateDTO;
import com.valeflores.vale_das_flores.entities.User;
import com.valeflores.vale_das_flores.services.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/user/")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping(value = "/me", produces = "application/json")
	public ResponseEntity<UserResponseDTO> getUserFromToken(@RequestHeader("Authorization") String token) {
		token = token.replace("Bearer ", "");
		jwtUtil.isTokenExpired(token);
		String email = jwtUtil.extractUsername(token);
		User user = service.findByEmail(email);
		UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getName(), user.getEmail(),
				user.getPhone());
		return ResponseEntity.ok(userResponseDTO);
	}

	@PutMapping(value = "/me", produces = "application/json")
	public ResponseEntity<AuthResponseDTO> updateUserFromToken(@RequestHeader("Authorization") String token,
			@RequestBody UserUpdateDTO userUpdateDTO) {
		token = token.replace("Bearer ", "");
		jwtUtil.isTokenExpired(token);
		String email = jwtUtil.extractUsername(token);
		User user = service.findByEmail(email);

		User updatedUser = service.update(user, userUpdateDTO);

		String newToken = jwtUtil.generateToken(updatedUser.getEmail());
		AuthResponseDTO response = new AuthResponseDTO(newToken);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(value = "/me/change-password", produces = "application/json")
	public ResponseEntity<AuthResponseDTO> updatePasswordFromToken(@RequestHeader("Authorization") String token,
			@RequestBody UpdatePasswordDTO passwordDTO) {
		token = token.replace("Bearer ", "");
		jwtUtil.isTokenExpired(token);
		String email = jwtUtil.extractUsername(token);
		User user = service.findByEmail(email);

		User updatedUser = service.updatePassword(user, passwordDTO);

		String newToken = jwtUtil.generateToken(updatedUser.getEmail());
		AuthResponseDTO response = new AuthResponseDTO(newToken);
		return ResponseEntity.ok(response);
	}
}
