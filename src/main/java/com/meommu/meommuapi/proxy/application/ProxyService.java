package com.meommu.meommuapi.proxy.application;

import com.meommu.meommuapi.proxy.dto.Html2canvasRequest;

public interface ProxyService {

	byte[] html2canvasProxy(Html2canvasRequest request);
}
