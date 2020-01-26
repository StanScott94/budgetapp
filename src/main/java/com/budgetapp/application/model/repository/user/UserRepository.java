package com.budgetapp.application.model.repository.user;

import com.budgetapp.application.model.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
