package com.meommu.meommuapi.diary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.token.Auth;
import com.meommu.meommuapi.diary.dto.DiaryResponse;
import com.meommu.meommuapi.diary.dto.DiaryResponses;
import com.meommu.meommuapi.diary.dto.DiarySaveRequest;
import com.meommu.meommuapi.diary.dto.DiarySaveResponse;
import com.meommu.meommuapi.diary.dto.DiarySearchCriteria;
import com.meommu.meommuapi.diary.dto.DiaryUpdateRequest;
import com.meommu.meommuapi.diary.service.DiaryService;

@RestController
public class DiaryController {

	private final DiaryService diaryService;

	public DiaryController(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

	@GetMapping("/api/v1/diaries")
	public DiaryResponses findDiaries(
		@ModelAttribute DiarySearchCriteria criteria,
		@Auth AuthInfo authInfo) {
		return diaryService.findDiaries(criteria, authInfo);
	}

	@GetMapping("/api/v1/diaries/{diaryId}")
	public DiaryResponse findDiary(
		@PathVariable Long diaryId,
		@Auth AuthInfo authInfo) {
		return diaryService.findDiary(diaryId, authInfo);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/api/v1/diaries")
	public DiarySaveResponse createDairy(
		@RequestBody DiarySaveRequest diarySaveRequest,
		@Auth AuthInfo authInfo) {
		return diaryService.create(diarySaveRequest, authInfo);
	}

	@PutMapping(value = "/api/v1/diaries/{diaryId}")
	public void updateDiary(
		@PathVariable Long diaryId,
		@RequestBody DiaryUpdateRequest diaryUpdateRequest,
		@Auth AuthInfo authInfo) {
		diaryService.update(diaryId, diaryUpdateRequest, authInfo);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/api/v1/diaries/{diaryId}")
	public void deleteDiary(
		@PathVariable Long diaryId,
		@Auth AuthInfo authInfo) {
		diaryService.delete(diaryId, authInfo);
	}
}
