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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.meommu.meommuapi.core.auth.controller.AuthController;
import com.meommu.meommuapi.core.auth.service.AuthService;
import com.meommu.meommuapi.core.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.global.interceptor.AuthInterceptor;
import com.meommu.meommuapi.core.diary.controller.DiaryController;
import com.meommu.meommuapi.core.diary.service.DiaryService;
import com.meommu.meommuapi.errorCode.ErrorCodeController;
import com.meommu.meommuapi.core.gpt.controller.GptController;
import com.meommu.meommuapi.core.gpt.service.GptService;
import com.meommu.meommuapi.core.guide.controller.GuideController;
import com.meommu.meommuapi.core.guide.service.GuideService;
import com.meommu.meommuapi.core.image.controller.ImageController;
import com.meommu.meommuapi.core.image.service.ImageService;
import com.meommu.meommuapi.core.kindergarten.controller.KindergartenController;
import com.meommu.meommuapi.core.kindergarten.service.KindergartenService;
import com.meommu.meommuapi.core.notice.controller.NoticeController;
import com.meommu.meommuapi.core.notice.service.NoticeService;
import com.meommu.meommuapi.core.proxy.controller.ProxyController;
import com.meommu.meommuapi.core.proxy.service.ProxyService;
import com.meommu.meommuapi.util.documentation.DocumentUtils;

@WebMvcTest({
	KindergartenController.class,
	AuthController.class,
	ImageController.class,
	GptController.class,
	GuideController.class,
	DiaryController.class,
	NoticeController.class,
	ErrorCodeController.class,
	ProxyController.class,
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

	@MockBean
	protected NoticeService noticeService;

	@MockBean
	protected ProxyService proxyService;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.apply(documentationConfiguration(restDocumentation).snippets().withEncoding("UTF-8"))
			.alwaysDo(MockMvcResultHandlers.print())
			.build();

		given(authInterceptor.preHandle(any(), any(), any()))
			.willReturn(true);
	}
}
