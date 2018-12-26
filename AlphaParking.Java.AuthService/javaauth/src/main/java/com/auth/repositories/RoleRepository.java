package com.auth.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import com.auth.models.Role;
import com.auth.models.mappers.RoleRowMapper;

@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Role> getRoles () {
        return this.jdbcTemplate.query(
            "SELECT * FROM roles",
            new BeanPropertyRowMapper(Role.class));
    }

    public List<String> getRoleNames () {
        return this.jdbcTemplate.queryForList(
            "SELECT name FROM roles",
            String.class);
    }

    public Role create(final Role role) throws Exception{
        Role oldRole = getRoleByName( role.getName() );
        if (oldRole != null){
            throw new Exception("Роль с таким наименованием уже существует");
        }

        final String sql = "insert into roles(name)"+
                           "values(?)";            

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO);
                ps.setString(1, role.getName());
                return ps;
            }
        });

        Role newRole = getRoleByName(role.getName());
        return newRole;
    }

    public Role getRoleByName (String name) {
        try{
            return (Role) this.jdbcTemplate.queryForObject(
                "SELECT * FROM roles WHERE lower(name) = ?", new RoleRowMapper(), name.toLowerCase());
        }
        catch(EmptyResultDataAccessException e ){
            return null;
        }
    }

    public void update(Role role){
        final String sql = "update roles set name = ? where id = ?";          

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO);
                ps.setString(1, role.getName());
                ps.setInt(2, role.getId());
                return ps;
            }
        });
    }

    public void delete(int roleId){
        final String sql = "delete roles where id = ?";          

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO);
                ps.setInt(1, roleId);
                return ps;
            }
        });
    }
}
