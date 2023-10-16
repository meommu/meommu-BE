package com.meommu.meommuapi.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.meommu.meommuapi.auth.controller.AuthController;
import com.meommu.meommuapi.auth.service.AuthService;
import com.meommu.meommuapi.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.common.interceptor.AuthInterceptor;
import com.meommu.meommuapi.diary.controller.DiaryController;
import com.meommu.meommuapi.diary.service.DiaryService;
import com.meommu.meommuapi.gpt.controller.GptController;
import com.meommu.meommuapi.gpt.service.GptService;
import com.meommu.meommuapi.guide.controller.GuideController;
import com.meommu.meommuapi.guide.service.GuideService;
import com.meommu.meommuapi.image.controller.ImageController;
import com.meommu.meommuapi.image.service.ImageService;
import com.meommu.meommuapi.kindergarten.controller.KindergartenController;
import com.meommu.meommuapi.kindergarten.service.KindergartenService;
import com.meommu.meommuapi.util.documentation.DocumentUtils;

@WebMvcTest({
	KindergartenController.class,
	AuthController.class,
	ImageController.class,
	GptController.class,
	GuideController.class,
	DiaryController.class
})
@Import(DocumentUtils.class)
@ExtendWith(RestDocumentationExtension.class)
public abstract class ControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected AuthInterceptor authInterceptor;

	@MockBean
	protected JwtTokenProvider jwtTokenProvider;

	@MockBean
	protected AuthService authService;

	@MockBean
	protected KindergartenService kindergartenService;

	@MockBean
	protected ImageService imageService;

	@MockBean
	protected GptService gptService;

	@MockBean
	protected GuideService guideService;

	@MockBean
	protected DiaryService diaryService;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentation).snippets().withEncoding("UTF-8"))
			.build();

		given(authInterceptor.preHandle(any(), any(), any()))
			.willReturn(true);
	}
}
