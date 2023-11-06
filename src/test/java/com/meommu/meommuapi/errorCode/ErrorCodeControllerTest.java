package com.meommu.meommuapi.errorCode;

import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meommu.meommuapi.util.ControllerTest;
import com.meommu.meommuapi.util.documentation.CustomResponseFieldsSnippet;

@DisplayName("에러코드 문서화")
class ErrorCodeControllerTest extends ControllerTest {

	ObjectMapper objectMapper = new ObjectMapper();

	@DisplayName("에러코드 전체 조회: 성공 -> 200")
	@Test
	public void testFindErrorCodes() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/error-code")
		);

		List<ErrorCodeResponse> errorCodeResponses = getData(resultActions.andReturn());

		// then
		resultActions.andExpectAll(
			status().isOk()
		).andDo(
			document("error-code/getAll/success",
				getDocumentRequest(), getDocumentResponse(),
				customResponseFields("errorCode-response",
					fieldDescriptors(errorCodeResponses)
				)
			)
		);
	}

	private static CustomResponseFieldsSnippet customResponseFields(
		String snippetFilePrefix,
		List<FieldDescriptor> fieldDescriptors) {
		return new CustomResponseFieldsSnippet(snippetFilePrefix, fieldDescriptors, true);
	}

	private List<FieldDescriptor> fieldDescriptors(List<ErrorCodeResponse> errorResponses) {
		List<FieldDescriptor> fieldDescriptors = new ArrayList<>();

		for (ErrorCodeResponse errorCodeResponse : errorResponses) {

			FieldDescriptor attributes = fieldWithPath(errorCodeResponse.getName()).type(JsonFieldType.OBJECT)
				.attributes(
					key("code").value(errorCodeResponse.getCode()),
					key("name").value(errorCodeResponse.getName()),
					key("message").value(errorCodeResponse.getMessage()));
			fieldDescriptors.add(attributes);
		}

		return fieldDescriptors;
	}

	private List<ErrorCodeResponse> getData(MvcResult result) throws IOException {
		String content = result.getResponse().getContentAsString();
		Map<String, ErrorCodeResponse> errorCodeMap = objectMapper.readValue(content, new TypeReference<>() {
		});
		return new ArrayList<>(errorCodeMap.values());
	}
}
