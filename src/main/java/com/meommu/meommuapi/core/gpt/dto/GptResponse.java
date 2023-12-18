package com.meommu.meommuapi.core.gpt.dto;

import java.util.List;

public record GptResponse(List<GptChoice> choices) {
}
