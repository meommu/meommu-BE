package com.meommu.meommuapi.proxy.exception.errorCode;

import com.meommu.meommuapi.core.exception.errorCode.ErrorCode;

public enum ProxyErrorCode implements ErrorCode {

	HTML_2_CANVAS_ERROR("P001", "Html2canvas 프록시 처리 과정에서 오류가 발생했습니다. URL을 확인하거나 관리자에게 문의하세요.");

	private final String code;

	private final String description;

	ProxyErrorCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public String getName() {
		return this.name();
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
