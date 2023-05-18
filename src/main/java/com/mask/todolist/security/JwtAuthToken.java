package com.mask.todolist.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.mask.todolist.util.JwtUtil;

public class JwtAuthToken extends AbstractAuthenticationToken {

	private final JwtUtil jwtUtil = new JwtUtil();

	private String token;

	public JwtAuthToken(String token) {
		super(null);
		this.token = token;
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
	public Object getPrincipal() {
		return jwtUtil.extractID(token);
	}
}
