package com.meommu.meommuapi.kindergarten.application;

public record SendCodeEvent(
	String email,
	String code
) {
}
