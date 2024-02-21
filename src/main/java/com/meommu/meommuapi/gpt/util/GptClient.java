package com.meommu.meommuapi.gpt.util;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import com.meommu.meommuapi.gpt.application.dto.GptRequest;
import com.meommu.meommuapi.gpt.application.dto.GptRequestStream;
import com.meommu.meommuapi.gpt.application.dto.GptResponse;

import reactor.core.publisher.Flux;

public interface GptClient {
	@PostExchange("/v1/chat/completions")
	GptResponse chat(@RequestBody GptRequest gptRequest);

	@PostExchange("/v1/chat/completions")
	Flux<String> chatStream(@RequestBody GptRequestStream gptRequest);
}