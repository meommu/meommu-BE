package com.meommu.meommuapi.gpt.util;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import com.meommu.meommuapi.gpt.dto.GptRequest;
import com.meommu.meommuapi.gpt.dto.GptResponse;

public interface GptClient {
	@PostExchange("/v1/chat/completions")
	GptResponse chat(@RequestBody GptRequest gptRequest);
}