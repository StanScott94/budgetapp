package com.budgetapp.authentication.jwt.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtToken implements Serializable {

	private static final long serialVersionUID = -1055474103955783825L;

    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.token.valisity}")
    private String tokenValisity;


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    	try {
    		final Claims claims = getAllClaimsFromToken(token);
    		return claimsResolver.apply(claims);
    	} catch (SignatureException e) {
    		log.error("error retrieving claims from token: {} ", e.getMessage());
    	} catch (MalformedJwtException e) {
    		log.error("error retrieving claims from token: {}", e.getMessage());
    	}
    	return null;
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
        		.setSigningKey(secret)
        		.parseClaimsJws(token)
        		.getBody();
    }


    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }


    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
        		.setClaims(claims)
        		.setSubject(subject)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.valueOf(tokenValisity)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

