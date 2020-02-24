package com.budgetapp.application.model.data.expense;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.budgetapp.application.model.data.user.User;
import lombok.Data;

@Data
@Entity
public class Expense {

	private @Id @GeneratedValue Long id;
	private String title;
	private String description;
	private double ammount;
	private MainCategory mainCategory;
	private SubCategory subCategory;
	@ManyToOne(optional=false)
	private User user;

	public Expense() {
	}

	public Expense(User user,String title, String description, double ammount, MainCategory mainCategory, SubCategory subCategory) {
		this.user = user;
		this.title = title;
		this.description = description;
		this.ammount = ammount;
		this.mainCategory = mainCategory;
		this.subCategory = subCategory;
	}
}
