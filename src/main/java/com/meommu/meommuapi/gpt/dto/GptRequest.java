package com.meommu.meommuapi.gpt.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class GptRequest {
	private String model;
	private boolean stream;
	private List<GptMessage> messages;
	private double temperature;
	private int max_tokens;
	private double top_p;
	private double frequency_penalty;
	private double presence_penalty;
	private static final String CONTENT_FORMAT = "'''";
	public static final String CONTENT = "3개의 글머리 기호로 구분된 텍스트가 오늘의 한 일입니다. "
		+ "한 일은 콤마로 구분되어 전달됩니다. "
		+ "당신은 강아지 유치원을 다니는 강아지입니다. "
		+ "지금부터 오늘 한 일을 기반으로 강아지의 입장에서 일기를 써주세요. "
		+ "주어지는 한 일은 주인과 함께 한 일이 아닙니다. "
		+ "방금 문장은 일기 내용에서 제외해주세요. "
		+ "모두 유치원에서 일어난 일입니다. "
		+ "대략 50개의 단어로 일기를 작성해주세요.";

	public GptRequest(String details) {
		this.model = "gpt-3.5-turbo";
		this.stream = false;
		this.messages = new ArrayList<>();
		this.messages.add(new GptMessage("system", CONTENT));
		this.messages.add(new GptMessage("user", formatAndJoinMessages(details)));
		this.temperature = 0.5;
		this.max_tokens = 405;
		this.top_p = 0.5;
		this.frequency_penalty = 0.1;
		this.presence_penalty = 0;
	}

	private String formatAndJoinMessages(String origin) {
		return Arrays.stream(origin.split(","))
			.map(s -> CONTENT_FORMAT + s + CONTENT_FORMAT)
			.collect(Collectors.joining(","));
	}
}