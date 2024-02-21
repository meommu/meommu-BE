package com.meommu.meommuapi.gpt.application;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.meommu.meommuapi.gpt.application.dto.GptGenerateRequest;
import com.meommu.meommuapi.gpt.application.dto.GptGenerateResponse;
import com.meommu.meommuapi.gpt.application.dto.GptRequest;
import com.meommu.meommuapi.gpt.application.dto.GptRequestStream;
import com.meommu.meommuapi.gpt.application.dto.GptResponse;
import com.meommu.meommuapi.gpt.exception.GptGenerateException;
import com.meommu.meommuapi.gpt.util.GptClient;

import reactor.core.publisher.Flux;

@Service
public class GptServiceImpl implements GptService{

	private final GptClient gptClient;

	public GptServiceImpl(GptClient gptClient) {
		this.gptClient = gptClient;
	}

	@Override
	public GptGenerateResponse createGptContent(GptGenerateRequest gptGenerateRequest) {
		try {
			GptResponse response = gptClient.chat(new GptRequest(gptGenerateRequest.getDetails()));
			return GptGenerateResponse.from(extractContent(response));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new GptGenerateException();
		}
	}

	@Override
	public Flux<String> createGptContentStream(GptGenerateRequest gptGenerateRequest) {
		try {
			return gptClient.chatStream(new GptRequestStream(gptGenerateRequest.getDetails()));
		} catch (Exception e) {
			throw new GptGenerateException();
		}
	}

	private static String extractContent(GptResponse gptResponse) {
		return gptResponse.choices().stream()
			.map(c -> c.message().content())
			.collect(Collectors.joining("\n"));
	}
}
