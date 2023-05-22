package com.mask.todolist.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private final JwtUtil jwtUtil;

	@Autowired
	public JwtAuthFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

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

		// 跳過
		if (SkipPath(req.getRequestURI())) {
			filterChain.doFilter(req, res);
			return;
		}

		// 驗證 Token
		try {

			boolean valid = jwtUtil.checkToken(token);

			if (valid) {
				// 建立完成驗證的 Authentication
				JwtAuthToken auth = new JwtAuthToken(token, jwtUtil.extractID(token));
				// 存入 Context
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			System.out.println(e);

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
			return;
		}

		filterChain.doFilter(req, res);

	}

	/**
	 * 確認此次請求是否需要驗證
	 */
	public boolean SkipPath(String url) {
		String[] needCheckURL = {
				"/user/info",
		};
		List<String> list = Arrays.asList(needCheckURL);

		// 反轉boolean，不在的為true
		return !list.contains(url);
	}

}
