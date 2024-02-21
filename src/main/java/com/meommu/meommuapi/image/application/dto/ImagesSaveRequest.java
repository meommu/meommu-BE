package com.meommu.meommuapi.image.application.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImagesSaveRequest {

	@NotBlank(message = "카테고리는 반드시 입력해야 합니다.")
	String category;

	List<MultipartFile> images;

	@Builder
	private ImagesSaveRequest(List<MultipartFile> images, String category) {
		this.images = images;
		this.category = category;
	}
}
