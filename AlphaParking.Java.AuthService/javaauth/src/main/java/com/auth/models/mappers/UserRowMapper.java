package com.auth.models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.auth.models.User;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper
{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setFIO(rs.getString("fio"));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setPhone(rs.getString("phone"));
        user.setVkId(rs.getInt("vkId"));
        user.setVkToken(rs.getString("vkToken"));
		return user;
	}
	
}