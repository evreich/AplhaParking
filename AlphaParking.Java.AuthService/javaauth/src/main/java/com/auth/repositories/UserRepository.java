package com.auth.repositories;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import com.auth.models.Role;
import com.auth.models.User;
import com.auth.models.mappers.UserRowMapper;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        if (login.equals(Strings.EMPTY)){
            throw new Exception("Пользователь не может быть создан без логина");
        }
        User oldUser = getUserByLogin( login );
        if (oldUser != null){
            throw new Exception("Пользователь с таким логином уже существует");
        }

        final String sql = "insert into users(id,login,password,fio,address,phone,email)"+
                           "values(NEXT VALUE FOR dbo.UserSeq,?,?,?,?,?,?)";

        String hashedPass = new BCryptPasswordEncoder().encode( user.getPassword());              

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
                return ps;
            }
        });

        Long newUserId = jdbcTemplate.queryForObject("SELECT current_value FROM sys.sequences WHERE name = 'UserSeq';", Long.class);
     
        user.setId(Math.toIntExact(newUserId));
        return user;
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
        try{
            return (User) this.jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE lower(login) = ?", new UserRowMapper(), login.toLowerCase());
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

    // TODO: переделать C# код под Java
    
    // public void AddRoleToUser(UserDTO user, RoleDTO role)
    // {
    //     user.Roles.Add(role);
    //     _database.UserRepository.Update(_mapper.Map<User>(user));
    // }

    // public async Task<bool> IsRegistered(string login, string password)
    // {
    //     var hasher = new PasswordHasher<User>();
    //     return await _database.UserRepository.GetElem(u => u.Login.Equals(login) &&
    //                     hasher.VerifyHashedPassword(u, u.Password, password) == PasswordVerificationResult.Success) != null;
    // }

    // public async Task<UserDTO> Create(UserDTO elem)
    // {
    //     if(await IsRegistered(elem.Login, elem.Password))
    //     {
    //         throw new BadRequestException("Пользователь уже существет");
    //     }
    //     var newUser = _mapper.Map<UserDTO>(await _database.UserRepository.Create(MapUserFromDTO(elem)));

    //     var defaultRole = _mapper.Map<RoleDTO>(await _database.RoleRepository.GetElem(role => role.Name == RoleConstants.EMPLOYEE));
    //     this.AddRoleToUser(newUser, defaultRole);
    //     // await _unitOfWork.UserRoleRepository.Create(new UserRole { RoleId = defaultRole.Id, UserId = newUser.Id });

    //     return _mapper.Map<UserDTO>(newUser);
    // }

    // public async Task<UserDTO> Update(UserDTO elem)
    // {
    //     var updatedUser = await _database.UserRepository.Update(MapUserFromDTO(elem));
    //     return _mapper.Map<UserDTO>(updatedUser);
    // }

    // public async Task<UserDTO> Delete(UserDTO elem)
    // {
    //     return await MappingDataUtils.WrapperMappingDALFunc<UserDTO, User>
    //         (_database.UserRepository.Delete, elem, _mapper);
    // }
}