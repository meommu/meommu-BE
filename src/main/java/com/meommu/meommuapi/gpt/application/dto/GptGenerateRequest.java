package com.meommu.meommuapi.gpt.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GptGenerateRequest {

	@NotBlank(message = "디테일은 공백이 될 수 없습니다.")
	private String details;

	private GptGenerateRequest() {
	}

	@Builder
	private GptGenerateRequest(String details) {
		this.details = details;
	}
}
