package com.valeflores.vale_das_flores.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.valeflores.vale_das_flores.config.JwtUtil;
import com.valeflores.vale_das_flores.dto.AuthResponseDTO;
import com.valeflores.vale_das_flores.dto.LoginRequestDTO;
import com.valeflores.vale_das_flores.dto.UserCreateDTO;
import com.valeflores.vale_das_flores.dto.UserResponseDTO;
import com.valeflores.vale_das_flores.entities.User;
import com.valeflores.vale_das_flores.mappers.UserMapper;
import com.valeflores.vale_das_flores.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/auth/")
public class UserResource {

	@Autowired
	private UserService service;

	@Autowired
	private JwtUtil jwtUtil;

	@Operation(summary = "New User", description = "Create a new user", tags = "User", responses = {
			@ApiResponse(responseCode = "201", description = "Successfully inserted", content = @Content(mediaType = "application/json", schema = @Schema(example = ""))) })
	@PostMapping(value = "register")
	public ResponseEntity<User> insert(@RequestBody UserCreateDTO userCreateDTO) {
		User user = UserMapper.toEntity(userCreateDTO);
		user = service.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}

	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		boolean isAuthenticated = service.authenticateUser(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

		if (isAuthenticated) {
			String token = jwtUtil.generateToken(loginRequestDTO.getEmail());
			return ResponseEntity.ok(new AuthResponseDTO(token));
		}

		return ResponseEntity.status(401).body("Unauthorized");
	}
	
	@GetMapping(value = "/user", produces = "application/json")
	public ResponseEntity<?> getUserFromToken(@RequestHeader("Authorization") String token) {
	    try {
	        token = token.replace("Bearer ", "");

	        if (jwtUtil.isTokenExpired(token)) {
	            return ResponseEntity.status(401).body("Token expired");
	        }

	        String email = jwtUtil.extractUsername(token);
	        User user = service.findByEmail(email);

	        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone());

	        return ResponseEntity.ok(userResponseDTO);
	    } catch (Exception e) {
	        return ResponseEntity.status(401).body("Invalid token");
	    }
	}
}