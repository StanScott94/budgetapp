package com.budgetapp.authentication.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.budgetapp.authentication.Constants;
import com.budgetapp.authentication.jwt.data.JwtToken;
import com.budgetapp.authentication.jwt.service.JwtService;
import com.budgetapp.authentication.jwt.service.JwtUserDetailsService;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtService jwtService;
	
	private final JwtToken jwtTokenUtil;

	public JwtRequestFilter(JwtToken jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			final String requestTokenHeader = request.getHeader(Constants.AUTHORIZATION);
			String jwtToken = jwtService.getJWTFromRequest(request);
			
			UserDetails userDetails = jwtUserDetailsService.getUserDetailsFromRequest(request);
			if (userDetails !=  null && StringUtils.isNotEmpty(jwtToken) && jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}