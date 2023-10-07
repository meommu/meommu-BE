package com.meommu.meommuapi.image.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImagesSaveRequest {

	@NotBlank(message = "카테고리는 반드시 입력해야 합니다.")
	String category;

	@NotNull(message = "이미지는 최소 1장 이상 업로드해야 합니다.")
	List<MultipartFile> images;

	@Builder
	private ImagesSaveRequest(List<MultipartFile> images, String category) {
		this.images = images;
		this.category = category;
	}
}
