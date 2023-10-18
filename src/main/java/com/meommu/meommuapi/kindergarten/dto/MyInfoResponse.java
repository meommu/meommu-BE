package com.meommu.meommuapi.kindergarten.dto;

import com.meommu.meommuapi.kindergarten.domain.Kindergarten;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyInfoResponse {

	private Long id;

	private String name;

	private String email;

	private MyInfoResponse() {
	}

	@Builder
	private MyInfoResponse(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public static MyInfoResponse from(Kindergarten kindergarten) {
		return MyInfoResponse.builder()
			.id(kindergarten.getId())
			.name(kindergarten.getName())
			.email(kindergarten.getEmail())
			.build();
	}
}