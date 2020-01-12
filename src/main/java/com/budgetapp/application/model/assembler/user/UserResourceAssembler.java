package com.budgetapp.application.model.assembler.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo; 
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.budgetapp.application.controller.user.UserController;
import com.budgetapp.application.model.data.user.User;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {

	@Override
	public Resource<User> toResource(User user) {
		return new Resource<>(user, 
				linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel(),
				linkTo(methodOn(UserController.class).findAll()).withRel("users"));
	}
}