package com.budgetapp.application.controller.expenses.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.budgetapp.application.model.exception.expenses.ExpenseNotFoundException;

@ControllerAdvice
public class ExpenseNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(ExpenseNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(ExpenseNotFoundException ex) {
		return ex.getMessage();
	}
}