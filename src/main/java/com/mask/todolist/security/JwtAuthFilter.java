package com.mask.todolist.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mask.todolist.controller.dto.Response;
import com.mask.todolist.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Jwt 驗證中間層
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil = new JwtUtil();

	/**
	 * 主要的中間層邏輯
	 */
	@Override
	protected void doFilterInternal(
			HttpServletRequest req,
			HttpServletResponse res,
			FilterChain filterChain) throws ServletException, IOException {

		// 取得token
		String token = jwtUtil.getToken(req);

		// 驗證 Token
		try {

			boolean valid = jwtUtil.checkToken(token);

			if (valid) {
				Jwt jwt = jwtUtil.getTokenJwt(token);
				JwtAuthenticationToken auth = new JwtAuthenticationToken(jwt, null, token);

				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			filterChain.doFilter(req, res);
		} catch (Exception e) {

			// status code 401
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			// 預設是ISO-8859，會編譯不了中文
			res.setCharacterEncoding("UTF-8");
			// application/json
			res.setContentType(MediaType.APPLICATION_JSON_VALUE);

			Response resBody = new Response().Error().ErrorMessage(e);
			// 將Response轉成Json格式的字串
			String resJson = new ObjectMapper()
					.writeValueAsString(resBody);

			// 取得輸出流,寫入回傳內容
			res.getWriter().write(resJson);
			// 回傳給Client
			res.getWriter().flush();
			// 關閉輸出流
			res.getWriter().close();
		}

	}

}
