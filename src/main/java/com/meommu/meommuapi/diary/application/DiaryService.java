package com.meommu.meommuapi.diary.application;

import com.meommu.meommuapi.auth.application.dto.AuthInfo;
import com.meommu.meommuapi.diary.application.dto.DiaryResponse;
import com.meommu.meommuapi.diary.application.dto.DiaryResponses;
import com.meommu.meommuapi.diary.application.dto.DiarySaveRequest;
import com.meommu.meommuapi.diary.application.dto.DiarySaveResponse;
import com.meommu.meommuapi.diary.application.dto.DiarySearchCriteria;
import com.meommu.meommuapi.diary.application.dto.DiarySummaryResponses;
import com.meommu.meommuapi.diary.application.dto.DiaryUUIDResponse;
import com.meommu.meommuapi.diary.application.dto.DiaryUpdateRequest;

public interface DiaryService {

	DiarySummaryResponses findDiariesSummary(AuthInfo authInfo);

	DiaryResponses findDiaries(DiarySearchCriteria criteria, AuthInfo authInfo);

	DiaryResponse findDiary(Long diaryId, AuthInfo authInfo);

	DiarySaveResponse create(DiarySaveRequest request, AuthInfo authInfo);

	void update(Long diaryId, DiaryUpdateRequest request, AuthInfo authInfo);

	void delete(Long diaryId, AuthInfo authInfo);

	DiaryUUIDResponse findDiaryUUID(Long dairyId, AuthInfo authInfo);

	DiaryResponse findSharedDiary(String uuid);

}
