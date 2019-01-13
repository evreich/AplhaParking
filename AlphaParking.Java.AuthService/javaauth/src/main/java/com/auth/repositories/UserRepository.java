package com.auth.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth.models.Role;
import com.auth.models.User;
import com.auth.models.mappers.UserRowMapper;
import com.auth.utils.AppConsts;
import com.auth.utils.HttpClient;
import com.auth.view_models.TokenVKViewModel;
import com.auth.view_models.VkProfileViewModel;
import com.auth.view_models.VkUserViewModel;
import com.google.gson.Gson;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HttpClient htppClient;

    public User getUserByVkToken(TokenVKViewModel vkToken) throws Exception {

        User user = null;
        try {
            user = (User) this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE vkId = ?", new UserRowMapper(),
                    vkToken.user_id);
        } catch (EmptyResultDataAccessException e) {
            user = null;
        }

        if (user != null) {
            return user;
        }

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("v","5.92");
        queryParams.put("access_token", vkToken.access_token);
        queryParams.put("user_ids", Integer.toString(vkToken.user_id));
        System.out.println(vkToken.user_id);
        System.out.println(vkToken.access_token);
        String serverResponse = htppClient.getRequest(AppConsts.VK_API_URI + AppConsts.VK_PROFILE_INFO_METHOD, queryParams);
        VkProfileViewModel vkProfile = new Gson().fromJson(serverResponse, VkProfileViewModel.class);

        VkUserViewModel userInfo = vkProfile.response[0];
        String userFio = userInfo.first_name + ' ' + userInfo.last_name;
        String userPhone = "";
        String userEmail = vkToken.email;

        user = new User(vkToken.user_id, vkToken.access_token, userFio, userPhone, userEmail);

        return create(user);
    }

    public List<User> getUsers () {
        return this.jdbcTemplate.query(
            "SELECT * FROM users",
            new BeanPropertyRowMapper(User.class));
    }

    public List<String> getLogins () {
        return this.jdbcTemplate.queryForList(
            "SELECT login FROM users",
            String.class);
    }

    public User create(final User user) throws Exception{
        String login = user.getLogin();
        String password = user.getPassword();
        if (user.getVkId() == 0 && login.equals(Strings.EMPTY)){
            throw new Exception("Пользователь не может быть создан без логина");
        }
        User oldUser = getUserByLogin(login);
        
        if (oldUser != null){
            throw new Exception("Пользователь с таким логином уже существует");
        }

        final String sql = "insert into users(login,password,fio,address,phone,email, vkId, vkToken)"+
                           "values(?,?,?,?,?,?,?,?)";
  
        String hashedPass = password.equals(Strings.EMPTY) ? Strings.EMPTY : new BCryptPasswordEncoder().encode( user.getPassword());              

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO);
                ps.setString(1, login);
                ps.setString(2, hashedPass);
                ps.setString(3, user.getFIO());
                ps.setString(4, user.getAddress());
                ps.setString(5, user.getPhone());
                ps.setString(6, user.getEmail());
                ps.setInt(7, user.getVkId());
                ps.setString(8, user.getVkToken());
                return ps;
            }
        });
     
        User newUser = this.getUserByLogin(user.getLogin());
        return newUser;
    }

    public User getUserById (int userId) {
        try{
            return (User) this.jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?", new UserRowMapper(), userId);
        }
        catch(EmptyResultDataAccessException e ){
            return null;
        }
    }

    public User getUserByLogin (String login) {
        if (login == null){
            return null;
        }
        try{
            return (User) this.jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE lower(login) = ?", new UserRowMapper(), login.toLowerCase());
        }
        catch(EmptyResultDataAccessException e ){
            return null;
        }
    }

    public User getUserByVkId (int vkId) {
        try{
            return (User) this.jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE vkId = ?", new UserRowMapper(), vkId);
        }
        catch(EmptyResultDataAccessException e ){
            return null;
        }
    }

    public void update(User user) {
        final String sql = 
            "update users " +
            "set login = ?, fio = ?, address = ?, phone = ?, email = ? " + 
            "where id = ?";          

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO);
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getFIO());
                ps.setString(3, user.getAddress());
                ps.setString(4, user.getPhone());
                ps.setString(5, user.getEmail());
                ps.setInt(6, user.getId());
                return ps;
            }
        });

        if (user.getPassword().equals(Strings.EMPTY)){
            return;
        }
        final String sqlPassword = 
        "update users " +
        "set password = ? " + 
        "where id = ?";          

    this.jdbcTemplate.update(new PreparedStatementCreator() {
        @Override
        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
            PreparedStatement ps = connection.prepareStatement(sqlPassword.toString(), Statement.SUCCESS_NO_INFO);
            ps.setString(1, new BCryptPasswordEncoder().encode( user.getPassword() ) );
            ps.setInt(2, user.getId());
            return ps;
        }
    });
    }

    public void delete(int userId) {
        final String sql = "delete users where id = ?";          

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO);
                ps.setInt(1, userId);
                return ps;
            }
        });
    }

    public boolean isExistsUserRole (int userId, int roleId) {
        try{
            Object userRole = this.jdbcTemplate.queryForObject(
                "select userId from user_role where userId = ? and roleId = ?", Integer.class, userId, roleId);

            return true;   
        }
        catch(EmptyResultDataAccessException e ){
            return false;
        }
    }

    public void grantRole(int userId, int roleId) {
        if (isExistsUserRole(userId, roleId)){
            return;
        }

        final String sql = "insert into user_role (userId, roleId) values (?, ?)";          

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO);
                ps.setInt(1, userId);
                ps.setInt(2, roleId);
                return ps;
            }
        });
    }

    public void revokeRole(int userId, int roleId) {
        final String sql = "delete user_role where userId = ? and roleId = ?";          

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO);
                ps.setInt(1, userId);
                ps.setInt(2, roleId);
                return ps;
            }
        });
    }

    public boolean checkPassword (String login, String pass) {
        User user = getUserByLogin(login);
        if (user == null){
            return false;
        }
        
        return new BCryptPasswordEncoder().matches(pass, user.getPassword());
    }

    public List<Role> getUserRoles (int userId) {
        List<Role> userRoles = this.jdbcTemplate.query(
            "select r.id, r.name from user_role ur join roles r on ur.roleId = r.id where userId = ?", new BeanPropertyRowMapper(Role.class), userId
        );

        return userRoles;
    }
}