package com.meommu.meommuapi.diary.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import com.meommu.meommuapi.diary.exception.InvalidContentFormatException;
import com.meommu.meommuapi.diary.exception.InvalidDogNameFormatException;
import com.meommu.meommuapi.diary.exception.InvalidTitleFormatException;
import com.meommu.meommuapi.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;

class DiaryTest {

	@Mock
	Kindergarten kindergarten;

	@DisplayName("일기를 생성할 수 있다.")
	@Test
	void createDiary() {
		// when
		var diary = Diary.of(
			"코코",
			"일기 제목",
			"일기 내용",
			LocalDate.now(),
			kindergarten
		);

		// then
		assertAll(
			() -> assertThat(diary.getDogName()).isEqualTo("코코"),
			() -> assertThat(diary.getTitle()).isEqualTo("일기 제목"),
			() -> assertThat(diary.getContent()).isEqualTo("일기 내용"),
			() -> assertThat(diary.getDate()).isNotNull(),
			() -> assertThat(diary.getKindergarten()).isEqualTo(kindergarten)
		);
	}

	@DisplayName("잘못된 제목 형식이 입력되면 예외가 발생한다.")
	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = {
		"AAAAAAAAAAAAAAAAAAAAA" // 20글자 이상
	})
	void createDiary_exception_invalidTitleFormat(String invalidTitle) {
		// when & then
		assertThatThrownBy(
			() -> Diary.of(
				"코코",
				invalidTitle,
				"일기 내용",
				LocalDate.now(),
				kindergarten
			))
			.isInstanceOf(InvalidTitleFormatException.class)
			.hasMessageContaining(DiaryErrorCode.TITLE_FORMAT_EXCEPTION.getDescription());
	}

	@DisplayName("잘못된 내용 형식이 입력되면 예외가 발생한다.")
	@Test
	void createDiary_exception_invalidContentFormat() {
		String invalidContent = "A".repeat(1001);

		// when & then
		assertThatThrownBy(
			() -> Diary.of(
				"코코",
				"일기 제목",
				invalidContent,
				LocalDate.now(),
				kindergarten
			))
			.isInstanceOf(InvalidContentFormatException.class)
			.hasMessageContaining(DiaryErrorCode.CONTENT_FORMAT_EXCEPTION.getDescription());
	}

	@DisplayName("잘못된 강아지 이름 형식이 입력되면 예외가 발생한다.")
	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = {
		"AAAAAAAAAAA" // 10글자 이상
	})
	void createDiary_exception_invalidDogNameFormat(String invalidDogName) {
		// when & then
		assertThatThrownBy(
			() -> Diary.of(
				invalidDogName,
				"일기 제목",
				"일기 내용",
				LocalDate.now(),
				kindergarten
			))
			.isInstanceOf(InvalidDogNameFormatException.class)
			.hasMessageContaining(DiaryErrorCode.DOG_NAME_FORMAT_EXCEPTION.getDescription());
	}
}