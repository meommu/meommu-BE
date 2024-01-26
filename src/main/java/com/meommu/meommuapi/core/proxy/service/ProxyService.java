package com.meommu.meommuapi.core.proxy.service;

import com.meommu.meommuapi.core.proxy.dto.Html2canvasRequest;

public interface ProxyService {

	byte[] html2canvasProxy(Html2canvasRequest request);
}
