package com.meommu.meommuapi.guide.service;

import com.meommu.meommuapi.guide.dto.GuideDetailResponses;
import com.meommu.meommuapi.guide.dto.GuideDetailSaveRequest;
import com.meommu.meommuapi.guide.dto.GuideDetailSaveResponse;
import com.meommu.meommuapi.guide.dto.GuideResponses;

public interface GuideService {
	GuideResponses findGuides();

	GuideDetailResponses findGuideDetails(Long guideId);

	GuideDetailSaveResponse createGuideDetail(Long guideId, GuideDetailSaveRequest guideDetailSaveRequest);

	void deleteGuideDetail(Long guideId, Long detailId);

}
