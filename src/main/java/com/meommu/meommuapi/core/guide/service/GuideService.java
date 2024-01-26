package com.meommu.meommuapi.core.guide.service;

import com.meommu.meommuapi.core.guide.dto.GuideDetailResponses;
import com.meommu.meommuapi.core.guide.dto.GuideDetailSaveRequest;
import com.meommu.meommuapi.core.guide.dto.GuideDetailSaveResponse;
import com.meommu.meommuapi.core.guide.dto.GuideResponses;

public interface GuideService {
	GuideResponses findGuides();

	GuideDetailResponses findGuideDetails(Long guideId);

	GuideDetailSaveResponse createGuideDetail(Long guideId, GuideDetailSaveRequest guideDetailSaveRequest);

	void deleteGuideDetail(Long guideId, Long detailId);

}
