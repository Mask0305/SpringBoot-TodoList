package com.mask.todolist.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mask.todolist.exception.error.AuthException;
import com.mask.todolist.model.User;
import com.mask.todolist.repository.RedisRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

/**
 * JWT 各種函式
 */
@Component
public class JwtUtil {

	private String secret = "Mask Spring-Boot";
	public final long expDay = 7;
	private String issuer = "http://mask.com";

	@Autowired
	private RedisRepo redisRepo;

	/**
	 * 產生 Token
	 */
	public String generateToken(User user) {

		// 設置到期日
		LocalDateTime expTime = LocalDateTime.now().plusDays(expDay);
		Instant exp = expTime.atZone(ZoneId.systemDefault()).toInstant();

		// 初始化Body
		Claims claims = Jwts.claims();

		// 設置主題
		claims.setSubject(user.getAccount());
		// 設置 Token 內容
		claims.put("id", user.getId());
		claims.put("account", user.getAccount());
		claims.put("name", user.getName());
		// 設置發行方
		claims.setIssuer(issuer);
		// 設定到期
		claims.setExpiration(Date.from(exp));

		return Jwts.builder()
				// 放入內容
				.setClaims(claims)
				// 加密方式與密鑰
				.signWith(SignatureAlgorithm.HS256, secret)
				// 轉成字串
				.compact();
	}

	/**
	 * 驗證 Token
	 */
	public boolean checkToken(String token) throws Exception {

		try {
			// 傳入加密後的JWT Token
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

			// 檢查到期日
			isExpired(token);

			// 檢查是否有在Redis中
			isExistInRedis(token);

			return true;
		} catch (Exception e) {
			System.out.println(e);
			throw new AuthException().AuthFail(e);
		}

	}

	/**
	 * 取得Requet中的Token
	 */
	public String getToken(HttpServletRequest req) {
		// 從 header 取得
		final String bearerToken = req.getHeader("Authorization");

		// hasText() 檢查傳入的 String 是否為null、是否為空、是否都是空格
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

			// 返回除了前綴的主要token內容
			return bearerToken.substring(7);
		}

		return null;
	}

	/**
	 * 取得token裡的id
	 */
	public Long extractID(String token) {
		// 解出來的值只能是 Object
		Object id = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("id");
		// Object自帶toString()，再轉成Long
		return Long.valueOf(id.toString());
	}

	/**
	 * 取得token存放的資料
	 */
	public Claims extractAllClaims(String token) {
		// 注意使用的parseClaimsJw's'，不是parseClaimsJw't'
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * 從Token取得指定的值
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * 驗證 Token 是否過期
	 */
	public void isExpired(String token) throws Exception {
		// 取得到期日
		Date exp = extractClaim(token, Claims::getExpiration);
		if (exp.before(new Date())) {
			throw new AuthException().AuthExpired(new IllegalAccessException());
		}
	}

	/**
	 * 檢查Token是否有在Redis中
	 */
	public void isExistInRedis(String token) throws Exception {
		// 取出token中的使用者編號
		Long id = extractID(token);
		System.out.println("ID:" + id);
		System.out.println(this.redisRepo);
		// 查詢redis
		String tokenInRedis = redisRepo.get(id.toString());
		System.out.println("token: " + tokenInRedis);
		// 比對token是否相同
		if (!tokenInRedis.equals(token)) {
			throw new IllegalAccessError();
		}

	}

}
