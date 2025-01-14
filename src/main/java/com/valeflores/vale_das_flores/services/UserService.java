package com.valeflores.vale_das_flores.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeflores.vale_das_flores.dto.UpdatePasswordDTO;
import com.valeflores.vale_das_flores.dto.UserUpdateDTO;
import com.valeflores.vale_das_flores.entities.User;
import com.valeflores.vale_das_flores.repositories.UserRepository;
import com.valeflores.vale_das_flores.services.exceptions.RegisterException;
import com.valeflores.vale_das_flores.services.exceptions.ResourceNotFoundException;
import com.valeflores.vale_das_flores.services.exceptions.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	private PasswordEncoder passwordEncoder;

	private static final String EMAIL_PATTERN = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";

	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User insert(User user) {
		if (!isValidName(user.getName())) {
			throw new RegisterException(
					"Your name must include both first and last names, with a minimum of 8 characters.");
		}

		if (!isValidEmail(user.getEmail())) {
			throw new RegisterException("Invalid email. Please provide a valid email address.");
		}

		if (repository.existsByEmail(user.getEmail())) {
			throw new RegisterException("Email already in use.");
		}

		if (!isValidPassword(user.getPassword())) {
			throw new RegisterException("Your password must be at least 8 characters long for security reasons.");
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}

	@Transactional
	public User update(User user, UserUpdateDTO userUpdateDTO) {
		if (userUpdateDTO.getName() != null) {
			if (!isValidName(userUpdateDTO.getName())) {
				throw new RegisterException(
						"Your name must include both first and last names, with a minimum of 8 characters.");
			}
			user.setName(userUpdateDTO.getName());
		}

		if (userUpdateDTO.getEmail() != null) {
			if (!isValidEmail(userUpdateDTO.getEmail())) {
				throw new RegisterException("Invalid email. Please provide a valid email address.");
			}
			boolean emailInUse = repository.existsByEmail(userUpdateDTO.getEmail())
					&& !user.getEmail().equals(userUpdateDTO.getEmail());
			if (emailInUse) {
				throw new RegisterException("Email already in use.");
			}
			user.setEmail(userUpdateDTO.getEmail());
		}
		return repository.save(user);
	}

	@Transactional
	public User updatePassword(User user, UpdatePasswordDTO password) {
		if (!isValidUpdatePassword(user.getPassword(), password.getOldPassword())) {
			throw new RegisterException(
					"The current password entered does not match the registered one. Check that you entered correctly and try again.");
		}

		if (!isValidPassword(password.getNewPassword())) {
			throw new RegisterException("Your password must be at least 8 characters long for security reasons.");
		}

		user.setPassword(passwordEncoder.encode(password.getNewPassword()));
		return repository.save(user);
	}

	public boolean authenticateUser(String email, String password) {
		User user = repository.findByEmail(email);

		if (user == null) {
			return false;
		}

		return passwordEncoder.matches(password, user.getPassword());
	}

	private boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private boolean isValidName(String name) {
		return name != null && name.length() >= 8 && name.contains(" ");
	}

	private boolean isValidPassword(String password) {
		return password.length() >= 8;
	}

	private boolean isValidUpdatePassword(String password, String oldPassword) {
		return passwordEncoder.matches(oldPassword, password);
	}

	public User findByEmail(String email) {
		User user = repository.findByEmail(email);
		if (user == null) {
			throw new UserNotFoundException("User not found");
		}
		return user;
	}
}