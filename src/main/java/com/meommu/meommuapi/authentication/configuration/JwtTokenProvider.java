package com.meommu.meommuapi.authentication.configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.meommu.meommuapi.authentication.dto.AuthInfo;
import com.meommu.meommuapi.authentication.exception.JwtException;
import com.meommu.meommuapi.authentication.exception.errorCode.AuthErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	private static final String AUTHORIZATION_ID = "id";

	private final Key signingKey;

	private final long validityAccessInMilliseconds;

	private final long validityRefreshInMilliseconds;

	public JwtTokenProvider(
		@Value("${security.jwt.token.secret-key}") String signingKey,
		@Value("${security.jwt.token.expire-length.access}") long validityAccessInMilliseconds,
		@Value("${security.jwt.token.expire-length.refresh}") long validityRefreshInMilliseconds
	) {
		byte[] keyBytes = signingKey.getBytes(StandardCharsets.UTF_8);
		this.signingKey = Keys.hmacShaKeyFor(keyBytes);
		this.validityAccessInMilliseconds = validityAccessInMilliseconds;
		this.validityRefreshInMilliseconds = validityRefreshInMilliseconds;
	}

	public String createAccessToken(Long id) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityAccessInMilliseconds);
		return Jwts.builder()
			.claim(AUTHORIZATION_ID, id)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(signingKey)
			.compact();
	}

	public String createRefreshToken(Long id) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityRefreshInMilliseconds);
		return Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(signingKey)
			.compact();
	}

	public AuthInfo getParsedAuthInfo(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token);
			return AuthInfo.from(claimsJws.getBody().get(AUTHORIZATION_ID));
		} catch (ExpiredJwtException e) {
			return AuthInfo.from(e.getClaims().get(AUTHORIZATION_ID));
		}
	}

	public void validateAccessToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token : {}", token);
			throw new JwtException(AuthErrorCode.UNSUPPORTED_JWT);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token : {}", token);
			throw new JwtException(AuthErrorCode.EXPIRED_JWT);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token : {}", token);
			throw new JwtException(AuthErrorCode.MALFORMED_JWT);
		}
	}

	public void validateRefreshToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token : {}", token);
			throw new JwtException(AuthErrorCode.UNSUPPORTED_JWT);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token : {}", token);
			throw new JwtException(AuthErrorCode.EXPIRED_JWT);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token : {}", token);
			throw new JwtException(AuthErrorCode.MALFORMED_JWT);
		}
	}

	public Long getExpiration(String token) {
		Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
		return claims.getExpiration().getTime();
	}
}