package com.meommu.meommuapi.kindergarten.dto;

import com.meommu.meommuapi.kindergarten.domain.Kindergarten;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyInfoBasicResponse {

	private Long id;
	private String email;

	private MyInfoBasicResponse() {
	}

	@Builder
	private MyInfoBasicResponse(final Long id, final String email) {
		this.id = id;
		this.email = email;
	}

	public static MyInfoBasicResponse from(Kindergarten kindergarten) {
		return MyInfoBasicResponse.builder()
			.id(kindergarten.getId())
			.email(kindergarten.getEmail())
			.build();
	}
}