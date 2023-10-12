package com.meommu.meommuapi.guide.controller;

import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.meommu.meommuapi.common.util.JsonUtils;
import com.meommu.meommuapi.guide.dto.GuideDetailResponse;
import com.meommu.meommuapi.guide.dto.GuideDetailResponses;
import com.meommu.meommuapi.guide.dto.GuideDetailSaveRequest;
import com.meommu.meommuapi.guide.dto.GuideDetailSaveResponse;
import com.meommu.meommuapi.guide.dto.GuideResponse;
import com.meommu.meommuapi.guide.dto.GuideResponses;
import com.meommu.meommuapi.util.ControllerTest;

@DisplayName("가이드 API")
class GuideControllerTest extends ControllerTest {

	@DisplayName("[일기] 가이드 목록 조회 요청을 하면 200을 반환한다.")
	@Test
	void testFindGuides() throws Exception {
		// given
		GuideResponse guideResponse1 = GuideResponse.builder()
			.id(1L)
			.guide("산책에 관한 일상")
			.build();
		GuideResponse guideResponse2 = GuideResponse.builder()
			.id(2L)
			.guide("낮잠에 관한 일상")
			.build();
		GuideResponses guideResponses = GuideResponses.builder()
			.guides(List.of(guideResponse1, guideResponse2))
			.build();
		given(guideService.findGuides()).willReturn(guideResponses);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/guides")
			.header(AUTHORIZATION, "<ACCESS_TOKEN>")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.guides[0].id").value(1L),
			jsonPath("$.data.guides[0].guide").value("산책에 관한 일상"),
			jsonPath("$.data.guides[1].id").value(2L),
			jsonPath("$.data.guides[1].guide").value("낮잠에 관한 일상")
		).andDo(
			MockMvcResultHandlers.print()
		).andDo(
			document("guide/getAll/success",
				getDocumentRequest(), getDocumentResponse()
			)
		);
	}

	@DisplayName("[일기] 가이드 디테일 목록 조회 요청을 하면 200을 반환한다.")
	@Test
	void guideDetailList() throws Exception {
		// given
		GuideDetailResponse guideDetailResponse1 = GuideDetailResponse.builder()
			.id(1L)
			.detail("산책을 오래 했어요.")
			.build();
		GuideDetailResponse guideDetailResponse2 = GuideDetailResponse.builder()
			.id(2L)
			.detail("산책을 조금 했어요.")
			.build();
		GuideDetailResponses guideDetailResponses = GuideDetailResponses.builder()
			.details(List.of(guideDetailResponse1, guideDetailResponse2))
			.build();
		given(guideService.findGuideDetails(any(), any())).willReturn(guideDetailResponses);

		// when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.get("/api/v1/guides/{guideId}/details", 1L)
				.header(AUTHORIZATION, "<ACCESS_TOKEN>")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.details[0].id").value(1L),
			jsonPath("$.data.details[0].detail").value("산책을 오래 했어요."),
			jsonPath("$.data.details[1].id").value(2L),
			jsonPath("$.data.details[1].detail").value("산책을 조금 했어요.")
		).andDo(
			MockMvcResultHandlers.print()
		).andDo(
			document("guide-detail/getAll/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("guideId").description("가이드 id")

				)
			)
		);
	}

	@DisplayName("[일기] 가이드 디테일 생성 요청을 하면 201을 반환한다.")
	@Test
	void guideDetailAdd() throws Exception {
		// given
		GuideDetailSaveResponse guideDetailSaveResponse = GuideDetailSaveResponse.builder().savedId(1L).build();
		GuideDetailSaveRequest guideDetailSaveRequest = GuideDetailSaveRequest.builder().detail("산책하다 꽃을 봤어요.").build();
		given(guideService.createGuideDetail(any(), any(), any())).willReturn(guideDetailSaveResponse);

		// when
		ResultActions resultActions = mockMvc.perform(
			post("/api/v1/guides/{guideId}/details", 1L)
				.header(AUTHORIZATION, "<ACCESS_TOKEN>")
				.content(JsonUtils.toJson(guideDetailSaveRequest))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpectAll(
			status().isCreated(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.savedId").value(1L)
		).andDo(
			MockMvcResultHandlers.print()
		).andDo(
			document("guide-detail/create/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("guideId").description("가이드 id")
				),
				requestFields(
					fieldWithPath("detail").description("디테일")
				)
			)
		);
	}

	@DisplayName("[일기] 가이드 디테일 삭제 요청을 하면 200을 반환한다.")
	@Test
	void guideDetailRemove() throws Exception {
		// given
		doNothing().when(guideService).deleteGuideDetail(any(), any(), any());

		// when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.delete("/api/v1/guides/{guideId}/details/{detailId}", 1L, 1L)
				.header(AUTHORIZATION, "<ACCESS_TOKEN>"));

		// then
		resultActions.andExpectAll(
			status().isNoContent()
		).andDo(
			document("guide-detail/delete/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("guideId").description("가이드 id"),
					parameterWithName("detailId").description("디테일 id")
				)
			)
		);
	}
}