package com.meommu.meommuapi.gpt.presentation;

import static com.meommu.meommuapi.util.documentation.DocumentConstant.*;
import static com.meommu.meommuapi.util.documentation.DocumentFormatGenerator.*;
import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.meommu.meommuapi.gpt.application.dto.GptGenerateRequest;
import com.meommu.meommuapi.gpt.application.dto.GptGenerateResponse;
import com.meommu.meommuapi.common.util.JsonUtils;
import com.meommu.meommuapi.util.ControllerTest;

import reactor.core.publisher.Flux;

@DisplayName("GPT API")
class GptControllerTest extends ControllerTest {

	@DisplayName("GPT 생성: 성공 -> 201")
	@Test
	void testCreateGptContent() throws Exception {
		// given
		GptGenerateRequest gptGenerateRequest = GptGenerateRequest.builder()
			.details("수영장에서 친구랑 놀기|맛있는 간식 먹기")
			.build();
		GptGenerateResponse gptGenerateResponse = GptGenerateResponse.builder()
			.content("생성된 일기 내용")
			.build();
		given(gptService.createGptContent(any())).willReturn(gptGenerateResponse);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/gpt")
			.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
			.content(JsonUtils.toJson(gptGenerateRequest))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		// then
		resultActions.andExpectAll(
			status().isCreated(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.content").value("생성된 일기 내용")
		).andDo(
			document("gpt/create/success",
				getDocumentRequest(), getDocumentResponse(),
				requestFields(
					fieldWithPath("details").type(JsonFieldType.STRING).description("일기 생성을 위한 디테일")
						.attributes(
							getConstraints("constraints", "일기 디테일은 최소 1개이상 존재해야하며, 디테일들은 파이프(|)로 구분되어야 합니다."))
				)
			)
		);
	}

	@DisplayName("GPT Stream 생성: 성공 -> 201")
	@Test
	void testCreateGptContentStream() throws Exception {
		// given
		GptGenerateRequest gptGenerateRequest = GptGenerateRequest.builder()
			.details("수영장에서 친구랑 놀기|맛있는 간식 먹기")
			.build();
		var response1 = """
			    {
			        "id": "chatcmpl-8PkyK7PqVwezCANDoBBe8wa07h8MM",
			        "object": "chat.completion.chunk",
			        "created": 1701149452,
			        "model": "gpt-3.5-turbo-0613",
			        "choices": [
			            {
			                "index": 0,
			                "delta": {
			                    "role": "assistant",
			                    "content": ""
			                },
			                "finish_reason": null
			            }
			        ]
			    }
			""";
		var response2 = """
			    {
			        "id": "chatcmpl-8PkyK7PqVwezCANDoBBe8wa07h8MM",
			        "object": "chat.completion.chunk",
			        "created": 1701149452,
			        "model": "gpt-3.5-turbo-0613",
			        "choices": [
			            {
			                "index": 0,
			                "delta": {
			                    "content": "일"
			                },
			                "finish_reason": null
			            }
			        ]
			    }
			""";
		var response3 = """
			    {
			        "id": "chatcmpl-8PkyK7PqVwezCANDoBBe8wa07h8MM",
			        "object": "chat.completion.chunk",
			        "created": 1701149452,
			        "model": "gpt-3.5-turbo-0613",
			        "choices": [
			            {
			                "index": 0,
			                "delta": {
			                    "content": "기"
			                },
			                "finish_reason": null
			            }
			        ]
			    }
			""";
		var response4 = """
			    {
			        "id": "chatcmpl-8PkyK7PqVwezCANDoBBe8wa07h8MM",
			        "object": "chat.completion.chunk",
			        "created": 1701149452,
			        "model": "gpt-3.5-turbo-0613",
			        "choices": [
			            {
			                "index": 0,
			                "delta": {},
			                "finish_reason": "stop"
			            }
			        ]
			    }
			""";
		var response5 = """
			    {
			        "[DONE]"
			    }
			""";

		var responseSource = Flux.just(response1, response2, response3, response4, response5);

		given(gptService.createGptContentStream(any())).willReturn(responseSource);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/gpt/stream")
			.header(AUTHORIZATION, ACCESS_TOKEN_WITH_BEARER)
			.content(JsonUtils.toJson(gptGenerateRequest))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		// then
		resultActions.andExpectAll(
			status().isCreated()
		).andDo(
			document("gpt/create-stream/success",
				getDocumentRequest(), getDocumentResponse(),
				requestFields(
					fieldWithPath("details").type(JsonFieldType.STRING).description("일기 생성을 위한 디테일")
						.attributes(
							getConstraints("constraints", "일기 디테일은 최소 1개이상 존재해야하며, 디테일들은 파이프(|)로 구분되어야 합니다."))
				)
			)
		);
	}
}