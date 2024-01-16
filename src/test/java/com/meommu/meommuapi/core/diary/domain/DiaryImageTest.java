package com.meommu.meommuapi.core.diary.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class DiaryImageTest {

	@Mock
	Diary diary;

	@DisplayName("일기를 생성할 수 있다.")
	@Test
	void createDiary() {
		// when
		var diaryImage = DiaryImage.builder()
			.imageId(1L)
			.diary(diary)
			.build();

		// then
		assertThat(diaryImage.getImageId()).isEqualTo(1L);
	}

}