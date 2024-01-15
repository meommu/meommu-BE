package com.meommu.meommuapi.core.guide.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.core.guide.domain.Guide;
import com.meommu.meommuapi.core.guide.domain.GuideDetail;
import com.meommu.meommuapi.core.guide.dto.GuideDetailResponses;
import com.meommu.meommuapi.core.guide.dto.GuideDetailSaveRequest;
import com.meommu.meommuapi.core.guide.dto.GuideDetailSaveResponse;
import com.meommu.meommuapi.core.guide.dto.GuideResponses;
import com.meommu.meommuapi.core.guide.exception.GuideDetailNotFoundException;
import com.meommu.meommuapi.core.guide.exception.GuideNotFoundException;
import com.meommu.meommuapi.core.guide.exception.InvalidIdException;
import com.meommu.meommuapi.core.guide.repository.GuideDetailRepository;
import com.meommu.meommuapi.core.guide.repository.GuideRepository;

@Transactional(readOnly = true)
@Service
public class GuideServiceImpl implements GuideService {

	private final GuideRepository guideRepository;

	private final GuideDetailRepository guideDetailRepository;

	public GuideServiceImpl(
		GuideRepository guideRepository,
		GuideDetailRepository guideDetailRepository) {
		this.guideRepository = guideRepository;
		this.guideDetailRepository = guideDetailRepository;
	}

	@Override
	public GuideResponses findGuides() {
		return GuideResponses.from(guideRepository.findAll());
	}

	@Override
	public GuideDetailResponses findGuideDetails(Long guideId) {
		List<GuideDetail> guideDetails = guideDetailRepository.findAllByGuideId(guideId);
		return GuideDetailResponses.from(guideDetails);
	}

	@Override
	@Transactional
	public GuideDetailSaveResponse createGuideDetail(
		Long guideId,
		GuideDetailSaveRequest guideDetailSaveRequest
	) {
		Guide guide = getGuideById(guideId);
		GuideDetail guideDetail = GuideDetail.of(guideDetailSaveRequest.getDetail(), guide);
		guideDetailRepository.save(guideDetail);
		return GuideDetailSaveResponse.from(guideDetail.getId());
	}

	@Override
	@Transactional
	public void deleteGuideDetail(Long guideId, Long detailId) {
		Guide guide = getGuideById(guideId);
		GuideDetail guideDetail = getGuideDetailById(detailId);
		validatePermission(guide, guideDetail);
		guideDetailRepository.delete(guideDetail);
	}

	private void validatePermission(Guide guide, GuideDetail guideDetail) {
		if (!guideDetail.getGuide().equals(guide)) {
			throw new InvalidIdException();
		}
	}

	private Guide getGuideById(Long id) {
		return guideRepository.findById(id).orElseThrow(() -> new GuideNotFoundException(id));
	}

	private GuideDetail getGuideDetailById(Long id) {
		return guideDetailRepository.findById(id).orElseThrow(() -> new GuideDetailNotFoundException(id));
	}

}