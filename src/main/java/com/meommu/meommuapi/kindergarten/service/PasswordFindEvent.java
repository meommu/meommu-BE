package com.meommu.meommuapi.kindergarten.service;

public record PasswordFindEvent(
	String email,
	String title,
	String code
) {
}
