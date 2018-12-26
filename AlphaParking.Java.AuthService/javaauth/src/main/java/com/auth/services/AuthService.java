package com.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.auth.models.*;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.auth.repositories.RoleRepository;
import com.auth.repositories.UserRepository;
import com.auth.utils.AppConsts;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private RoleRepository roleRepository;

    public String getToken(String login, String pass) throws Exception {
        if (login == null || pass == null){
            return null;
        }
        User user = userRepository.getUserByLogin(login);       
        if (userRepository.checkPassword(login, pass)) { 
            if (roleRepository.getRoleByName("employee") == null)
                throw new EmptyResultDataAccessException("Ошибка БД! Стандартной роли пользователя не существует.", 1);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);

            JwtBuilder jwtBuilder = Jwts.builder();
            Claims claims = Jwts.claims();

            claims.put(AppConsts.ROLES_CLAIM, userRepository.getUserRoles( user.getId() )
                .stream().map(role -> role.getName()).collect(Collectors.toList()));

            String token = jwtBuilder
                .setClaims(claims)
                .setSubject(login)
                .setIssuer(AppConsts.ISSUER_CLAIM)
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256, AppConsts.SUBSCRIBE_KEY.getBytes("UTF-8"))
                .compact();
                
            return token;
        } else {
            throw new Exception("Неверный пароль");
        }
    }
}