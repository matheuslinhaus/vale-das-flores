package com.valeflores.vale_das_flores.entities;

import java.time.Instant;

public class LoginAttempt {
	private int attempts;
	private Instant lockTime;

	public LoginAttempt() {
		this.attempts = 0;
		this.lockTime = null;
	}

	public int getAttempts() {
		return attempts;
	}

	public void incrementAttempts() {
		this.attempts++;
	}

	public void resetAttempts() {
		this.attempts = 0;
	}

	public Instant getLockTime() {
		return lockTime;
	}

	public void setLockTime(Instant lockTime) {
		this.lockTime = lockTime;
	}

	public boolean isLocked() {
		return lockTime != null && lockTime.isAfter(Instant.now());
	}
}