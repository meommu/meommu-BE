package com.meommu.meommuapi.kindergarten.controller;

import static com.meommu.meommuapi.util.documentation.DocumentFormatGenerator.*;
import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.meommu.meommuapi.common.util.JsonUtils;
import com.meommu.meommuapi.kindergarten.dto.SignUpRequest;
import com.meommu.meommuapi.kindergarten.exception.DuplicateEmailException;
import com.meommu.meommuapi.util.ControllerTest;

@DisplayName("유치원 API")
class KindergartenControllerTest extends ControllerTest {

	@DisplayName("회원가입: 성공 -> 201")
	@Test
	void testSignUp() throws Exception {
		// given
		var signUpRequest = SignUpRequest.builder()
			.name("멈무유치원")
			.ownerName("홍길동")
			.phone("010-0000-0000")
			.email("meommu@exam.com")
			.password("Password1!")
			.passwordConfirmation("Password1!")
			.build();

		doNothing().when(kindergartenService).signUp(any());

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/kindergartens/signup")
			.content(JsonUtils.toJson(signUpRequest))
			.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpectAll(
			status().isCreated()
		).andDo(
			MockMvcResultHandlers.print()
		).andDo(
			document("kindergartens/signUp/success",
				getDocumentRequest(), getDocumentResponse(),
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("유치원이름")
						.attributes(
							getConstraints("constraints", "1~18자 사이여야 합니다.")),
					fieldWithPath("ownerName").type(JsonFieldType.STRING).description("원장님 성함")
						.attributes(
							getConstraints("constraints", "2~18자 사이여야 합니다.")),
					fieldWithPath("phone").type(JsonFieldType.STRING).description("전화번호")
						.attributes(
							getConstraints("constraints", "xxx-xxxx-xxxx 의 형식이어야 합니다.")),
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
						.attributes(
							getConstraints("constraints", "이메일 형식이어야 합니다.")),
					fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
						.attributes(
							getConstraints("constraints", "8~20자 사이여야 합니다. 숫자와 특수기호가 각각 한 글자 이상 포함되어야 합니다.")),
					fieldWithPath("passwordConfirmation").type(JsonFieldType.STRING).description("비밀번호 확인")
						.attributes(
							getConstraints("constraints", "password와 값이 같아야합니다."))
				)
			)
		);
	}

	@DisplayName("이메일 중복 확인: 성공 -> 200")
	@Test
	void testCheckEmailDuplication() throws Exception {
		// given
		doNothing().when(kindergartenService).existsByEmail(any());

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/kindergartens/email?email=meommu@exam.com")
		);

		// then
		resultActions.andExpectAll(
			status().isOk()
		).andDo(
			MockMvcResultHandlers.print()
		).andDo(
			document("kindergartens/email/success",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("email").description("중복 확인 이메일")
						.attributes(getConstraints("constraints", "이메일 형식이어야 합니다."))
				)
			)
		);
	}

	@DisplayName("이메일 중복 확인: 중복 -> 400")
	@Test
	void testCheckEmailDuplicationWhenDuplicated() throws Exception { //TODO 테스트 속도 체크
		// given
		willThrow(new DuplicateEmailException()).given(kindergartenService).existsByEmail(any());

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/kindergartens/email?email=meommu@exam.com")
		);

		// then
		resultActions.andExpectAll(
			status().isBadRequest()
		).andDo(
			MockMvcResultHandlers.print()
		).andDo(
			document("kindergartens/email/fail",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("email").description("중복 확인 이메일")
						.attributes(getConstraints("constraints", "이메일 형식이어야 합니다."))
				)
			)
		);
	}
}