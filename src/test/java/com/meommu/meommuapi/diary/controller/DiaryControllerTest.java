package com.meommu.meommuapi.diary.controller;

import static com.meommu.meommuapi.util.documentation.DocumentConstant.*;
import static com.meommu.meommuapi.util.documentation.DocumentFormatGenerator.*;
import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.meommu.meommuapi.common.util.JsonUtils;
import com.meommu.meommuapi.diary.dto.DiaryResponse;
import com.meommu.meommuapi.diary.dto.DiaryResponses;
import com.meommu.meommuapi.diary.dto.DiarySaveRequest;
import com.meommu.meommuapi.diary.dto.DiarySaveResponse;
import com.meommu.meommuapi.diary.dto.DiarySummaryResponse;
import com.meommu.meommuapi.diary.dto.DiarySummaryResponses;
import com.meommu.meommuapi.diary.dto.DiaryUUIDResponse;
import com.meommu.meommuapi.diary.dto.DiaryUpdateRequest;
import com.meommu.meommuapi.util.ControllerTest;
import com.meommu.meommuapi.util.documentation.DocumentConstant;

@DisplayName("일기 API")
class DiaryControllerTest extends ControllerTest {

	LocalDate startDate;

	LocalDate endDate;

	DiaryResponse diaryResponse1;

	DiaryResponse diaryResponse2;

	DiaryResponses diaryResponses;

	List<Long> imageIds;

	@BeforeEach
	void setUp() {
		startDate = LocalDate.of(0000, 1, 1);
		endDate = LocalDate.of(9999, 12, 31);
		imageIds = List.of(1L, 2L, 3L, 4L, 5L);
		diaryResponse1 = DiaryResponse.builder()
			.id(1L)
			.date(LocalDate.now().minusDays(1))
			.dogName("코코")
			.title("일기 1 제목")
			.content("일기 1 내용")
			.createdAt(LocalDateTime.now())
			.imageIds(imageIds)
			.build();
		diaryResponse2 = DiaryResponse.builder()
			.id(2L)
			.date(LocalDate.now())
			.dogName("똘이")
			.title("일기 2 제목")
			.content("일기 2 내용")
			.createdAt(LocalDateTime.now())
			.imageIds(imageIds)
			.build();
		diaryResponses = DiaryResponses.builder()
			.diaryResponses(List.of(diaryResponse2, diaryResponse1))
			.build();
	}

	@DisplayName("요약 전체 조회: 성공 -> 200")
	@Test
	void findDiariesSummary() throws Exception {
		// given
		DiarySummaryResponse diarySummaryResponse1 = DiarySummaryResponse.builder()
			.id(1L)
			.date(LocalDate.now().minusDays(1))
			.createdAt(LocalDateTime.now())
			.imageIds(imageIds)
			.build();

		DiarySummaryResponse diarySummaryResponse2 = DiarySummaryResponse.builder()
			.id(2L)
			.date(LocalDate.now())
			.createdAt(LocalDateTime.now())
			.imageIds(imageIds)
			.build();
		DiarySummaryResponses diarySummaryResponses = DiarySummaryResponses.builder()
			.diarySimpleResponses(List.of(diarySummaryResponse2, diarySummaryResponse1))
			.build();

		given(diaryService.findDiariesSummary(any())).willReturn(diarySummaryResponses);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/diaries/summary")
			.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.diaries[0].id").value(2L),
			jsonPath("$.data.diaries[0].date").value(LocalDate.now().toString()),
			jsonPath("$.data.diaries[0].createdAt").isNotEmpty(),
			jsonPath("$.data.diaries[0].imageIds").isArray(),
			jsonPath("$.data.diaries[1].id").value(1L),
			jsonPath("$.data.diaries[1].date").value(LocalDate.now().minusDays(1).toString()),
			jsonPath("$.data.diaries[1].createdAt").isNotEmpty(),
			jsonPath("$.data.diaries[1].imageIds").isArray()
		).andDo(
			document("diaries/summary/success",
				getDocumentRequest(), getDocumentResponse()
			)
		);
	}

	@DisplayName("전체 조회: 성공 -> 200")
	@Test
	void findDiaries() throws Exception {
		// given
		given(diaryService.findDiaries(any(), any())).willReturn(diaryResponses);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/diaries")
			.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
			.param("year", String.valueOf(LocalDate.now().getYear()))
			.param("month", String.valueOf(LocalDate.now().getMonthValue()))
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.diaries[0].id").value(2L),
			jsonPath("$.data.diaries[0].date").value(LocalDate.now().toString()),
			jsonPath("$.data.diaries[0].dogName").value("똘이"),
			jsonPath("$.data.diaries[0].title").value("일기 2 제목"),
			jsonPath("$.data.diaries[0].content").value("일기 2 내용"),
			jsonPath("$.data.diaries[0].createdAt").isNotEmpty(),
			jsonPath("$.data.diaries[0].imageIds").isArray(),
			jsonPath("$.data.diaries[1].id").value(1L),
			jsonPath("$.data.diaries[1].date").value(LocalDate.now().minusDays(1).toString()),
			jsonPath("$.data.diaries[1].dogName").value("코코"),
			jsonPath("$.data.diaries[1].title").value("일기 1 제목"),
			jsonPath("$.data.diaries[1].content").value("일기 1 내용"),
			jsonPath("$.data.diaries[1].createdAt").isNotEmpty(),
			jsonPath("$.data.diaries[1].imageIds").isArray()
		).andDo(
			document("diaries/getAll/success",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("year").description("년")
						.attributes(getConstraints("constraints", "yyyy의 형식이어야 합니다.")),
					parameterWithName("month").description("월")
						.attributes(getConstraints("constraints", "MM의 형식이어야 합니다."))
				)
			)
		);
	}

	@DisplayName("조회: 성공 -> 200")
	@Test
	void findDiary() throws Exception {
		// given
		given(diaryService.findDiary(any(), any())).willReturn(diaryResponse1);

		// when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.get("/api/v1/diaries/{diaryId}", 1L)
				.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.id").value(1L),
			jsonPath("$.data.date").value(LocalDate.now().minusDays(1).toString()),
			jsonPath("$.data..dogName").value("코코"),
			jsonPath("$.data.title").value("일기 1 제목"),
			jsonPath("$.data.content").value("일기 1 내용"),
			jsonPath("$.data.createdAt").isNotEmpty(),
			jsonPath("$.data.imageIds").isArray()
		).andDo(
			document("diaries/get/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("diaryId").description("일기 id")
				)
			)
		);
	}

	@DisplayName("생성: 성공 -> 201")
	@Test
	void createDiary() throws Exception {
		// given
		DiarySaveRequest diarySaveRequest = DiarySaveRequest.builder()
			.date(LocalDate.now())
			.dogName("사랑이")
			.title("일기 제목")
			.content("일기 내용")
			.imageIds(imageIds)
			.build();
		DiarySaveResponse diarySaveResponse = DiarySaveResponse.builder()
			.savedId(1L)
			.build();
		given(diaryService.create(any(), any())).willReturn(diarySaveResponse);

		// when
		ResultActions resultActions = mockMvc.perform(
			post("/api/v1/diaries")
				.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
				.content(JsonUtils.toJson(diarySaveRequest))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpectAll(
			status().isCreated(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.savedId").value(1L)
		).andDo(
			document("diaries/create/success",
				getDocumentRequest(), getDocumentResponse(),
				requestFields(
					fieldWithPath("date").type(JsonFieldType.STRING).description("일기 날짜")
						.attributes(getConstraints("constraints", "yyyy-MM-dd의 형식이어야 합니다.")),
					fieldWithPath("dogName").type(JsonFieldType.STRING).description("강아지 이름")
						.attributes(getConstraints("constraints", "1자 이상 10자 이하여야 합니다.")),
					fieldWithPath("title").type(JsonFieldType.STRING).description("일기 제목")
						.attributes(getConstraints("constraints", "1자 이상 20자 이하여야 합니다.")),
					fieldWithPath("content").type(JsonFieldType.STRING).description("일기 내용")
						.attributes(getConstraints("constraints", "1자 이상 1000자 이하여야 합니다.")),
					fieldWithPath("imageIds").type(JsonFieldType.ARRAY).description("일기 이미지 id 리스트")
						.attributes(getConstraints("constraints", "id는 1개 이상 5개 이하여야 하며 리스트 형태로 입력해야합니다. [1,2,3...], "
							+ "사전에 이미지를 저장해서 id를 받아와야합니다."))
				)
			)
		);
	}

	@DisplayName("수정: 성공 -> 200")
	@Test
	void updateDiary() throws Exception {
		// given
		DiaryUpdateRequest diaryUpdateRequest = DiaryUpdateRequest.builder()
			.date(LocalDate.now())
			.dogName("사랑이")
			.title("일기 제목")
			.content("일기 내용")
			.imageIds(imageIds)
			.build();
		doNothing().when(diaryService).update(any(), any(), any());

		// when
		ResultActions resultActions = mockMvc.perform(
			put("/api/v1/diaries/{diaryId}", 1L)
				.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
				.content(JsonUtils.toJson(diaryUpdateRequest))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").doesNotExist()
		).andDo(
			document("diaries/update/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("diaryId").description("일기 id")
				),
				requestFields(
					fieldWithPath("date").type(JsonFieldType.STRING).description("일기 날짜")
						.attributes(getConstraints("constraints", "yyyy-MM-dd의 형식이어야 합니다.")),
					fieldWithPath("dogName").type(JsonFieldType.STRING).description("강아지 이름")
						.attributes(getConstraints("constraints", "1자 이상 10자 이하여야 합니다.")),
					fieldWithPath("title").type(JsonFieldType.STRING).description("일기 제목")
						.attributes(getConstraints("constraints", "1자 이상 20자 이하여야 합니다.")),
					fieldWithPath("content").type(JsonFieldType.STRING).description("일기 내용")
						.attributes(getConstraints("constraints", "1자 이상 1000자 이하여야 합니다.")),
					fieldWithPath("imageIds").type(JsonFieldType.ARRAY).description("일기 이미지 id 리스트")
						.attributes(getConstraints("constraints", "id는 1개 이상 5개 이하여야 하며 리스트 형태로 입력해야합니다. [1,2,3...], "
							+ "사전에 이미지를 수정해서 id를 받아와야합니다."))
				)
			)
		);
	}

	@DisplayName("삭제: 성공 -> 200")
	@Test
	void deleteDiary() throws Exception {
		// given
		doNothing().when(diaryService).delete(any(), any());

		// when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.delete("/api/v1/diaries/{diaryId}", 1L)
				.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER));

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").doesNotExist()
		).andDo(
			document("diaries/delete/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("diaryId").description("일기 id")
				)
			)
		);
	}

	@DisplayName("공유 UUID 조회: 성공 -> 200")
	@Test
	void findDiaryUUID() throws Exception {
		// given
		String uuid = UUID.randomUUID().toString();
		DiaryUUIDResponse response = DiaryUUIDResponse.builder()
			.uuid(uuid).build();

		given(diaryService.findDiaryUUID(any(), any())).willReturn(response);

		// when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.get("/api/v1/diaries/{diaryId}/share-uuid", 1L)
				.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.uuid").value(uuid)
		).andDo(
			document("diaries/getUUID/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("diaryId").description("일기 id")
				)
			)
		);
	}

	@DisplayName("공유된 일기 조회: 성공 -> 200")
	@Test
	void findSharedDiary() throws Exception {
		// given
		given(diaryService.findSharedDiary(any())).willReturn(diaryResponse1);

		// when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.get("/api/v1/diaries/shared/{uuid}", 1L)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.id").value(1L),
			jsonPath("$.data.date").value(LocalDate.now().minusDays(1).toString()),
			jsonPath("$.data..dogName").value("코코"),
			jsonPath("$.data.title").value("일기 1 제목"),
			jsonPath("$.data.content").value("일기 1 내용"),
			jsonPath("$.data.createdAt").isNotEmpty(),
			jsonPath("$.data.imageIds").isArray()
		).andDo(
			document("diaries/getShared/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("uuid").description("공유된 일기의 uuid")
				)
			)
		);
	}
}