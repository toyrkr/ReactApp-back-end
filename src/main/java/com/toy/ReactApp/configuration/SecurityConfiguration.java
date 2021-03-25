package com.toy.ReactApp.configuration;

import com.toy.ReactApp.user.User;
import com.toy.ReactApp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.httpBasic().authenticationEntryPoint( ( (httpServletRequest, httpServletResponse, e) -> {
			httpServletResponse.sendError( HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}) );
		
		http.authorizeRequests()
				.antMatchers( HttpMethod.POST, "/api/v1/auth" )
				.authenticated()
				.and()
				.authorizeRequests()
				.anyRequest()
				.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService( (username) -> {
			User inDB = userRepository.findUserByUsername( username );
			if(inDB == null)
				throw new UsernameNotFoundException( "Username not found!" );
			
			return new ReactAppUserDetails( inDB );
		} )
		.passwordEncoder( passwordEncoder() );
	}
	
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
