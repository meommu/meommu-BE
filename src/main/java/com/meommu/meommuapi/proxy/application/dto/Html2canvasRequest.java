package com.meommu.meommuapi.proxy.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Html2canvasRequest {

	@NotNull(message = "이미지 url은 필수로 입력해야합니다.")
	private String url;

	private Html2canvasRequest() {
	}

	@Builder
	private Html2canvasRequest(String url) {
		this.url = url;
	}
}
