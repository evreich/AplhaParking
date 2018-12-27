package com.auth.utils;

public final class AppConsts {
    static public final String VK_ACCESS_TOKEN_URI = "https://oauth.vk.com/access_token";
    static public final int VK_APP_ID = 6798414; 
    static public final String VK_CLIENT_SECRET = "LdxxPc6z9JLbCN0jgvYA"; 
    static public final String VK_REDIRECT_URI = "http://localhost:8383/vk/auth";  

    static public final String ROLE_ADMIN = "admin"; 
    static public final String ROLE_CLAIM = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role";
    static public final String ROLES_CLAIM = "roles";
    static public final String ISSUER_CLAIM = "AlphaParkingAuthService";
    static public final String SUBSCRIBE_KEY = "SuperSecretAlphaParkingKey321";
    static public final String exchangeName = "alphaparking_eventbus_exhange";
	static public final String queueName = "alphaparking_eventbus-queue";
}