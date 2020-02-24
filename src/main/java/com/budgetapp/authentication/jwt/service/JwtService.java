package com.budgetapp.authentication.jwt.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.budgetapp.authentication.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtService {

	public String getJWTFromRequest(HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader(Constants.AUTHORIZATION);
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith(Constants.BEARER)) {
			jwtToken = requestTokenHeader.substring(Constants.TOKEN_INDEX);
		} else {
			log.warn("JWT Token does not begin with Bearer String");
		}

		return jwtToken;
	}
}
