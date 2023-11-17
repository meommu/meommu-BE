package com.meommu.meommuapi.proxy.controller;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.util.IOUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Html2CanvasProxyController {

	@GetMapping("/html2canvas/proxy.json")
	public byte[] html2canvasProxy(HttpServletRequest req) {
		byte[] data = null;
		try {
			URL url = new URL(
				URLDecoder.decode(req.getParameter("url"), java.nio.charset.StandardCharsets.UTF_8.toString()));
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");

			if (connection.getResponseCode() == 200) {
				data = IOUtils.toByteArray(connection.getInputStream());
			} else {
				System.out.println("responseCode : " + connection.getResponseCode());
			}
		} catch (MalformedURLException e) {
			data = "wrong URL".getBytes(java.nio.charset.StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println(e);
		}
		return data;
	}
}