package com.budgetapp.application.model.data.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.budgetapp.application.model.data.expense.Expense;
import com.budgetapp.application.model.data.role.UserRole;

import lombok.Data;

@Data
@Entity
public class User {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lasttName;
	private String password;
	private String emailAddress;
	private UserRole role;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Expense> expenses;

	public User() {
	}

	public User(String firstName, String lasttName, String password, String emailAddress, UserRole role, List<Expense> expenses) {
		this.firstName = firstName;
		this.lasttName = lasttName;
		this.password = password;
		this.emailAddress = emailAddress;
		this.role = role;
		this.expenses = expenses;
	}
}
