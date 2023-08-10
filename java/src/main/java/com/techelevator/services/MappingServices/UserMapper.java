package com.techelevator.services.MappingServices;

import com.techelevator.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserMapper {

	public User mapRowToUser(SqlRowSet rs) {
		User user = new User();
		user.setId(rs.getInt("user_id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password_hash"));
		user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
		user.setActivated(true);
		return user;
	}
}
