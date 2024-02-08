package com.meommu.meommuapi.core.auth.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRefreshTokenRepository implements RefreshTokenRepository {

	private final RedisTemplate<Object, Object> refreshTokenTemplate;

	public RedisRefreshTokenRepository(RedisTemplate<Object, Object> refreshTokenTemplate) {
		this.refreshTokenTemplate = refreshTokenTemplate;
	}

	@Override
	public void save(Long userId, String refreshToken, Long milliseconds) {
		String key = "refresh_token:" + userId;
		refreshTokenTemplate.opsForValue().set(key, refreshToken, milliseconds, TimeUnit.MILLISECONDS);
	}

	@Override
	public String findByUserId(Long userId) {
		String key = "refresh_token:" + userId;
		return String.valueOf(refreshTokenTemplate.opsForValue().get(key));

	}

	@Override
	public boolean deleteByUserId(Long userId) {
		String key = "refresh_token:" + userId;
		Boolean isDeleted = refreshTokenTemplate.delete(key);
		return isDeleted != null && isDeleted;
	}
}
