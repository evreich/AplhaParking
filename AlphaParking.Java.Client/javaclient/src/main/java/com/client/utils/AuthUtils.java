package com.client.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthUtils {

    public static String getJWTFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    return cookie.getValue();
                }
            }
        } 

        return null;
    }

    public static void setJWTToCookie(String token, HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("Authorization", token);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
    }

    public static void clearJWTCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("Authorization", null);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
    }

    public static Claims getJWTClaims(String token) {
        try{
            return Jwts.parser()         
            .setSigningKey(ServerInfo.SUBSCRIBE_KEY.getBytes("UTF-8"))
            .parseClaimsJws(token).getBody();
        } catch(Exception e) {
            return null;
        }  
    }
}