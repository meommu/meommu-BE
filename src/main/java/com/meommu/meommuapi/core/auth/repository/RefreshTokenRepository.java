package com.meommu.meommuapi.core.auth.repository;

public interface RefreshTokenRepository {

	void save(Long userId, String refreshToken, Long milliseconds);

	String findByUserId(Long userId);

	boolean deleteByUserId(Long userId);
}
