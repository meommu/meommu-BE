package com.meommu.meommuapi.guide.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.exception.AuthorizationException;
import com.meommu.meommuapi.guide.domain.Guide;
import com.meommu.meommuapi.guide.domain.GuideDetail;
import com.meommu.meommuapi.guide.dto.GuideDetailResponses;
import com.meommu.meommuapi.guide.dto.GuideDetailSaveRequest;
import com.meommu.meommuapi.guide.dto.GuideDetailSaveResponse;
import com.meommu.meommuapi.guide.dto.GuideResponses;
import com.meommu.meommuapi.guide.exception.GuideDetailNotFoundException;
import com.meommu.meommuapi.guide.exception.GuideNotFoundException;
import com.meommu.meommuapi.guide.exception.InvalidIdException;
import com.meommu.meommuapi.guide.repository.GuideDetailRepository;
import com.meommu.meommuapi.guide.repository.GuideRepository;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.exception.KindergartenNotFoundException;
import com.meommu.meommuapi.kindergarten.repository.KindergartenRepository;

@Transactional(readOnly = true)
@Service
public class GuideService {

	private final GuideRepository guideRepository;

	private final GuideDetailRepository guideDetailRepository;

	public GuideService(
		GuideRepository guideRepository,
		GuideDetailRepository guideDetailRepository) {
		this.guideRepository = guideRepository;
		this.guideDetailRepository = guideDetailRepository;
	}

	public GuideResponses findGuides() {
		return GuideResponses.from(guideRepository.findAll());
	}

	public GuideDetailResponses findGuideDetails(Long guideId) {
		List<GuideDetail> guideDetails = guideDetailRepository.findAllByGuideId(guideId);
		return GuideDetailResponses.from(guideDetails);
	}

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
