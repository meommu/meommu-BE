package com.meommu.meommuapi.kindergarten.service;

public record SendCodeEvent(
	String email,
	String code
) {
}
