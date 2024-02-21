package com.meommu.meommuapi.diary.presentation;

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
import com.meommu.meommuapi.diary.dto.DiarySummaryResponses;
import com.meommu.meommuapi.diary.dto.DiaryUUIDResponse;
import com.meommu.meommuapi.diary.dto.DiaryUpdateRequest;
import com.meommu.meommuapi.diary.application.DiaryService;

import jakarta.validation.Valid;

@RestController
public class DiaryController {

	private final DiaryService diaryService;

	public DiaryController(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

	@GetMapping("/api/v1/diaries/summary")
	public DiarySummaryResponses findDiariesSummary(
		@Auth AuthInfo authInfo) {
		return diaryService.findDiariesSummary(authInfo);
	}

	@GetMapping("/api/v1/diaries")
	public DiaryResponses findDiaries(
		@Valid @ModelAttribute DiarySearchCriteria criteria,
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
		@Valid @RequestBody DiarySaveRequest diarySaveRequest,
		@Auth AuthInfo authInfo) {
		return diaryService.create(diarySaveRequest, authInfo);
	}

	@PutMapping(value = "/api/v1/diaries/{diaryId}")
	public void updateDiary(
		@PathVariable Long diaryId,
		@Valid @RequestBody DiaryUpdateRequest diaryUpdateRequest,
		@Auth AuthInfo authInfo) {
		diaryService.update(diaryId, diaryUpdateRequest, authInfo);
	}

	@DeleteMapping("/api/v1/diaries/{diaryId}")
	public void deleteDiary(
		@PathVariable Long diaryId,
		@Auth AuthInfo authInfo) {
		diaryService.delete(diaryId, authInfo);
	}

	@GetMapping("/api/v1/diaries/{dairyId}/share-uuid")
	public DiaryUUIDResponse findDiaryUUID(
		@PathVariable Long dairyId,
		@Auth AuthInfo authInfo) {
		return diaryService.findDiaryUUID(dairyId, authInfo);
	}

	@GetMapping("/api/v1/diaries/shared/{uuid}")
	public DiaryResponse findSharedDiary(@PathVariable String uuid) {
		return diaryService.findSharedDiary(uuid);
	}
}
