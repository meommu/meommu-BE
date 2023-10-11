package com.meommu.meommuapi.gpt.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.gpt.dto.GptGenerateRequest;
import com.meommu.meommuapi.gpt.dto.GptGenerateResponse;
import com.meommu.meommuapi.gpt.dto.GptRequest;
import com.meommu.meommuapi.gpt.dto.GptResponse;
import com.meommu.meommuapi.gpt.util.GptClient;

@Transactional(readOnly = true)
@Service
public class GptService {

	private final GptClient gptClient;

	public GptService(GptClient gptClient) {
		this.gptClient = gptClient;
	}

	public GptGenerateResponse createGptContent(GptGenerateRequest gptGenerateRequest) {
		try {
			GptResponse gptResponse = gptClient.chat(new GptRequest(gptGenerateRequest.getDetails()));
			return GptGenerateResponse.from(extractContent(gptResponse));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	private static String extractContent(GptResponse gptResponse) {
		return gptResponse.choices().stream()
			.map(c -> c.message().content())
			.collect(Collectors.joining("\n"));
	}
}
