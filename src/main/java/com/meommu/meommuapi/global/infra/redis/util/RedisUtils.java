package com.meommu.meommuapi.global.infra.redis.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	private final RedisTemplate<String, Object> refreshTokenTemplate; // 리프레시 토큰 저장용 RedisTemplate

	private final RedisTemplate<String, Object> emailTemplate; // 리프레시 토큰 저장용 RedisTemplate

	public RedisUtils(RedisTemplate<String, Object> refreshTokenTemplate, RedisTemplate<String, Object> emailTemplate) {
		this.refreshTokenTemplate = refreshTokenTemplate;
		this.emailTemplate = emailTemplate;
	}

	public void setRefreshToken(Long userId, String refreshToken, long milliseconds) {
		String key = "refresh_token:" + userId;
		refreshTokenTemplate.opsForValue().set(key, refreshToken, milliseconds, TimeUnit.MILLISECONDS);
	}

	public String getRefreshToken(Long userId) {
		String key = "refresh_token:" + userId;
		return String.valueOf(refreshTokenTemplate.opsForValue().get(key));
	}

	public boolean deleteRefreshToken(Long userId) {
		String key = "refresh_token:" + userId;
		Boolean isDeleted = refreshTokenTemplate.delete(key);
		return isDeleted != null && isDeleted;
	}

	public void setEmailCode(Long userId, String code, long milliseconds) {
		String key = "email_code:" + userId;
		emailTemplate.opsForValue().set(key, code, milliseconds, TimeUnit.MILLISECONDS);
	}

	public boolean deleteEmailCode(Long userId) {
		String key = "email_code:" + userId;
		Boolean isDeleted = emailTemplate.delete(key);
		return isDeleted != null && isDeleted;
	}
}
