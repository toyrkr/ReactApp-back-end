package com.toy.ReactApp;

import com.toy.ReactApp.user.User;
import com.toy.ReactApp.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReactAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactAppApplication.class, args);
	}

	@Bean
	CommandLineRunner createInitialUsers(UserService userService){
		return (args) -> {
			User user = new User();
			user.setUsername( "user1" );
			user.setDisplayname( "display1" );
			user.setPassword( "p4ssword" );
			userService.save( user );
		};
	}
}
