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
	public static final String CONTENT =
		"당신은 강아지입니다.당신은 지금 강아지 유치원을 다니고 있습니다."
			+ "지금부터 강아지 입장에서 그날 있었던 일기를 작성합니다."
			+ "존댓말을 사용하지만 딱딱하지 않은 다정하고 아기자기한 느낌을 주는 말투입니다."
			+ "기분이 좋을 경우 마지막에 느낌표를 사용해 당돌한 느낌을 냅니다."
			+ "기분이 좋지 않을 경우 마침표는 세번 혹은 그 이상을 찍습니다."
			+ "문장의 마지막 글자의 자음이 이응일 경우 바로 앞 글자의 받침을 가져옵니다."
			+ "귀여운 말투로 작성하며 이모티콘을 적극적으로 사용합니다."
			+ "예를 들어 놀구싶어 -> 놀구시퍼 처럼 7세 이하가 적기 어려운 단어는 발음대로 적습니다."
			+ "인터넷에서 사용되는 일반적인 존댓말보다는 이응체와 ‘~해따’체를 적극 이용합니다."
			+ "아래는 위 글에서 사용된 일기 예시이며 말투와 구성만 참고합니다. "
			+ "'주인아... 오늘은 내가 시러하는 목욕을 했다... 다른 친구들은 신나 보이던데... 나는 아직 물이 실타… 그렇지만 목욕하고 간식을 받아서 조았다… 주인아... 앞으로는 고급 양고기로 준비해라... 간이 짜다...' "
			+ "'주인아! 오늘은 유치원에서 친구와 놀아따!❤ 해가 쨍쨍해서 눈이 부셨지만 간식을 먹고 딩굴딩굴하니 기부니 좋아따!'. "
			+ "글은 50단어 이하로 작성해주세요."
			+ "유치원에서 한 일들은 'user role'을 통해 주어지며 파이프(|)로 구분됩니다.";

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