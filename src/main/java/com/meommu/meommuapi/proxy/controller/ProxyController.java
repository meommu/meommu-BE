package com.meommu.meommuapi.proxy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.proxy.dto.Html2canvasRequest;
import com.meommu.meommuapi.proxy.service.ProxyService;

import jakarta.validation.Valid;

@RestController
public class ProxyController {

	private final ProxyService proxyService;

	public ProxyController(ProxyService proxyService) {
		this.proxyService = proxyService;
	}

	@GetMapping("/api/v1/proxy")
	public byte[] html2canvasProxy(@Valid @ModelAttribute Html2canvasRequest request) {
		return proxyService.html2canvasProxy(request);
	}
}