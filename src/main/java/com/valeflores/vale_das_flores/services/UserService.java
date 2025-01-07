package com.valeflores.vale_das_flores.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeflores.vale_das_flores.entities.User;
import com.valeflores.vale_das_flores.repositories.UserRepository;
import com.valeflores.vale_das_flores.services.exceptions.EmailException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	private PasswordEncoder passwordEncoder;

	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User insert(User user) {
		if (repository.existsByEmail(user.getEmail())) {
			throw new EmailException("Email already in use.");
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}

	public boolean authenticateUser(String email, String password) {
		User user = repository.findByEmail(email);

		if (user == null) {
			return false; // Se o usuário não existe, retorno false
		}

		// Verifica se a senha fornecida corresponde à senha criptografada no banco
		return passwordEncoder.matches(password, user.getPassword());
	}
}