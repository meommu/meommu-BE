package com.meommu.meommuapi.image.controller;

import static com.meommu.meommuapi.util.documentation.DocumentFormatGenerator.*;
import static com.meommu.meommuapi.util.documentation.DocumentUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.meommu.meommuapi.image.domain.Image;
import com.meommu.meommuapi.image.dto.ImageResponse;
import com.meommu.meommuapi.image.dto.ImageResponses;
import com.meommu.meommuapi.util.ControllerTest;

@DisplayName("이미지 API")
class ImageControllerTest extends ControllerTest {

	ImageResponse imageResponse1;

	ImageResponse imageResponse2;

	ImageResponses imageResponses;

	@BeforeEach
	void setUp() throws Exception {
		Image image1 = Image.of("https://test1.com");
		Image image2 = Image.of("https://test2.com");

		Field idField = Image.class.getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(image1, 1L);
		idField.set(image2, 2L);

		imageResponse1 = ImageResponse.from(image1);
		imageResponse2 = ImageResponse.from(image2);
		imageResponses = ImageResponses.from(List.of(image1, image2));
	}

	@DisplayName("다건 조회: 성공 -> 200")
	@Test
	void ImageList() throws Exception {
		// given
		given(imageService.findAllById(any())).willReturn(imageResponses);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/images?id=1&id=2")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.images[0].id").value(1L),
			jsonPath("$.data.images[0].url").value("https://test1.com"),
			jsonPath("$.data.images[1].id").value(2L),
			jsonPath("$.data.images[1].url").value("https://test2.com")
		).andDo(
			document("images/getAll/success",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("id").description("이미지 id")
						.attributes(getConstraints("constraints", "\"?id=1&id=2\"와 같이 이미지 id 여러 건을 조회할 수 있습니다."))
				)
			)
		);
	}

	@DisplayName("단건 조회: 성공 -> 200")
	@Test
	void ImageDetails() throws Exception {
		// given
		given(imageService.findById(any())).willReturn(imageResponse1);

		// when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.get("/api/v1/images/{imageId}", 1L)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.id").value(1L),
			jsonPath("$.data.url").value("https://test1.com")
		).andDo(
			document("images/get/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("imageId").description("이미지 id")
				)
			)
		);
	}

	@DisplayName("생성: 성공 -> 201")
	@Test
	void ImageAdd() throws Exception {
		// given
		String fileName = "coco";
		String contentType = "jpeg"; // 이미지 파일 형식에 맞게 설정
		String filePath = "src/test/resources/testimages/" + fileName + "." + contentType;

		given(imageService.create(any())).willReturn(imageResponses);

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.multipart("/api/v1/images")
				.part(new MockPart("category", "<IMAGE_CATEGORY>".getBytes()))
				.file("images", filePath.getBytes())
		);

		// then
		resultActions.andExpectAll(
			status().isCreated(),
			jsonPath("$.code").value("0000"),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data.images[0].id").value(1L),
			jsonPath("$.data.images[0].url").value("https://test1.com"),
			jsonPath("$.data.images[1].id").value(2L),
			jsonPath("$.data.images[1].url").value("https://test2.com")
		).andDo(
			document("images/create/success",
				getDocumentRequest(), getDocumentResponse(),
				requestParts(
					partWithName("category").description("이미지 카테고리")
						.attributes(getConstraints("constraints",
							"이미지 카테고리는 \"PARENT_PROFILE\", \"KINDERGARTEN_PROFILE\", \"DOG_PROFILE\", \"DIARY_IMAGE\", \"NOTICE_IMAGE\" 중에 하나여야 하며 대소문자를 구분하지 않습니다.")),
					partWithName("images").description("일기 이미지 id 리스트")
						.attributes(getConstraints("constraints",
							"이미지 타입은 \"image/jpeg\", \"image/png\", \"image/heic\", \"image/jpg\" 중에 하나여야 합니다. 한 요청시 보낼 수 있는 이미지 한 장의 최대 용량은 30MB입니다. 한 요청의 이미지 총 용량은 150MB를 넘을 수 없습니다."))
				)
			)
		);
	}

	@DisplayName("단건 삭제: 성공 -> 204")
	@Test
	void ImageDelete() throws Exception {
		// given
		doNothing().when(imageService).deleteById(any());

		// when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.delete("/api/v1/images/{imageId}", 1L)
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").doesNotExist()
		).andDo(
			document("images/delete/success",
				getDocumentRequest(), getDocumentResponse(),
				pathParameters(
					parameterWithName("imageId").description("이미지 id")
				)
			)
		);
	}

	@DisplayName("다건 삭제: 성공 -> 204")
	@Test
	void ImagesDelete() throws Exception {
		// given
		doNothing().when(imageService).deleteAllById(any());

		// when
		ResultActions resultActions = mockMvc.perform(delete("/api/v1/images?id=1&id=2")
		);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("정상"),
			jsonPath("$.data").doesNotExist()
		).andDo(
			document("images/deleteAll/success",
				getDocumentRequest(), getDocumentResponse(),
				queryParameters(
					parameterWithName("id").description("이미지 id")
						.attributes(getConstraints("constraints", "\"?id=1&id=2\"와 같이 이미지 id 여러 건을 삭제할 수 있습니다.")
						)
				)
			)
		);
	}
}