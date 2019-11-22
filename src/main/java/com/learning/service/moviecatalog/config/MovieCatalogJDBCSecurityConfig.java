package com.learning.service.moviecatalog.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class MovieCatalogJDBCSecurityConfig extends WebSecurityConfigurerAdapter {
	
	Logger logger = LoggerFactory.getLogger(MovieCatalogJDBCSecurityConfig.class);
	
	
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
	
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		//Default schema does not work with mysql as table creation syntax is different for H2 and MySQL
		/*auth.jdbcAuthentication()
			.dataSource(dataSource)
			.withDefaultSchema()
			.withUser(User.withUsername("rouser")
					      .password("rouser")
						  .roles("ROUSER"))
			.withUser(User.withUsername("rwuser")
					      .password("rwuser")
						  .roles("RWUSER"));*/
		
			auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, authority from authorities where username=?");
		
		
	}
	
	
	//@Bean
	/* protected PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(4);
		
	} */
	
	@Bean
	protected PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		logger.info("Inside configure method of MovieCatalogJDBCSecurityConfig - HttpSecurity");
		
		http.csrf().disable();
		http.authorizeRequests() 
			.antMatchers("/api/*/catalog").hasAnyRole("ROUSER", "RWUSER")
			.antMatchers(AUTH_WHITE_LIST).permitAll()
			.and().httpBasic();
		
	} 
	
}
