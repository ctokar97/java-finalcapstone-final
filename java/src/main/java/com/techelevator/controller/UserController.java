package com.techelevator.controller;

import com.techelevator.dao.DaoInterface.UserDao;
import com.techelevator.model.RoleDto;
import com.techelevator.model.User;
import com.techelevator.security.RegisterUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	private final UserDao userDao;

	public UserController(UserDao userDao) {
		this.userDao = userDao;
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users;
		try {
			users = userDao.getUsers();
			if (users.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user;
		try {
			user = userDao.getUserByUsername(username);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/{userId}")
	public ResponseEntity<User> changeUserRole(@PathVariable Integer userId, @RequestBody RoleDto roleDto) {
		User updatedUser;
		try {
			updatedUser = userDao.changeUserRole(userId, roleDto.getRole());
			return ResponseEntity.ok(updatedUser);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
