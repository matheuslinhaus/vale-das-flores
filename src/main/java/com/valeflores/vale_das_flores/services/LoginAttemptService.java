package com.valeflores.vale_das_flores.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.valeflores.vale_das_flores.entities.LoginAttempt;
import com.valeflores.vale_das_flores.services.exceptions.LoginAttemptException;

@Service
public class LoginAttemptService {
	private static final int MAX_ATTEMPTS = 4;
	private static final int LOCK_DURATION_MINUTES = 5;

	private final ConcurrentHashMap<String, LoginAttempt> attemptsCache = new ConcurrentHashMap<>();

	@Autowired
	private MessageSource messageSource;

	public void loginFailed(String username) {
		LoginAttempt attempt = attemptsCache.computeIfAbsent(username, key -> new LoginAttempt());
		attempt.incrementAttempts();

		if (attempt.getAttempts() >= MAX_ATTEMPTS) {
			attempt.setLockTime(Instant.now().plus(LOCK_DURATION_MINUTES, ChronoUnit.MINUTES));
		}
	}

	public void loginSucceeded(String username) {
		attemptsCache.remove(username);
	}

	public void isBlocked(String username) {
		Locale locale = LocaleContextHolder.getLocale();
		LoginAttempt attempt = attemptsCache.get(username);
		if (attempt != null) {
			if (attempt.isLocked()) {
				throw new LoginAttemptException(messageSource.getMessage("login.attempt.max", null, locale));
			} else if (attempt.getAttempts() >= MAX_ATTEMPTS) {
				attempt.resetAttempts();
			}
		}
	}
}
