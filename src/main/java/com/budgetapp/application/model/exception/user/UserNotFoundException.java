package com.budgetapp.application.model.exception.user;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Long id) {
		super("Could not find user: " + id);
	}
}