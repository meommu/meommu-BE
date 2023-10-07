package com.meommu.meommuapi.image.exception.errorCode;

import com.meommu.meommuapi.common.exception.errorCode.ErrorCode;

public enum ImageErrorCode implements ErrorCode {

	IMAGE_NOT_FOUND("I001", "id로 이미지를 찾을 수 없음"),
	INVALID_IMAGE_TYPE("I002", "올바르지 않은 이미지 타입"),
	CATEGORY_NOT_FOUND("I003", "일치하는 이미지 카테고리를 찾을 수 없음"),
	IMAGE_MAX_SIZE_ERROR("I004", "이미지 최대 개수 오류"),
	IMAGE_DELETE_FAIL("I005", "이미지 삭제 실패"),
	IMAGE_UPLOAD_FAIL("I006", "이미지 업로드 실패"),
	;

	private final String code;

	private final String description;

	ImageErrorCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
}
