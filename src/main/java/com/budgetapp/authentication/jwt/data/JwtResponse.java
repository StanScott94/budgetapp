package com.budgetapp.authentication.jwt.data;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 8954952063742997170L;
	private final String jwttoken;
	
}
