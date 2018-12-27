package com.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.subst.Token;

import com.auth.models.*;

import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.auth.repositories.RoleRepository;
import com.auth.repositories.UserRepository;
import com.auth.utils.AppConsts;
import com.auth.utils.HttpClient;
import com.auth.view_models.TokenVKViewModel;
import com.microsoft.applicationinsights.core.dependencies.gson.Gson;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private RoleRepository roleRepository;

    @Autowired
    HttpClient httpClient;

    public TokenVKViewModel getTokenVK(String code) throws IOException{
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("client_id", Integer.toString(AppConsts.VK_APP_ID) );
        queryParams.put("client_secret", AppConsts.VK_CLIENT_SECRET);
        queryParams.put("redirect_uri", AppConsts.VK_REDIRECT_URI);
        queryParams.put("code", code);
        String serverResponse = httpClient.getRequest(AppConsts.VK_ACCESS_TOKEN_URI, queryParams);
        TokenVKViewModel tokenVK = new Gson().fromJson(serverResponse, TokenVKViewModel.class);
        //TODO: check generated token and answer from VK
        return tokenVK;
    }

    public String getToken(String login, String pass, TokenVKViewModel vkToken) throws Exception {
        if (login == null || pass == null){
            return null;
        }
        User user = vkToken == null ? userRepository.getUserByLogin(login) : userRepository.getUserByVkId(vkToken.user_id);
    
        if (vkToken != null || userRepository.checkPassword(login, pass)) { 
            if (roleRepository.getRoleByName("employee") == null)
                throw new EmptyResultDataAccessException("Ошибка БД! Стандартной роли пользователя не существует.", 1);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);

            JwtBuilder jwtBuilder = Jwts.builder();
            Claims claims = Jwts.claims();

            claims.put(AppConsts.ROLES_CLAIM, userRepository.getUserRoles( user.getId() )
                .stream().map(role -> role.getName()).collect(Collectors.toList()));

            if (vkToken != null){
                claims.put(AppConsts.VK_CLAIM, vkToken.access_token);   
            }  

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