package com.auth.models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.auth.models.Role;

import org.springframework.jdbc.core.RowMapper;

public class RoleRowMapper implements RowMapper
{
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		Role role = new Role();
		role.setId(rs.getInt("id"));
        role.setName(rs.getString("name"));
		return role;
	}
	
}