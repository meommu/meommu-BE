package com.meommu.meommuapi.kindergarten.dto;

import java.time.LocalDateTime;

import com.meommu.meommuapi.kindergarten.domain.Kindergarten;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyInfoResponse {

	private Long id;

	private String name;

	private String email;

	private LocalDateTime createdAt;

	private MyInfoResponse() {
	}

	@Builder
	private MyInfoResponse(Long id, String name, String email, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.createdAt = createdAt;
	}

	public static MyInfoResponse from(Kindergarten kindergarten) {
		return MyInfoResponse.builder()
			.id(kindergarten.getId())
			.name(kindergarten.getName())
			.email(kindergarten.getEmail())
			.createdAt(kindergarten.getCreatedAt())
			.build();
	}
}