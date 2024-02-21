package com.meommu.meommuapi.diary.application;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.authentication.dto.AuthInfo;
import com.meommu.meommuapi.authentication.exception.AuthorizationException;
import com.meommu.meommuapi.diary.domain.Diary;
import com.meommu.meommuapi.diary.domain.DiaryImage;
import com.meommu.meommuapi.diary.dto.DiaryResponse;
import com.meommu.meommuapi.diary.dto.DiaryResponses;
import com.meommu.meommuapi.diary.dto.DiarySaveRequest;
import com.meommu.meommuapi.diary.dto.DiarySaveResponse;
import com.meommu.meommuapi.diary.dto.DiarySearchCriteria;
import com.meommu.meommuapi.diary.dto.DiarySummaryResponses;
import com.meommu.meommuapi.diary.dto.DiaryUUIDResponse;
import com.meommu.meommuapi.diary.dto.DiaryUpdateRequest;
import com.meommu.meommuapi.diary.exception.DiaryNotFoundException;
import com.meommu.meommuapi.diary.infrastructure.DiaryImageRepository;
import com.meommu.meommuapi.diary.infrastructure.DiaryRepository;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.exception.KindergartenNotFoundException;
import com.meommu.meommuapi.kindergarten.infrastructure.KindergartenRepository;

@Transactional(readOnly = true)
@Service
public class DiaryServiceImpl implements DiaryService {

	private final DiaryRepository diaryRepository;

	private final DiaryImageRepository diaryImageRepository;

	private final KindergartenRepository kindergartenRepository;

	public DiaryServiceImpl(DiaryRepository diaryRepository, DiaryImageRepository diaryImageRepository,
		KindergartenRepository kindergartenRepository) {
		this.diaryRepository = diaryRepository;
		this.diaryImageRepository = diaryImageRepository;
		this.kindergartenRepository = kindergartenRepository;
	}

	@Override
	public DiarySummaryResponses findDiariesSummary(AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		List<Diary> diaries = diaryRepository.findByKindergartenOrderByDateDescCreatedAtDesc(kindergarten);
		return DiarySummaryResponses.from(diaries);
	}

	@Override
	public DiaryResponses findDiaries(DiarySearchCriteria criteria, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		LocalDate startDate = LocalDate.of(criteria.getYear(), criteria.getMonth(), 1);
		LocalDate endDate = startDate.plusMonths(1).minusDays(1);
		List<Diary> diaries = diaryRepository.findByKindergartenAndDateBetweenOrderByDateDescCreatedAtDesc(kindergarten,
			startDate,
			endDate);
		return DiaryResponses.from(diaries);
	}

	@Override
	public DiaryResponse findDiary(Long diaryId, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		Diary diary = getDiaryById(diaryId);
		validateOwner(diary, kindergarten);
		return DiaryResponse.from(diary);
	}

	@Override
	@Transactional
	public DiarySaveResponse create(DiarySaveRequest request, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		Diary diary = createDiaryFromRequest(request, kindergarten);
		return DiarySaveResponse.from(diary);
	}

	@Override
	@Transactional
	public void update(Long diaryId, DiaryUpdateRequest request, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		Diary diary = getDiaryById(diaryId);
		validateOwner(diary, kindergarten);
		updateDiaryFields(request, diary);
	}

	@Override
	@Transactional
	public void delete(Long diaryId, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		Diary diary = getDiaryById(diaryId);
		validateOwner(diary, kindergarten);
		diaryRepository.deleteById(diaryId);
	}

	@Override
	public DiaryUUIDResponse findDiaryUUID(Long dairyId, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		Diary diary = getDiaryById(dairyId);
		validateOwner(diary, kindergarten);
		return DiaryUUIDResponse.from(diary);
	}

	@Override
	public DiaryResponse findSharedDiary(String uuid) {
		Diary diary = diaryRepository.findByUuid(uuid).orElseThrow(() -> new DiaryNotFoundException());
		return DiaryResponse.from(diary);
	}

	private Kindergarten getKindergartenById(Long id) {
		return kindergartenRepository.findById(id).orElseThrow(() -> new KindergartenNotFoundException(id));
	}

	private Diary getDiaryById(Long id) {
		return diaryRepository.findById(id).orElseThrow(() -> new DiaryNotFoundException(id));
	}

	private void validateOwner(Diary diary, Kindergarten kindergarten) {
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

		List<DiaryImage> diaryImages = createDiaryImagesFromImageIds(request.getImageIds(), diary);

		diaryRepository.save(diary);
		diaryImageRepository.saveAll(diaryImages);
		return diary;
	}

	private void updateDiaryFields(DiaryUpdateRequest request, Diary diary) {
		diary.updateTitle(request.getTitle());
		diary.updateContent(request.getContent());
		diary.updateDate(request.getDate());
		diary.updateDogName(request.getDogName());

		List<DiaryImage> diaryImages = createDiaryImagesFromImageIds(request.getImageIds(), diary);

		diary.updateImages(diaryImages);
		diaryImageRepository.saveAll(diaryImages);
	}

	private List<DiaryImage> createDiaryImagesFromImageIds(List<Long> imageIds, Diary diary) {
		return imageIds.stream()
			.map(imageId -> {
				DiaryImage diaryImage = DiaryImage.builder()
					.imageId(imageId)
					.diary(diary)
					.build();
				return diaryImage;
			})
			.collect(Collectors.toList());
	}

}
