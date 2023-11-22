package com.meommu.meommuapi.kindergarten.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.token.Auth;
import com.meommu.meommuapi.kindergarten.dto.EmailRequest;
import com.meommu.meommuapi.kindergarten.dto.KindergartenPasswordUpdateRequest;
import com.meommu.meommuapi.kindergarten.dto.MyInfoResponse;
import com.meommu.meommuapi.kindergarten.dto.KindergartenUpdateRequest;
import com.meommu.meommuapi.kindergarten.dto.KindergartenResponse;
import com.meommu.meommuapi.kindergarten.dto.SignUpRequest;
import com.meommu.meommuapi.kindergarten.service.KindergartenService;

import jakarta.validation.Valid;

@RestController
public class KindergartenController {

	private final KindergartenService kindergartenService;

	public KindergartenController(KindergartenService kindergartenService) {
		this.kindergartenService = kindergartenService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/kindergartens/signup")
	public MyInfoResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
		return kindergartenService.signUp(signUpRequest);
	}

	@GetMapping("/api/v1/kindergartens/email")
	public boolean checkEmailDuplication(@Valid @ModelAttribute EmailRequest emailRequest) {
		return kindergartenService.existsByEmail(emailRequest);
	}

	@GetMapping("/api/v1/kindergartens/me")
	public MyInfoResponse findMyInfo(@Auth AuthInfo authInfo) {
		return kindergartenService.findMyInfo(authInfo);
	}

	@GetMapping("/api/v1/kindergartens/{kindergartenId}")
	public KindergartenResponse findKindergarten(@PathVariable Long kindergartenId, @Auth AuthInfo authInfo) {
		return kindergartenService.find(kindergartenId, authInfo);
	}

	@PutMapping("/api/v1/kindergartens/{kindergartenId}")
	public void updateKindergarten(
		@PathVariable Long kindergartenId,
		@Valid @RequestBody KindergartenUpdateRequest kindergartenUpdateRequest,
		@Auth AuthInfo authInfo) {
		kindergartenService.update(kindergartenId, kindergartenUpdateRequest, authInfo);
	}

	@DeleteMapping("/api/v1/kindergartens/{kindergartenId}")
	public void deleteKindergarten(@PathVariable Long kindergartenId, @Auth AuthInfo authInfo) {
		kindergartenService.delete(kindergartenId, authInfo);
	}

	@PostMapping("/api/v1/kindergartens/email/verification-request")
	public void sendCodeToEmail(@RequestParam("email") String email) {
		kindergartenService.sendCodeToEmail(email);
	}

	@GetMapping("/api/v1/kindergartens/email/verification")
	public boolean verificationCode(@RequestParam("email") String email, @RequestParam("code") String code) {
		return kindergartenService.verifiedCode(email, code);
	}

	@PatchMapping("/api/v1/kindergartens/password")
	public void updatePassword(
		@RequestParam("email") String email,
		@Valid @RequestBody KindergartenPasswordUpdateRequest kindergartenPasswordUpdateRequest
	) {
		kindergartenService.updatePassword(email, kindergartenPasswordUpdateRequest);
	}
}
