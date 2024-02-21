package com.meommu.meommuapi.gpt.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GptGenerateResponse {

	private String content;

	private GptGenerateResponse() {
	}

	@Builder
	private GptGenerateResponse(String content) {
		this.content = content;
	}

	public static GptGenerateResponse from(String content) {
		return GptGenerateResponse.builder()
			.content(content)
			.build();
	}
}

