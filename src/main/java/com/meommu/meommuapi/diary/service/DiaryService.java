package com.meommu.meommuapi.diary.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.exception.AuthorizationException;
import com.meommu.meommuapi.diary.domain.Diary;
import com.meommu.meommuapi.diary.domain.DiaryImage;
import com.meommu.meommuapi.diary.dto.DiaryResponse;
import com.meommu.meommuapi.diary.dto.DiaryResponses;
import com.meommu.meommuapi.diary.dto.DiarySaveRequest;
import com.meommu.meommuapi.diary.dto.DiarySaveResponse;
import com.meommu.meommuapi.diary.dto.DiarySearchCriteria;
import com.meommu.meommuapi.diary.dto.DiaryUpdateRequest;
import com.meommu.meommuapi.diary.exception.DiaryNotFoundException;
import com.meommu.meommuapi.diary.repository.DiaryImageRepository;
import com.meommu.meommuapi.diary.repository.DiaryRepository;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.exception.KindergartenNotFoundException;
import com.meommu.meommuapi.kindergarten.repository.KindergartenRepository;

@Transactional(readOnly = true)
@Service
public class DiaryService {

	private final DiaryRepository diaryRepository;

	private final DiaryImageRepository diaryImageRepository;

	private final KindergartenRepository kindergartenRepository;

	public DiaryService(DiaryRepository diaryRepository, DiaryImageRepository diaryImageRepository,
		KindergartenRepository kindergartenRepository) {
		this.diaryRepository = diaryRepository;
		this.diaryImageRepository = diaryImageRepository;
		this.kindergartenRepository = kindergartenRepository;
	}

	public DiaryResponses findDiaries(DiarySearchCriteria criteria, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		LocalDate startDate = LocalDate.of(criteria.getYear(), criteria.getMonth(), 1);
		LocalDate endDate = startDate.plusMonths(1).minusDays(1);
		List<Diary> diaries = diaryRepository.findByKindergartenIdAndDateBetweenOrderByDateDesc(kindergarten, startDate, endDate);
		return DiaryResponses.from(diaries);
	}

	public DiaryResponse findDiary(Long diaryId, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		Diary diary = getDiaryById(diaryId);
		validatePermission(diary, kindergarten);
		return DiaryResponse.from(diary);
	}

	@Transactional
	public DiarySaveResponse create(DiarySaveRequest request, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		Diary diary = createDiaryFromRequest(request, kindergarten);
		return DiarySaveResponse.from(diary.getId());
	}

	@Transactional
	public void update(Long diaryId, DiaryUpdateRequest request, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		Diary diary = getDiaryById(diaryId);
		validatePermission(diary, kindergarten);
		updateDiaryFields(diary, request);
		updateDiaryImages(diary, request.getImageIds());
	}

	@Transactional
	public void delete(Long diaryId, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		Diary diary = getDiaryById(diaryId);
		validatePermission(diary, kindergarten);
		diaryRepository.deleteById(diaryId);
	}

	private Kindergarten getKindergartenById(Long id) {
		return kindergartenRepository.findById(id).orElseThrow(() -> new KindergartenNotFoundException(id));
	}

	private Diary getDiaryById(Long id) {
		return diaryRepository.findById(id).orElseThrow(() -> new DiaryNotFoundException(id));
	}

	private void validatePermission(Diary diary, Kindergarten kindergarten) {
		if (!diary.getKindergarten().equals(kindergarten)) {
			throw new AuthorizationException();
		}
	}

	private Diary createDiaryFromRequest(DiarySaveRequest request, Kindergarten kindergarten) {
		Diary diary = Diary.of(
			request.getDogName(),
			request.getTitle(),
			request.getContent(),
			request.getDate(),
			kindergarten
		);

		List<DiaryImage> diaryImages = request.getImageIds().stream()
			.map(imageId -> {
				DiaryImage diaryImage = DiaryImage.of(imageId);
				diaryImage.setDiary(diary);
				return diaryImage;
			})
			.collect(Collectors.toList());

		diaryRepository.save(diary);
		diaryImageRepository.saveAll(diaryImages);
		return diary;
	}

	private void updateDiaryFields(Diary diary, DiaryUpdateRequest request) {
		diary.updateTitle(request.getTitle());
		diary.updateContent(request.getContent());
		diary.updateDate(request.getDate());
	}

	private void updateDiaryImages(Diary diary, List<Long> updatedImageIds) {
		List<DiaryImage> diaryImages = updatedImageIds.stream()
			.map(imageId -> {
				DiaryImage diaryImage = DiaryImage.of(imageId);
				diaryImage.setDiary(diary);
				return diaryImage;
			})
			.collect(Collectors.toList());

		diary.getDiaryImages().clear();
		diary.getDiaryImages().addAll(diaryImages);
		diaryImageRepository.saveAll(diaryImages);
	}
}