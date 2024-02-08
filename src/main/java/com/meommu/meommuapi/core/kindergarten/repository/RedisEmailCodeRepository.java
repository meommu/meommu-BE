package com.meommu.meommuapi.core.kindergarten.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisEmailCodeRepository implements EmailCodeRepository {

	private final RedisTemplate<Object, Object> emailTemplate;

	public RedisEmailCodeRepository(RedisTemplate<Object, Object> emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	@Override
	public void save(String email, String code, Long milliseconds) {
		String key = "email_code:" + email;
		emailTemplate.opsForValue().set(key, code, milliseconds, TimeUnit.MILLISECONDS);
	}

	@Override
	public String findByEmail(String email) {
		String key = "email_code:" + email;
		return String.valueOf(emailTemplate.opsForValue().get(key));
	}

	@Override
	public boolean deleteByEmail(String email) {
		String key = "email_code:" + email;
		Boolean isDeleted = emailTemplate.delete(key);
		return isDeleted != null && isDeleted;

	}
}
