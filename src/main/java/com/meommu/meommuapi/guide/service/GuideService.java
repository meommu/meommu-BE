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

	private final KindergartenRepository kindergartenRepository;

	public GuideService(
		GuideRepository guideRepository,
		GuideDetailRepository guideDetailRepository,
		KindergartenRepository kindergartenRepository
	) {
		this.guideRepository = guideRepository;
		this.guideDetailRepository = guideDetailRepository;
		this.kindergartenRepository = kindergartenRepository;
	}

	public GuideResponses findGuides() {
		return GuideResponses.from(guideRepository.findAll());
	}

	public GuideDetailResponses findGuideDetails(Long guideId, AuthInfo authInfo) {
		List<GuideDetail> guideDetails = guideDetailRepository.findAllByGuideIdAndKindergartenId(
			guideId, authInfo.getId());
		return GuideDetailResponses.from(guideDetails);
	}

	@Transactional
	public GuideDetailSaveResponse createGuideDetail(
		Long guideId,
		GuideDetailSaveRequest guideDetailSaveRequest,
		AuthInfo authInfo
	) {
		Guide guide = getGuideById(guideId);
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		GuideDetail guideDetail = GuideDetail.of(guideDetailSaveRequest.getDetail(), guide, kindergarten);
		guideDetailRepository.save(guideDetail);
		return GuideDetailSaveResponse.from(guideDetail.getId());
	}

	@Transactional
	public void deleteGuideDetail(Long guideId, Long detailId, AuthInfo authInfo) {
		Guide guide = getGuideById(guideId);
		GuideDetail guideDetail = getGuideDetailById(detailId);
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		validatePermission(guide, guideDetail, kindergarten);
		guideDetailRepository.delete(guideDetail);
	}

	private void validatePermission(Guide guide, GuideDetail guideDetail, Kindergarten kindergarten) {
		if (!guideDetail.getGuide().equals(guide)) {
			throw new InvalidIdException();
		}

		if (!guideDetail.getKindergarten().equals(kindergarten)) {
			throw new AuthorizationException();
		}
	}

	private Guide getGuideById(Long id) {
		return guideRepository.findById(id).orElseThrow(() -> new GuideNotFoundException(id));
	}

	private GuideDetail getGuideDetailById(Long id) {
		return guideDetailRepository.findById(id).orElseThrow(() -> new GuideDetailNotFoundException(id));
	}

	private Kindergarten getKindergartenById(Long id) {
		return kindergartenRepository.findById(id).orElseThrow(() -> new KindergartenNotFoundException(id));
	}

}
