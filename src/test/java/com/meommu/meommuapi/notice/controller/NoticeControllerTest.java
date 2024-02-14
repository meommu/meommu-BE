package com.meommu.meommuapi.notice.controller;

import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.meommu.meommuapi.notice.dto.NoticeResponse;
import com.meommu.meommuapi.notice.dto.NoticeResponses;
import com.meommu.meommuapi.util.ControllerTest;

@DisplayName("공지 API")
class NoticeControllerTest extends ControllerTest {

	NoticeResponse noticeResponse1;

	NoticeResponse noticeResponse2;

	NoticeResponses noticeResponses;

	@BeforeEach
	void setUp() {
		noticeResponse1 = NoticeResponse.builder()
			.id(1L)
			.title("공지 1 제목")
			.content("공지 1 내용")
			.createdAt(LocalDateTime.now().minusDays(1))
			.build();
		noticeResponse2 = NoticeResponse.builder()
			.id(2L)
			.title("공지 2 제목")
			.content("공지 2 내용")
			.createdAt(LocalDateTime.now())
			.build();
		noticeResponses = NoticeResponses.builder()
			.noticeResponses(List.of(noticeResponse2, noticeResponse1))
			.build();
	}

	@DisplayName("전체 조회: 성공 -> 200")
	@Test
	void testFindNotices() throws Exception {
		// given
		given(noticeService.findNotices()).willReturn(noticeResponses);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/notices")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.notices[0].id").value(2L),
			jsonPath("$.data.notices[0].title").value("공지 2 제목"),
			jsonPath("$.data.notices[0].content").value("공지 2 내용"),
			jsonPath("$.data.notices[0].createdAt").isNotEmpty(),
			jsonPath("$.data.notices[1].id").value(1L),
			jsonPath("$.data.notices[1].title").value("공지 1 제목"),
			jsonPath("$.data.notices[1].content").value("공지 1 내용"),
			jsonPath("$.data.notices[1].createdAt").isNotEmpty()
		).andDo(
			document("notices/getAll/success",
				getDocumentRequest(), getDocumentResponse()
			)
		);
	}
}