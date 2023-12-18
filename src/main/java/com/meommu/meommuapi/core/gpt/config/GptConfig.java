package com.meommu.meommuapi.core.gpt.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.meommu.meommuapi.core.gpt.util.GptClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class GptConfig {
	@Value("${gpt.api-key}")
	private String API_KEY;

	@Bean
	public GptClient client(WebClient.Builder builder) {
		HttpClient httpClient = HttpClient.newConnection()
			.responseTimeout(Duration.ofSeconds(60))
			.keepAlive(false);

		WebClient wc = builder.baseUrl("https://api.openai.com")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY)
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.build();

		WebClientAdapter wca = WebClientAdapter.forClient(wc);
		return HttpServiceProxyFactory.builder()
			.blockTimeout(Duration.ofSeconds(60))
			.clientAdapter(wca)
			.build()
			.createClient(GptClient.class);
	}
}
