package com.mask.todolist.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthToken extends AbstractAuthenticationToken {

	private String token;

	public JwtAuthToken(String token) {
		super(null);
		this.token = token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	public String getToken() {
		return token;
	}

}
