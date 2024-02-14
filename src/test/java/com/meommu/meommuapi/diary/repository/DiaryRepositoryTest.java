package com.meommu.meommuapi.diary.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.meommu.meommuapi.diary.domain.Diary;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.util.RepositoryTest;

class DiaryRepositoryTest extends RepositoryTest {

	Kindergarten kindergarten;

	Diary diary1;

	Diary diary2;

	@BeforeEach
	void setUp() {
		kindergarten = Kindergarten.of(
			"멈무유치원",
			"김철수",
			"010-0000-0000",
			"meommu@exam.com",
			Password.of(encryptor, "Password1!"));
		kindergartenRepository.save(kindergarten);

		diary1 = Diary.builder()
			.dogName("코코")
			.title("일기1 제목")
			.content("일기1 내용")
			.date(LocalDate.now().minusDays(1))
			.kindergarten(kindergarten)
			.build();
		diaryRepository.save(diary1);

		diary2 = Diary.builder()
			.dogName("똘이")
			.title("일기2 제목")
			.content("일기2 내용")
			.date(LocalDate.now())
			.kindergarten(kindergarten)
			.build();
		diaryRepository.save(diary2);
	}

	@DisplayName("기간별로 회원의 일기를 최신순으로 조회할 수 있다.")
	@Test
	void findByKindergartenAndDateBetweenOrderByDateDesc() {
		//when
		LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
		LocalDate endDate = startDate.plusMonths(1).minusDays(1);
		List<Diary> diaries = diaryRepository.findByKindergartenAndDateBetweenOrderByDateDescCreatedAtDesc(
			kindergarten, startDate, endDate);

		// then
		assertAll(
			() -> assertThat(diaries.get(0).getTitle()).isEqualTo("일기2 제목"),
			() -> assertThat(diaries.get(0).getContent()).isEqualTo("일기2 내용")
		);
	}

	@DisplayName("회원의 일기를 최신순으로 조회할 수 있다.")
	@Test
	void findByKindergartenOrderByDateDesc() {
		List<Diary> diaries = diaryRepository.findByKindergartenOrderByDateDescCreatedAtDesc(kindergarten);

		// then
		assertAll(
			() -> assertThat(diaries.get(0).getTitle()).isEqualTo("일기2 제목"),
			() -> assertThat(diaries.get(0).getContent()).isEqualTo("일기2 내용")
		);
	}

	@DisplayName("UUID로 일기를 조회할 수 있다.")
	@Test
	void findByUuid() {
		Diary diary = diaryRepository.findByUuid(diary1.getUUID()).get();

		// then
		assertAll(
			() -> assertThat(diary.getTitle()).isEqualTo("일기1 제목"),
			() -> assertThat(diary.getContent()).isEqualTo("일기1 내용")
		);
	}

}