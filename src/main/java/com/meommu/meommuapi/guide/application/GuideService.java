package com.meommu.meommuapi.guide.application;

import com.meommu.meommuapi.guide.application.dto.GuideDetailResponses;
import com.meommu.meommuapi.guide.application.dto.GuideDetailSaveRequest;
import com.meommu.meommuapi.guide.application.dto.GuideDetailSaveResponse;
import com.meommu.meommuapi.guide.application.dto.GuideResponses;

public interface GuideService {
	GuideResponses findGuides();

	GuideDetailResponses findGuideDetails(Long guideId);

	GuideDetailSaveResponse createGuideDetail(Long guideId, GuideDetailSaveRequest guideDetailSaveRequest);

	void deleteGuideDetail(Long guideId, Long detailId);

}
