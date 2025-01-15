package com.valeflores.vale_das_flores.services;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeflores.vale_das_flores.dto.UpdatePasswordDTO;
import com.valeflores.vale_das_flores.dto.UserUpdateDTO;
import com.valeflores.vale_das_flores.entities.User;
import com.valeflores.vale_das_flores.repositories.UserRepository;
import com.valeflores.vale_das_flores.services.exceptions.RegisterException;
import com.valeflores.vale_das_flores.services.exceptions.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private MessageSource messageSource;

	private PasswordEncoder passwordEncoder;

	private static final String EMAIL_PATTERN = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";

	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User insert(User user) {
		Locale locale = LocaleContextHolder.getLocale();
		if (!isValidName(user.getName())) {
			throw new RegisterException(messageSource.getMessage("user.invalid.name", null, locale));
		}

		if (!isValidEmail(user.getEmail())) {
			throw new RegisterException(messageSource.getMessage("user.invalid.email", null, locale));
		}

		if (repository.existsByEmail(user.getEmail())) {
			throw new RegisterException(messageSource.getMessage("user.email.in.use", null, locale));
		}

		if (!isValidPassword(user.getPassword())) {
			throw new RegisterException(messageSource.getMessage("user.invalid.password", null, locale));
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}

	@Transactional
	public User update(User user, UserUpdateDTO userUpdateDTO) {
		Locale locale = LocaleContextHolder.getLocale();
		if (userUpdateDTO.getName() != null) {
			if (!isValidName(userUpdateDTO.getName())) {
				throw new RegisterException(messageSource.getMessage("user.invalid.name", null, locale));
			}
			user.setName(userUpdateDTO.getName());
		}

		if (userUpdateDTO.getEmail() != null) {
			if (!isValidEmail(userUpdateDTO.getEmail())) {
				throw new RegisterException(messageSource.getMessage("user.invalid.email", null, locale));
			}
			boolean emailInUse = repository.existsByEmail(userUpdateDTO.getEmail())
					&& !user.getEmail().equals(userUpdateDTO.getEmail());
			if (emailInUse) {
				throw new RegisterException(messageSource.getMessage("user.email.in.use", null, locale));
			}
			user.setEmail(userUpdateDTO.getEmail());
		}
		return repository.save(user);
	}

	@Transactional
	public User updatePassword(User user, UpdatePasswordDTO password) {
		Locale locale = LocaleContextHolder.getLocale();
		if (!isValidUpdatePassword(user.getPassword(), password.getOldPassword())) {
			throw new RegisterException(messageSource.getMessage("user.update.password.not.match", null, locale));
		}

		if (!isValidPassword(password.getNewPassword())) {
			throw new RegisterException(messageSource.getMessage("user.invalid.password", null, locale));
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
		Locale locale = LocaleContextHolder.getLocale();
		User user = repository.findByEmail(email);
		if (user == null) {
			throw new UserNotFoundException(messageSource.getMessage("user.not.found", null, locale));
		}
		return user;
	}
}