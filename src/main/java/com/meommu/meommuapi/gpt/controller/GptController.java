package com.meommu.meommuapi.gpt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.gpt.dto.GptGenerateRequest;
import com.meommu.meommuapi.gpt.dto.GptGenerateResponse;
import com.meommu.meommuapi.gpt.service.GptService;

@RestController
public class GptController {

	private final GptService gptService;

	public GptController(GptService gptService) {
		this.gptService = gptService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/gpt")
	public GptGenerateResponse createGptContent(@RequestBody GptGenerateRequest gptGenerateRequest) {
		return gptService.createGptContent(gptGenerateRequest);
	}
}
