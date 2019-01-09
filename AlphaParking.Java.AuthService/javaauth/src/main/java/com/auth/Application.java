package com.auth;

import com.auth.utils.SeedDb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
	    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		// Заполнение бд дефолтными значениями
		SeedDb seedDb = context.getBean(SeedDb.class);
		seedDb.seedDefaultRoles();
		seedDb.seedDefaultUsers();
		seedDb.setUserRoleRelationships();
	}
}
