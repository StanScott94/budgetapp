package com.budgetapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.budgetapp.application.model.data.expense.Expense;
import com.budgetapp.application.model.data.expense.MainCategory;
import com.budgetapp.application.model.data.expense.SubCategory;
import com.budgetapp.application.model.data.role.UserRole;
import com.budgetapp.application.model.data.user.User;
import com.budgetapp.application.model.repository.expenses.ExpenseRepository;
import com.budgetapp.application.model.repository.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DemoDataUtil {
	@Bean
	CommandLineRunner initDatabase(UserRepository repository, ExpenseRepository expenseRepository) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		List<Expense> expenses = new ArrayList<>();
		
		User user1 = new User("stanton", "scott", passwordEncoder.encode("1234"), "test@123.com", UserRole.ROLE_ADMIN, new ArrayList<>());
		User user2 = new User("max", "payne", passwordEncoder.encode("1234"), "max@123.com", UserRole.ROLE_USER, new ArrayList<>());
		User user3 =new User("lewy", "lou", passwordEncoder.encode("1234"), "lou@123.com", UserRole.ROLE_USER, new ArrayList<>());
		
		log.info("Preloading " + repository.save(user1));
		log.info("Preloading " + repository.save(user2));
		log.info("Preloading " + repository.save(user3));
		
		expenses.add(new Expense(user1, "Work lunch", "salad from lidl", 2.59, MainCategory.FOOD, SubCategory.WORK_LUNCH));
		expenses.add(new Expense(user1, "Afternoon snack", "nuts and fanta", 3.50, MainCategory.FOOD, SubCategory.SNACKS));
		expenses.add(new Expense(user1, "Weekly shopping", "groceries from Edeka", 66.87, MainCategory.FOOD, SubCategory.GROCERIES));
		expenses.add(new Expense(user1, "Burger", "late night burger", 2.59, MainCategory.FOOD, SubCategory.FAST_FOOD));
		
		expenseRepository.saveAll(expenses);		
		user1.setExpenses(expenses);
		
		return args -> {};
	}
}
