package com.mask.todolist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter = new JwtAuthFilter();

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		String[] userAllowPath = { "/user/login", "/user/register" };

		http
				// 禁用CSRF（跨站請求偽造）保護
				.csrf().disable()
				// 不使用Sesseion
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// 設定完SecurityConfig時使用，並返回SecurityBuilder
				.and()
				// 開始設置Request授權規則
				.authorizeHttpRequests()
				// 允許請求，即不受middleware限制
				.requestMatchers("/user/**").permitAll();

		/*
		 * 將自定義的過濾器放在User..Filter之前
		 * User...Filter是框架預設過濾器中的最後一個
		 */
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
			throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
