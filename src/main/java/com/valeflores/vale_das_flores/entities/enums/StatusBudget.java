package com.valeflores.vale_das_flores.entities.enums;

public enum StatusBudget {
	REQUESTED(0),
	PENDING_REVIEW(1),
	REVIEWED(2),
	WAITING_APPROVAL(3),
	APPROVED(4),
	COMPLETED(5);

	private int code;

	private StatusBudget(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static StatusBudget valueOf(int code) {
		for (StatusBudget value : StatusBudget.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid User Type");
	}
}
