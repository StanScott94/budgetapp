package com.budgetapp.application.model.data.role;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ROLE_ADMIN("ROLE_ADMIN"), 
    ROLE_USER("ROLE_USER");
    
    private String name;
    
    UserRole(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String getAuthority() {
		 return name;
	}
    
	
}
