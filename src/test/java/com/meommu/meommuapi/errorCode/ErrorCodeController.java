package com.meommu.meommuapi.errorCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.auth.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.common.exception.errorCode.BusinessErrorCode;
import com.meommu.meommuapi.common.exception.errorCode.ErrorCode;
import com.meommu.meommuapi.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.gpt.exception.errorCode.GptErrorCode;
import com.meommu.meommuapi.guide.exception.errorCode.GuideErrorCode;
import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.notice.exception.errorCode.NoticeErrorCode;

@RestController
public class ErrorCodeController {

	@GetMapping("/error-code")
	public Map<String, ErrorCodeResponse> findErrorCodes() {
		List<ErrorCode> errorCodes = new ArrayList<>();
		errorCodes.addAll(Arrays.asList(BusinessErrorCode.values()));
		errorCodes.addAll(Arrays.asList(AuthErrorCode.values()));
		errorCodes.addAll(Arrays.asList(KindergartenErrorCode.values()));
		errorCodes.addAll(Arrays.asList(GptErrorCode.values()));
		errorCodes.addAll(Arrays.asList(GuideErrorCode.values()));
		errorCodes.addAll(Arrays.asList(ImageErrorCode.values()));
		errorCodes.addAll(Arrays.asList(DiaryErrorCode.values()));
		errorCodes.addAll(Arrays.asList(NoticeErrorCode.values()));

		Map<String, ErrorCodeResponse> map = new LinkedHashMap<>();

		for (ErrorCode errorCode : errorCodes) {
			map.put(errorCode.getName(), new ErrorCodeResponse(errorCode));
		}
		return map;
	}
}