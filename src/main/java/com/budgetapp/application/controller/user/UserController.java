package com.budgetapp.application.controller.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.budgetapp.application.model.assembler.user.UserResourceAssembler;
import com.budgetapp.application.model.data.user.User;
import com.budgetapp.application.model.exception.user.UserNotFoundException;
import com.budgetapp.application.model.repository.user.UserRepository;

@RestController
public class UserController {

	private final UserRepository userRepository;
	private final UserResourceAssembler userResourceAssembler;

	public UserController(UserRepository userRepository, UserResourceAssembler userResourceAssembler) {
		this.userRepository = userRepository;
		this.userResourceAssembler = userResourceAssembler;
	}

	@PostMapping("/users")
	public ResponseEntity<?> newUser(@RequestBody User newUser) throws URISyntaxException {

		Resource<User> resource = userResourceAssembler.toResource(userRepository.save(newUser));

		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);

	}

	@GetMapping("/users/{id}")
	public Resource<User> findOne(@PathVariable Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));

		return userResourceAssembler.toResource(user);
	}

	@GetMapping("/users")
	public Resources<Resource<User>> findAll() {

		List<Resource<User>> users = userRepository.findAll().stream()
				.map(userResourceAssembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(users, linkTo(methodOn(UserController.class).findAll()).withSelfRel());
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) throws URISyntaxException {

		User updatedUser = userRepository.findById(id).map(user -> {
			user.setFirstName(newUser.getFirstName());
			user.setLasttName(newUser.getLasttName());
			user.setUsername(newUser.getUsername());
			user.setRole(newUser.getRole());
			return userRepository.save(user);
		}).orElseGet(() -> {
			newUser.setId(id);
			return userRepository.save(newUser);
		});
		
		Resource<User> resource = userResourceAssembler.toResource(updatedUser);

		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		
		userRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}