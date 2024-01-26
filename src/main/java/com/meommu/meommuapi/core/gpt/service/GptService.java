package com.meommu.meommuapi.core.gpt.service;

import com.meommu.meommuapi.core.gpt.dto.GptGenerateRequest;
import com.meommu.meommuapi.core.gpt.dto.GptGenerateResponse;

import reactor.core.publisher.Flux;

public interface GptService {
	GptGenerateResponse createGptContent(GptGenerateRequest gptGenerateRequest);

	Flux<String> createGptContentStream(GptGenerateRequest gptGenerateRequest);

}
