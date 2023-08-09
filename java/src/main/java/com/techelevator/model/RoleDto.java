package com.techelevator.model;

import javax.validation.constraints.NotEmpty;

public class RoleDto {

	@NotEmpty(message = "Please select a role for this user.")
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
