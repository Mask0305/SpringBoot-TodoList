package com.mask.todolist.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthToken extends AbstractAuthenticationToken {

	private String token;
	private Long principal;

	public JwtAuthToken(String token, Long id) {
		super(null);
		this.token = token;
		this.principal = id;
	}

	/**
	 * 驗證資訊
	 */
	@Override
	public Object getCredentials() {
		return token;
	}

	/**
	 * 身份資訊,使用者編號
	 */
	@Override
	public Long getPrincipal() {
		return this.principal;
	}
}
