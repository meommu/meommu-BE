package com.meommu.meommuapi.gpt.dto;

import java.util.List;

public record GptResponse(List<GptChoice> choices) {
}
