package com.budgetapp.application.model.service.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.budgetapp.application.model.data.user.User;
import com.budgetapp.application.model.repository.user.UserRepository;
import com.budgetapp.authentication.jwt.service.JwtUserDetailsService;

@Service
public class UserService {
	
	@Autowired
	JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private UserRepository userRepository;
	
	public User getcurrentUser(HttpServletRequest request)  {
		UserDetails userDetails = jwtUserDetailsService.getUserDetailsFromRequest(request);
		if (userDetails != null) {
			return userRepository.findByUsername(userDetails.getUsername());
		}
		return null;
	}
}
