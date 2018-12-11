package com.auth.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.auth.models.User;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User create(final User user) 
    {
        final String sql = "insert into users(id,login,password,fio,address,phone,email)"+
                           "values(NEXT VALUE FOR dbo.UserSeq,?,?,?,?,?,?)";

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.SUCCESS_NO_INFO);
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());
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