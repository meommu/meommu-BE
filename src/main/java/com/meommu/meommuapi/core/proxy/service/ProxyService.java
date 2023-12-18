package com.meommu.meommuapi.core.proxy.service;

import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.amazonaws.util.IOUtils;
import com.meommu.meommuapi.core.proxy.dto.Html2canvasRequest;
import com.meommu.meommuapi.core.proxy.exception.Html2canvasInternalException;

@Service
public class ProxyService {

	public byte[] html2canvasProxy(Html2canvasRequest request) {
		try {
			URL targetUrl = new URL(request.getUrl());
			HttpURLConnection connection = (HttpURLConnection)targetUrl.openConnection();
			connection.setRequestMethod("GET");
			return IOUtils.toByteArray(connection.getInputStream());
		} catch (Exception e) {
			throw new Html2canvasInternalException();
		}
	}
}
