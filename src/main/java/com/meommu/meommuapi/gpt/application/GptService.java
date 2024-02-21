package com.meommu.meommuapi.gpt.application;

import com.meommu.meommuapi.gpt.application.dto.GptGenerateRequest;
import com.meommu.meommuapi.gpt.application.dto.GptGenerateResponse;

import reactor.core.publisher.Flux;

public interface GptService {
	GptGenerateResponse createGptContent(GptGenerateRequest gptGenerateRequest);

	Flux<String> createGptContentStream(GptGenerateRequest gptGenerateRequest);

}
