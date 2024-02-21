package com.meommu.meommuapi.kindergarten.application.dto;

import com.meommu.meommuapi.kindergarten.domain.Kindergarten;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KindergartenResponse {

	private Long id;

	private String name;

	private String ownerName;

	private String phone;

	private String email;

	private KindergartenResponse() {
	}

	@Builder
	private KindergartenResponse(Long id, String name, String ownerName, String phone, String email) {
		this.id = id;
		this.name = name;
		this.ownerName = ownerName;
		this.phone = phone;
		this.email = email;
	}

	public static KindergartenResponse from(Kindergarten kindergarten) {
		return KindergartenResponse.builder()
			.id(kindergarten.getId())
			.name(kindergarten.getName())
			.ownerName(kindergarten.getOwnerName())
			.phone(kindergarten.getPhone())
			.email(kindergarten.getEmail())
			.build();
	}
}