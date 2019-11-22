package com.learning.service.moviecatalog.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
	
	
	String password;
	String username;
	boolean isAccountNonExpired;
	boolean isAccountNonLocked;
	boolean isCredentialsNonExpired;
	boolean isEnabled;
	Collection<GrantedAuthority> authorities;
	
	
	
	

}
