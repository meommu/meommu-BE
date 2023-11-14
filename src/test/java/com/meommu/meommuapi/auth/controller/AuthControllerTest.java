package com.meommu.meommuapi.auth.controller;

import static com.meommu.meommuapi.util.documentation.DocumentFormatGenerator.*;
import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.meommu.meommuapi.auth.dto.SignInRequest;
import com.meommu.meommuapi.auth.dto.TokenResponse;
import com.meommu.meommuapi.common.util.JsonUtils;
import com.meommu.meommuapi.util.ControllerTest;

@DisplayName("인증 API")
class AuthControllerTest extends ControllerTest {

	@DisplayName("로그인: 성공 -> 201")
	@Test
	void testSignIn() throws Exception {
		// given
		var signInRequest = SignInRequest.builder()
			.email("meommu@exam.com")
			.password("Password1!")
			.build();
		var tokenResponse = TokenResponse.builder()
			.accessToken("<ACCESS_TOKEN>")
			.build();
		given(authService.signIn(any())).willReturn(tokenResponse);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/kindergartens/signin")
			.content(JsonUtils.toJson(signInRequest))
			.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpectAll(
			status().isCreated(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.accessToken").value("<ACCESS_TOKEN>")
		).andDo(
			document("kindergartens/signIn/success",
				getDocumentRequest(), getDocumentResponse(),
				requestFields(
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
						.attributes(
							getConstraints("constraints", "이메일 형식이어야 합니다. ^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")),
					fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
						.attributes(
							getConstraints("constraints", "8~20자 사이여야 합니다. 숫자와 특수기호가 각각 한 글자 이상 포함되어야 합니다."))
				)
			)
		);
	}
}