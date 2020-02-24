package com.budgetapp.application.controller.expenses;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.budgetapp.application.model.assembler.expenses.ExpenseResourceAssembler;
import com.budgetapp.application.model.data.expense.Expense;
import com.budgetapp.application.model.data.user.User;
import com.budgetapp.application.model.exception.expenses.ExpenseNotFoundException;
import com.budgetapp.application.model.repository.expenses.ExpenseRepository;
import com.budgetapp.application.model.repository.user.UserRepository;
import com.budgetapp.application.model.service.user.UserService;

@RestController
public class ExpenseController {

	private final UserService userService;
	private final ExpenseRepository expenseRepository;
	private final ExpenseResourceAssembler expenseResourceAssembler;

	public ExpenseController(UserService userService, ExpenseRepository expenseRepository, ExpenseResourceAssembler expenseResourceAssembler) {
		this.userService = userService;
		this.expenseRepository = expenseRepository;
		this.expenseResourceAssembler = expenseResourceAssembler;
	}

	@GetMapping("/expenses/{id}")
	public Resource<Expense> findOne(@PathVariable Long id) {

		Expense expense = expenseRepository.findById(id)
				.orElseThrow(() -> new ExpenseNotFoundException(id));

		return expenseResourceAssembler.toResource(expense);
	}
	
	@GetMapping("/expenses")
	public Resources<Resource<Expense>> findAll(HttpServletRequest request) {
		List<Resource<Expense>> expenses = new ArrayList<>();
		User user = userService.getcurrentUser(request);
		
		if (user != null) {
			expenses = expenseRepository.findByUser(user).stream()
					.map(expenseResourceAssembler::toResource).collect(Collectors.toList());
		}
		return new Resources<>(expenses, linkTo(methodOn(ExpenseController.class).findAll(request)).withSelfRel());
	}
	
	public Resources<Resource<Expense>> findAll() {
		List<Resource<Expense>> expenses = expenseRepository.findAll().stream()
					.map(expenseResourceAssembler::toResource).collect(Collectors.toList());
		
		return new Resources<>(expenses, linkTo(methodOn(ExpenseController.class).findAll()).withSelfRel());
	}
}
