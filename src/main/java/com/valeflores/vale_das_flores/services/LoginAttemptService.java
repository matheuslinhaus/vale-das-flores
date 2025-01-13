package com.valeflores.vale_das_flores.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.valeflores.vale_das_flores.entities.LoginAttempt;

@Service
public class LoginAttemptService {
	private static final int MAX_ATTEMPTS = 4;
    private static final int LOCK_DURATION_MINUTES = 5;

    private final ConcurrentHashMap<String, LoginAttempt> attemptsCache = new ConcurrentHashMap<>();

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

    public boolean isBlocked(String username) {
        LoginAttempt attempt = attemptsCache.get(username);
        if (attempt != null) {
            if (attempt.isLocked()) {
                return true;
            } else if (attempt.getAttempts() >= MAX_ATTEMPTS) {
                attempt.resetAttempts();
                return false;
            }
        }
        return false;
    }
}
