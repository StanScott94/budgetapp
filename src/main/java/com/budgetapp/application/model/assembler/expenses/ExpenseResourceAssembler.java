package com.budgetapp.application.model.assembler.expenses;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import com.budgetapp.application.controller.expenses.ExpenseController;
import com.budgetapp.application.model.data.expense.Expense;

@Component
public class ExpenseResourceAssembler implements ResourceAssembler<Expense, Resource<Expense>> {
	
	@Override
	public Resource<Expense> toResource(Expense expense) {
		return new Resource<>(expense, 
				linkTo(methodOn(ExpenseController.class).findOne(expense.getId())).withSelfRel());
				//linkTo(methodOn(ExpenseController.class).findAll()).withRel("expenses"));
	}
}