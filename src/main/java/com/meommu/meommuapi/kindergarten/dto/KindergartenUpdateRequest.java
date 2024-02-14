package com.meommu.meommuapi.kindergarten.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KindergartenUpdateRequest {

	@NotBlank(message = "유치원 이름은 반드시 입력해야 합니다.")
	private String name;

	@NotBlank(message = "유치원 대표자명은 반드시 입력해야 합니다.")
	private String ownerName;

	@NotBlank(message = "전화번호는 반드시 입력해야 합니다.")
	private String phone;

	private KindergartenUpdateRequest() {
	}

	@Builder
	private KindergartenUpdateRequest(String name, String ownerName, String phone) {
		this.name = name;
		this.ownerName = ownerName;
		this.phone = phone;
	}
}
