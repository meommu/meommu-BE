package com.meommu.meommuapi.auth.application.dto;

import lombok.Getter;

@Getter
public class AuthInfo {

	private final Long id;

	public AuthInfo(Long id) {
		this.id = id;
	}

	public static AuthInfo from(Object idObject) {
		int id = (int)idObject;
		return new AuthInfo((long)id);
	}
}
