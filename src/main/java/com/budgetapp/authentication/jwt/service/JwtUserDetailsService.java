package com.budgetapp.authentication.jwt.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.budgetapp.application.model.data.role.UserRole;
import com.budgetapp.application.model.data.user.User;
import com.budgetapp.application.model.exception.user.UserNotFoundException;
import com.budgetapp.application.model.repository.user.UserRepository;
import com.budgetapp.authentication.Constants;
import com.budgetapp.authentication.jwt.data.JwtToken;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtService jwtService;
	
	private final JwtToken jwtTokenUtil;

	public JwtUserDetailsService(JwtToken jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		List<UserRole> roles = new ArrayList<>();
		roles.add(user.getRole());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>(roles));
	}

	public UserDetails getUserDetailsFromRequest(HttpServletRequest request) throws UserNotFoundException {
		String username = null;
		String jwtToken = jwtService.getJWTFromRequest(request);
		
		if (StringUtils.isNotEmpty(jwtToken)) {
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				//TODO: add message "Unable to get JWT Token"
				log.warn("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				//TODO: add message "JWT Token has expired"
				log.warn("JWT Token has expired");
			}
	
			if (username != null) {
				return this.jwtUserDetailsService.loadUserByUsername(username);
			}
		}
		return null;
	}
}

