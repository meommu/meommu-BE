package com.meommu.meommuapi.guide.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GuideDetailSaveRequest {

	@NotBlank(message = "디테일은 공백이 될 수 없습니다.")
	private String detail;

	private GuideDetailSaveRequest() {
	}

	@Builder
	private GuideDetailSaveRequest(String detail) {
		this.detail = detail;
	}
}
