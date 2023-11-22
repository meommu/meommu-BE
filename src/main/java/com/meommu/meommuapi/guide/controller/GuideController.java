package com.meommu.meommuapi.guide.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.token.Auth;
import com.meommu.meommuapi.guide.dto.GuideDetailResponses;
import com.meommu.meommuapi.guide.dto.GuideDetailSaveRequest;
import com.meommu.meommuapi.guide.dto.GuideDetailSaveResponse;
import com.meommu.meommuapi.guide.dto.GuideResponses;
import com.meommu.meommuapi.guide.service.GuideService;

@RestController
public class GuideController {

	private final GuideService guideService;

	public GuideController(GuideService guideService) {
		this.guideService = guideService;
	}

	@GetMapping("/api/v1/guides")
	public GuideResponses findGuides() {
		return guideService.findGuides();
	}

	@GetMapping("/api/v1/guides/{guideId}/details")
	public GuideDetailResponses findGuideDetails(@PathVariable Long guideId) {
		return guideService.findGuideDetails(guideId);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/guides/{guideId}/details")
	public GuideDetailSaveResponse createGuideDetails(
		@PathVariable Long guideId,
		@RequestBody GuideDetailSaveRequest request) {
		return guideService.createGuideDetail(guideId, request);
	}

	@DeleteMapping("/api/v1/guides/{guideId}/details/{detailId}")
	public void deleteGuideDetail(
		@PathVariable Long guideId,
		@PathVariable Long detailId) {
		guideService.deleteGuideDetail(guideId, detailId);
	}
}
