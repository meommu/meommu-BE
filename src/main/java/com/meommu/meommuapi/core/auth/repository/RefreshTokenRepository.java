package com.meommu.meommuapi.core.auth.repository;

import org.springframework.stereotype.Repository;

public interface RefreshTokenRepository {

	void save(Long userId, String refreshToken, Long milliseconds);

	String findByUserId(Long userId);

	boolean deleteByUserId(Long userId);
}
