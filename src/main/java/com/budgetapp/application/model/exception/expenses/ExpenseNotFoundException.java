package com.budgetapp.application.model.exception.expenses;

public class ExpenseNotFoundException extends RuntimeException {

	public ExpenseNotFoundException(Long id) {
		super("Could not find expense: " + id);
	}
}