package com.meommu.meommuapi.gpt.service;

import com.meommu.meommuapi.gpt.dto.GptGenerateRequest;
import com.meommu.meommuapi.gpt.dto.GptGenerateResponse;

import reactor.core.publisher.Flux;

public interface GptService {
	GptGenerateResponse createGptContent(GptGenerateRequest gptGenerateRequest);

	Flux<String> createGptContentStream(GptGenerateRequest gptGenerateRequest);

}
