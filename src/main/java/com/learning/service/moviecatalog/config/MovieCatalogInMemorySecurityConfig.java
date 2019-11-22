package com.learning.service.moviecatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableWebSecurity
public class MovieCatalogInMemorySecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] AUTH_WHITE_LIST = {
	          "/v2/api-docs"
	        , "/swagger-resources"
	        , "/swagger-resources/**"
	        , "/configuration/security"
	        , "/swagger-ui.html"
	        , "/webjars/**"
	        , "/security/**"
	        , "/actuator"
	        , "/actuator/**"
	    };
	
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//auth.userDetailsService(userDetailsService);
		
		
		auth.inMemoryAuthentication().withUser("rouser").password("rouser").roles("rouser");
		auth.inMemoryAuthentication().withUser("rwuser").password("rwuser").roles("rwuser");
		
		
	}
	
	
	@Bean
	protected PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.authorizeRequests() 
			.antMatchers("/api/*/catalog").hasAnyRole("rouser", "rwuser")
			.antMatchers(AUTH_WHITE_LIST).permitAll()
			.and().httpBasic();
		
	} 
	
}
