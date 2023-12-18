package com.meommu.meommuapi.core.proxy.controller;

import static com.meommu.meommuapi.util.documentation.DocumentFormatGenerator.*;
import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.meommu.meommuapi.util.ControllerTest;

@DisplayName("프록시 API")
class ProxyControllerTest extends ControllerTest {

	@DisplayName("Html2Canvas 변환: 성공 -> 200")
	@Test
	void html2canvasProxy() throws Exception {
		// given
		var url = "test.com";
		var byteValue = "ByteValue";
		var byteArray = byteValue.getBytes(StandardCharsets.UTF_8);
		given(proxyService.html2canvasProxy(any())).willReturn(byteArray);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/proxy")
			.param("url", URLEncoder.encode(url, StandardCharsets.UTF_8.name()))
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			content().string(byteValue)
		).andDo(
			document("proxy/html2canvas/success",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("url").description("이미지 url")
						.attributes(getConstraints("constraints", "이미지 조회 api를 통해 얻은 이미지 url을 입력해야 합니다."))
				)
			)
		);
	}
}