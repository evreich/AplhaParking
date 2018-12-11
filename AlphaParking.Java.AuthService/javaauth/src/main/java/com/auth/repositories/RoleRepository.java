package com.auth.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.auth.models.Role;

@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Role> findAll() {
        List<Role> result = this.jdbcTemplate.query(
                "SELECT * FROM dbo.Roles",
                (rs, rowNum) -> new Role
                (
                    rs.getInt("id"),
                    rs.getString("name")
                )
        );
        return result;
    }
}
