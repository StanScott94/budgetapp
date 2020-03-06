package com.budgetapp.application.model.repository.expenses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.budgetapp.application.model.data.expense.Expense;
import com.budgetapp.application.model.data.user.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	@Query
	List<Expense> findByUser(User user);
	
	@Query
	Expense findExpenseByIdAndUser(Long id, User user);
}
