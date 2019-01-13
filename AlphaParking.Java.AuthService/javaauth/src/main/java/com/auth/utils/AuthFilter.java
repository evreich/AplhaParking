package com.auth.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthFilter {
    private static boolean isJWTAuthorized(String token) {
        try{
            Jwts.parser()       
            .setSigningKey(AppConsts.SUBSCRIBE_KEY.getBytes("UTF-8")) 
            .parseClaimsJws(token).getBody();
            return true;
        } catch(Exception e) {
            return false;
        }    
    }

    public static ResponseEntity<ObjectNode> authRequestFilter(HttpServletRequest request) {
        String token = request.getHeader("Authorization").split("\\s+")[1];
        if(!AuthFilter.isJWTAuthorized(token)) {
            return new ResponseError().generateResponse(
                "Пользователь не выполнил JWT авторизацию", 
                HttpStatus.UNAUTHORIZED
            );
        }

        Claims tokenClaims = getJWTClaims(token);
        if (tokenClaims == null){
            return new ResponseError().generateResponse(
                "Пользователь не имеет прав для выполнения операции.", 
                HttpStatus.FORBIDDEN
            );
        }

        List<String> roleNames = (List<String>) tokenClaims.get(AppConsts.ROLES_CLAIM, List.class);
        
        for (String roleName : roleNames){
            if (roleName.toLowerCase().equals(AppConsts.ROLE_ADMIN) || roleName.toLowerCase().equals(AppConsts.ROLE_MANAGER)){
                return null;
            }
        }

        return new ResponseError().generateResponse(
            "Пользователь не имеет прав для выполнения операции.", 
            HttpStatus.FORBIDDEN
        );
    }

    public static Claims getJWTClaims(String token) {
        try{
            return Jwts.parser()         
            .setSigningKey(AppConsts.SUBSCRIBE_KEY.getBytes("UTF-8"))
            .parseClaimsJws(token).getBody();
        } catch(Exception e) {
            return null;
        }  
    }
}