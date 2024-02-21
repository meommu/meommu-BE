package com.meommu.meommuapi.gpt.application.dto;

import java.util.List;

public record GptResponse(List<GptChoice> choices) {
}
