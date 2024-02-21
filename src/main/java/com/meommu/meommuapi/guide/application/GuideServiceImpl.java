package com.meommu.meommuapi.guide.application;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.guide.domain.Guide;
import com.meommu.meommuapi.guide.domain.GuideDetail;
import com.meommu.meommuapi.guide.application.dto.GuideDetailResponses;
import com.meommu.meommuapi.guide.application.dto.GuideDetailSaveRequest;
import com.meommu.meommuapi.guide.application.dto.GuideDetailSaveResponse;
import com.meommu.meommuapi.guide.application.dto.GuideResponses;
import com.meommu.meommuapi.guide.exception.GuideDetailNotFoundException;
import com.meommu.meommuapi.guide.exception.GuideNotFoundException;
import com.meommu.meommuapi.guide.exception.InvalidIdException;
import com.meommu.meommuapi.guide.infrastructure.GuideDetailRepository;
import com.meommu.meommuapi.guide.infrastructure.GuideRepository;

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
	@Cacheable(cacheNames = "gpt_guide")
	public GuideResponses findGuides() {
		return GuideResponses.from(guideRepository.findAll());
	}

	@Override
	@Cacheable(cacheNames = "gpt_guide_detail")
	public GuideDetailResponses findGuideDetails(Long guideId) {
		List<GuideDetail> guideDetails = guideDetailRepository.findAllByGuideId(guideId);
		return GuideDetailResponses.from(guideDetails);
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = "gpt_guide_detail")
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
	@CacheEvict(cacheNames = "gpt_guide_detail")
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
