package com.meommu.meommuapi.kindergarten.presentation;

import static com.meommu.meommuapi.util.documentation.DocumentConstant.*;
import static com.meommu.meommuapi.util.documentation.DocumentFormatGenerator.*;
import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.meommu.meommuapi.kindergarten.application.dto.KindergartenPasswordUpdateRequest;
import com.meommu.meommuapi.kindergarten.application.dto.KindergartenResponse;
import com.meommu.meommuapi.kindergarten.application.dto.KindergartenUpdateRequest;
import com.meommu.meommuapi.kindergarten.application.dto.MyInfoResponse;
import com.meommu.meommuapi.kindergarten.application.dto.SignUpRequest;
import com.meommu.meommuapi.common.util.JsonUtils;
import com.meommu.meommuapi.util.ControllerTest;

@DisplayName("유치원 API")
class KindergartenControllerTest extends ControllerTest {

	@DisplayName("회원가입: 성공 -> 201")
	@Test
	void signUp() throws Exception {
		// given
		var signUpRequest = SignUpRequest.builder()
			.name("멈무유치원")
			.ownerName("홍길동")
			.phone("010-0000-0000")
			.email("meommu@exam.com")
			.password("Password1!")
			.passwordConfirmation("Password1!")
			.build();
		var myInfoResponse = MyInfoResponse.builder()
			.id(1L)
			.name("멈무유치원")
			.email("meommu@exam.com")
			.createdAt(LocalDateTime.now())
			.build();
		given(kindergartenService.signUp(any())).willReturn(myInfoResponse);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/kindergartens/signup")
			.content(JsonUtils.toJson(signUpRequest))
			.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpectAll(
			status().isCreated(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.id").value(1L),
			jsonPath("$.data.name").value("멈무유치원"),
			jsonPath("$.data.email").value("meommu@exam.com"),
			jsonPath("$.data.createdAt").isNotEmpty()

		).andDo(
			document("kindergartens/signUp/success",
				getDocumentRequest(), getDocumentResponse(),
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("유치원 이름")
						.attributes(
							getConstraints("constraints", "2~13자 사이여야 합니다.")),
					fieldWithPath("ownerName").type(JsonFieldType.STRING).description("원장님 이름")
						.attributes(
							getConstraints("constraints", "2~8자 사이여야 합니다.")),
					fieldWithPath("phone").type(JsonFieldType.STRING).description("전화번호")
						.attributes(
							getConstraints("constraints", "xxx-xxxx-xxxx 의 형식이어야 합니다.")),
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
						.attributes(
							getConstraints("constraints", "이메일 형식이어야 합니다. ^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")),
					fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
						.attributes(
							getConstraints("constraints", "8~20자 사이여야 합니다. 알파벳, 숫자와 특수기호가 각각 한 글자 이상 포함되어야 합니다.")),
					fieldWithPath("passwordConfirmation").type(JsonFieldType.STRING).description("비밀번호 확인")
						.attributes(
							getConstraints("constraints", "password와 값이 같아야합니다."))
				)
			)
		);
	}

	@DisplayName("이메일 중복 확인: 성공 -> 200")
	@Test
	void checkEmailDuplication() throws Exception {
		// given
		given(kindergartenService.existsByEmail(any())).willReturn(true);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/kindergartens/email?email=meommu@exam.com")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").value(true)
		).andDo(
			document("kindergartens/email/success",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("email").description("중복 확인 이메일")
						.attributes(
							getConstraints("constraints", "이메일 형식이어야 합니다. ^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
				)
			)
		);
	}

	@DisplayName("이메일 중복 확인: 중복 -> 200")
	@Test
	void checkEmailDuplicationWhenDuplicated() throws Exception {
		// given
		given(kindergartenService.existsByEmail(any())).willReturn(false);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/kindergartens/email?email=meommu@exam.com")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").value(false)
		).andDo(
			document("kindergartens/email/fail",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("email").description("중복 확인 이메일")
						.attributes(
							getConstraints("constraints", "이메일 형식이어야 합니다. ^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
				)
			)
		);
	}

	@DisplayName("토큰에서 회원 정보 추출: 성공 -> 200")
	@Test
	void findMyInfo() throws Exception {
		// given
		var myInfoResponse = MyInfoResponse.builder()
			.id(1L)
			.name("멈무유치원")
			.email("meommu@exam.com")
			.createdAt(LocalDateTime.now())
			.build();

		given(kindergartenService.findMyInfo(any())).willReturn(myInfoResponse);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/kindergartens/me")
			.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.id").value(1L),
			jsonPath("$.data.name").value("멈무유치원"),
			jsonPath("$.data.email").value("meommu@exam.com"),
			jsonPath("$.data.createdAt").isNotEmpty()
		).andDo(
			document("kindergartens/me/success",
				getDocumentRequest(), getDocumentResponse()
			)
		);
	}

	@DisplayName("조회: 성공 -> 200")
	@Test
	void findKindergarten() throws Exception {
		// given
		var kindergartenResponse = KindergartenResponse.builder()
			.id(1L)
			.name("멈무유치원")
			.ownerName("홍길동")
			.phone("010-0000-0000")
			.email("meommu@exam.com")
			.build();

		given(kindergartenService.find(any())).willReturn(kindergartenResponse);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/kindergartens/info")
			.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.id").value(1L),
			jsonPath("$.data.name").value("멈무유치원"),
			jsonPath("$.data.ownerName").value("홍길동"),
			jsonPath("$.data.phone").value("010-0000-0000"),
			jsonPath("$.data.email").value("meommu@exam.com")
		).andDo(
			document("kindergartens/get/success",
				getDocumentRequest(), getDocumentResponse()
			)
		);
	}

	@DisplayName("수정: 성공 -> 200")
	@Test
	void updateKindergarten() throws Exception {
		// given
		var kindergartenUpdateRequest = KindergartenUpdateRequest.builder()
			.name("멈무유치원")
			.ownerName("홍길동")
			.phone("010-0000-0000")
			.build();

		doNothing().when(kindergartenService).update(any(), any());

		// when
		ResultActions resultActions = mockMvc.perform(patch("/api/v1/kindergartens/info")
			.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
			.content(JsonUtils.toJson(kindergartenUpdateRequest))
			.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.name").doesNotExist()
		).andDo(
			document("kindergartens/update/success",
				getDocumentRequest(), getDocumentResponse(),
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("유치원 이름")
						.attributes(
							getConstraints("constraints", "2~13자 사이여야 합니다.")),
					fieldWithPath("ownerName").type(JsonFieldType.STRING).description("원장님 이름")
						.attributes(
							getConstraints("constraints", "2~8자 사이여야 합니다.")),
					fieldWithPath("phone").type(JsonFieldType.STRING).description("전화번호")
						.attributes(
							getConstraints("constraints", "xxx-xxxx-xxxx 의 형식이어야 합니다."))
				)
			)
		);
	}

	@DisplayName("탈퇴: 성공 -> 200")
	@Test
	void deleteDiary() throws Exception {
		// given
		doNothing().when(kindergartenService).delete(any());

		// when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.delete("/api/v1/kindergartens")
				.header(AUTHORIZATION, "bearer <ACCESS_TOKEN>"));

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").doesNotExist()
		).andDo(
			document("kindergartens/delete/success",
				getDocumentRequest(), getDocumentResponse()
			)
		);
	}

	@DisplayName("비밀번호 찾기 이메일 전송: 성공 -> 200")
	@Test
	void sendCodeToEmail() throws Exception {
		// given
		doNothing().when(kindergartenService).sendCodeToEmail(any());

		// when
		ResultActions resultActions = mockMvc.perform(
			post("/api/v1/kindergartens/email/verification-request?email=meommu@email.com")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").doesNotExist()
		).andDo(
			document("kindergartens/email-code-request/success",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("email").description("비밀번호를 수정하고자 하는 이메일")
				)
			)
		);
	}

	@DisplayName("비밀번호 찾기 이메일 코드 확인: 일치 -> 200")
	@Test
	void verificationCode() throws Exception {
		// given
		given(kindergartenService.verifiedCode(any(), any())).willReturn(true);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/kindergartens/email/verification?email=meommu@email.com&code=123456")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").value(true)
		).andDo(
			document("kindergartens/email-code-verification/success",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("email").description("비밀번호를 수정하고자 하는 이메일"),
					parameterWithName("code").description("이메일로 받은 코드")
						.attributes(
							getConstraints("constraints", "6글자의 숫자 입니다."))
				)
			)
		);
	}

	@DisplayName("비밀번호 찾기 이메일 코드 확인: 불일치 -> 200")
	@Test
	void verificationCodeWhenNotVerified() throws Exception {
		// given
		given(kindergartenService.verifiedCode(any(), any())).willReturn(false);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/kindergartens/email/verification?email=meommu@email.com&code=123456")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").value(false)
		).andDo(
			document("kindergartens/email-code-verification/fail",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("email").description("비밀번호를 수정하고자 하는 이메일"),
					parameterWithName("code").description("이메일로 받은 코드")
						.attributes(
							getConstraints("constraints", "6글자의 숫자 입니다."))
				)
			)
		);
	}

	@DisplayName("비밀번호 수정: 성공 -> 200")
	@Test
	void updatePassword() throws Exception {
		// given
		var request = KindergartenPasswordUpdateRequest.builder()
			.password("Password1!")
			.passwordConfirmation("Password1!")
			.build();

		doNothing().when(kindergartenService).updatePassword(any(), any());

		// when
		ResultActions resultActions = mockMvc.perform(patch("/api/v1/kindergartens/password?email=meommu@email.com")
			.content(JsonUtils.toJson(request))
			.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").doesNotExist()
		).andDo(
			document("kindergartens/update-password/success",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("email").description("비밀번호를 수정하고자 하는 이메일")
				),
				requestFields(
					fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
						.attributes(
							getConstraints("constraints", "8~20자 사이여야 합니다. 알파벳, 숫자와 특수기호가 각각 한 글자 이상 포함되어야 합니다.")),
					fieldWithPath("passwordConfirmation").type(JsonFieldType.STRING).description("비밀번호 확인")
						.attributes(
							getConstraints("constraints", "password와 값이 같아야합니다."))
				)
			)
		);
	}
}