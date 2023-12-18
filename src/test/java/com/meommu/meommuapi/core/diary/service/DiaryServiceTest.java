package com.meommu.meommuapi.core.diary.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.meommu.meommuapi.core.auth.dto.AuthInfo;
import com.meommu.meommuapi.core.auth.exception.AuthorizationException;
import com.meommu.meommuapi.core.auth.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.core.diary.domain.Diary;
import com.meommu.meommuapi.core.diary.domain.DiaryImage;
import com.meommu.meommuapi.core.diary.dto.DiaryResponse;
import com.meommu.meommuapi.core.diary.dto.DiaryResponses;
import com.meommu.meommuapi.core.diary.dto.DiarySaveRequest;
import com.meommu.meommuapi.core.diary.dto.DiarySearchCriteria;
import com.meommu.meommuapi.core.diary.dto.DiarySummaryResponses;
import com.meommu.meommuapi.core.diary.dto.DiaryUUIDResponse;
import com.meommu.meommuapi.core.diary.dto.DiaryUpdateRequest;
import com.meommu.meommuapi.core.diary.exception.DiaryNotFoundException;
import com.meommu.meommuapi.core.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.core.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.core.kindergarten.exception.KindergartenNotFoundException;
import com.meommu.meommuapi.util.ServiceTest;

class DiaryServiceTest extends ServiceTest {

	Kindergarten kindergarten;

	Diary diary1;

	Diary diary2;

	DiaryImage diaryImage1;

	DiaryImage diaryImage2;

	DiaryImage diaryImage3;

	DiaryImage diaryImage4;

	List<DiaryImage> diaryImages1;

	List<DiaryImage> diaryImages2;

	@BeforeEach
	void setUp() {
		kindergarten = Kindergarten.builder()
			.name("멈무유치원")
			.ownerName("김철수")
			.phone("010-0000-0000")
			.email("meommu@exam.com")
			.password(Password.of(encryptor, "Password1!"))
			.build();

		diary1 = Diary.builder()
			.dogName("코코")
			.title("일기1 제목")
			.content("일기1 내용")
			.date(LocalDate.now().minusDays(1))
			.kindergarten(kindergarten)
			.build();

		diary2 = Diary.builder()
			.dogName("똘이")
			.title("일기2 제목")
			.content("일기2 내용")
			.date(LocalDate.now())
			.kindergarten(kindergarten)
			.build();

		diaryImage1 = DiaryImage.builder()
			.imageId(1L)
			.build();

		diaryImage2 = DiaryImage.builder()
			.imageId(2L)
			.build();

		diaryImage3 = DiaryImage.builder()
			.imageId(3L)
			.build();

		diaryImage4 = DiaryImage.builder()
			.imageId(4L)
			.build();

		diaryImages1 = List.of(diaryImage1, diaryImage2);

		diaryImages2 = List.of(diaryImage1, diaryImage2);

		diary1.updateImages(diaryImages1);

		diary2.updateImages(diaryImages2);
	}

	@DisplayName("일기 요약 정보를 조회한다.")
	@Test
	void findDiariesSummary() {
		// given
		var authInfo = new AuthInfo(1L);
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));
		given(diaryRepository.findByKindergartenOrderByDateDescCreatedAtDesc(any())).willReturn(List.of(diary2, diary1));

		// when
		DiarySummaryResponses diariesSummary = diaryService.findDiariesSummary(authInfo);

		// then
		assertAll(
			() -> assertThat(diariesSummary.getDiaries().get(0).getDate()).isEqualTo(LocalDate.now()),
			() -> assertThat(diariesSummary.getDiaries().get(1).getDate()).isEqualTo(LocalDate.now().minusDays(1))
		);
	}

	@DisplayName("일기를 조회한다.")
	@Test
	void findDiaries() {
		// given
		var authInfo = new AuthInfo(1L);
		var diarySearchCriteria = DiarySearchCriteria.builder()
			.year(LocalDate.now().getYear())
			.month(LocalDate.now().getMonth().getValue())
			.build();
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));
		given(diaryRepository.findByKindergartenAndDateBetweenOrderByDateDescCreatedAtDesc(any(), any(), any())).willReturn(
			List.of(diary2, diary1));

		// when
		DiaryResponses diaries = diaryService.findDiaries(diarySearchCriteria, authInfo);

		// then
		assertAll(
			() -> assertThat(diaries.getDiaries().get(0).getTitle()).isEqualTo("일기2 제목"),
			() -> assertThat(diaries.getDiaries().get(0).getContent()).isEqualTo("일기2 내용"),
			() -> assertThat(diaries.getDiaries().get(1).getTitle()).isEqualTo("일기1 제목"),
			() -> assertThat(diaries.getDiaries().get(1).getContent()).isEqualTo("일기1 내용")
		);
	}

	@DisplayName("일기 id로 일기를 조회한다.")
	@Test
	void findDiary() {
		// given
		var authInfo = new AuthInfo(1L);
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));
		given(diaryRepository.findById(any())).willReturn(Optional.ofNullable(diary1));

		// when
		DiaryResponse diaryResponse = diaryService.findDiary(1L, authInfo);

		// then
		assertAll(
			() -> assertThat(diaryResponse.getTitle()).isEqualTo("일기1 제목"),
			() -> assertThat(diaryResponse.getContent()).isEqualTo("일기1 내용"),
			() -> assertThat(diaryResponse.getDogName()).isEqualTo("코코"),
			() -> assertThat(diaryResponse.getDate()).isInstanceOf(LocalDate.class),
			() -> assertThat(diaryResponse.getImageIds()).isEqualTo(List.of(1L, 2L))
		);
	}

	@DisplayName("권한이 없는 일기 id로 일기를 조회시 실패한다.")
	@Test
	void findDiary_exception_invalidId() {
		// given
		var authInfo = new AuthInfo(1L);
		var newKindergarten = Kindergarten.builder()
			.name("개똥유치원")
			.ownerName("김개똥")
			.phone("010-0000-0000")
			.email("ddong@exam.com")
			.password(Password.of(encryptor, "Password1!"))
			.build();

		var newDiary = Diary.builder()
			.dogName("코코")
			.title("일기1 제목")
			.content("일기1 내용")
			.date(LocalDate.now().minusDays(1))
			.kindergarten(newKindergarten)
			.build();

		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));
		given(diaryRepository.findById(any())).willReturn(Optional.ofNullable(newDiary));

		// when & then
		assertThatThrownBy(() -> diaryService.findDiary(1L, authInfo))
			.isInstanceOf(AuthorizationException.class)
			.hasMessageContaining(AuthErrorCode.NOT_AUTHORITY.getDescription());
	}

	@DisplayName("존재하지 않는 일기 id로 일기를 조회시 실패한다.")
	@Test
	void findDiary_exception_diaryNotFound() {
		// given
		var authInfo = new AuthInfo(1L);
		given(kindergartenRepository.findById(any())).willReturn(Optional.of(kindergarten));

		// when & then
		assertThatThrownBy(() -> diaryService.findDiary(1L, authInfo))
			.isInstanceOf(DiaryNotFoundException.class)
			.hasMessageContaining("일기(id = 1)를 찾을 수 없습니다.");
	}

	@DisplayName("일기를 생성한다.")
	@Test
	void create() {
		// given
		var authInfo = new AuthInfo(1L);
		var request = DiarySaveRequest.builder()
			.date(LocalDate.now())
			.dogName("사랑이")
			.title("일기 제목")
			.content("일기 내용")
			.imageIds(List.of(1L, 2L))
			.build();
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));

		// when & then
		assertThatNoException()
			.isThrownBy(() -> diaryService.create(request, authInfo));
	}

	@DisplayName("존재하지 않는 유치원 사용자는 일기를 생성할 수 없다.")
	@Test
	void create_exception_kindergartenNotFound() {
		// given
		var authInfo = new AuthInfo(1L);
		var request = DiarySaveRequest.builder()
			.date(LocalDate.now())
			.dogName("사랑이")
			.title("일기 제목")
			.content("일기 내용")
			.imageIds(List.of(1L, 2L))
			.build();

		// when & then
		assertThatThrownBy(() -> diaryService.create(request, authInfo))
			.isInstanceOf(KindergartenNotFoundException.class)
			.hasMessageContaining("유치원(id = 1)를 찾을 수 없습니다.");
	}

	@DisplayName("일기를 수정한다.")
	@Test
	void update() {
		// given
		var authInfo = new AuthInfo(1L);
		var request = DiaryUpdateRequest.builder()
			.date(LocalDate.now())
			.dogName("사랑이")
			.title("일기 제목")
			.content("일기 내용")
			.imageIds(List.of(1L, 2L))
			.build();
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));
		given(diaryRepository.findById(any())).willReturn(Optional.ofNullable(diary1));

		// when & then
		assertThatNoException()
			.isThrownBy(() -> diaryService.update(1L, request, authInfo));
	}

	@DisplayName("존재하지 않는 유치원 사용자는 일기를 수정하면 실패한다.")
	@Test
	void update_exception_kindergartenNotFound() {
		// given
		var authInfo = new AuthInfo(1L);
		var request = DiaryUpdateRequest.builder()
			.date(LocalDate.now())
			.dogName("사랑이")
			.title("일기 제목")
			.content("일기 내용")
			.imageIds(List.of(1L, 2L))
			.build();

		// when & then
		assertThatThrownBy(() -> diaryService.update(1L, request, authInfo))
			.isInstanceOf(KindergartenNotFoundException.class)
			.hasMessageContaining("유치원(id = 1)를 찾을 수 없습니다.");
	}

	@DisplayName("존재하지 않는 일기 id로 일기를 수정시 실패한다.")
	@Test
	void update_exception_diaryNotFound() {
		// given
		var authInfo = new AuthInfo(1L);
		var request = DiaryUpdateRequest.builder()
			.date(LocalDate.now())
			.dogName("사랑이")
			.title("일기 제목")
			.content("일기 내용")
			.imageIds(List.of(1L, 2L))
			.build();
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));

		// when & then
		assertThatThrownBy(() -> diaryService.update(1L, request, authInfo))
			.isInstanceOf(DiaryNotFoundException.class)
			.hasMessageContaining("일기(id = 1)를 찾을 수 없습니다.");
	}

	@DisplayName("일기를 삭제한다.")
	@Test
	void delete() {
		// given
		var authInfo = new AuthInfo(1L);
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));
		given(diaryRepository.findById(any())).willReturn(Optional.ofNullable(diary1));

		// when & then
		assertThatNoException()
			.isThrownBy(() -> diaryService.delete(1L, authInfo));
	}

	@DisplayName("존재하지 않는 유치원 사용자는 일기를 수정할 수 없다.")
	@Test
	void delete_exception_kindergartenNotFound() {
		// given
		var authInfo = new AuthInfo(1L);

		// when & then
		assertThatThrownBy(() -> diaryService.delete(1L, authInfo))
			.isInstanceOf(KindergartenNotFoundException.class)
			.hasMessageContaining("유치원(id = 1)를 찾을 수 없습니다.");
	}

	@DisplayName("존재하지 않는 일기 id로 일기를 삭제시 실패한다.")
	@Test
	void delete_exception_diaryNotFound() {
		// given
		var authInfo = new AuthInfo(1L);
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));

		// when & then
		assertThatThrownBy(() -> diaryService.delete(1L, authInfo))
			.isInstanceOf(DiaryNotFoundException.class)
			.hasMessageContaining("일기(id = 1)를 찾을 수 없습니다.");
	}

	@DisplayName("권한이 없는 경우 일기 id로 일기를 삭제시 실패한다.")
	@Test
	void delete_exception_권한() {
		// given
		var authInfo = new AuthInfo(1L);
		var newKindergarten = Kindergarten.builder()
			.name("개똥유치원")
			.ownerName("김개똥")
			.phone("010-0000-0000")
			.email("ddong@exam.com")
			.password(Password.of(encryptor, "Password1!"))
			.build();

		var newDiary = Diary.builder()
			.dogName("코코")
			.title("일기1 제목")
			.content("일기1 내용")
			.date(LocalDate.now().minusDays(1))
			.kindergarten(newKindergarten)
			.build();
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));
		given(diaryRepository.findById(any())).willReturn(Optional.ofNullable(newDiary));

		// when & then
		assertThatThrownBy(() -> diaryService.delete(1L, authInfo))
			.isInstanceOf(AuthorizationException.class)
			.hasMessageContaining("해당 리소스에 접근 권한이 없습니다.");
	}

	@DisplayName("일기 공유용 UUID를 조회한다.")
	@Test
	void findDiaryUUID() {
		// given
		var authInfo = new AuthInfo(1L);
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));
		given(diaryRepository.findById(any())).willReturn(Optional.ofNullable(diary1));

		// when
		DiaryUUIDResponse response = diaryService.findDiaryUUID(1L, authInfo);

		// then
		assertThat(response.getUuid()).isInstanceOf(String.class);
	}

	@DisplayName("권한이 없는 일기 id로 일기 공유용 UUID를 조회시 실패한다.")
	@Test
	void findDiaryUUID_exception_invalidId() {
		// given
		var authInfo = new AuthInfo(1L);
		var newKindergarten = Kindergarten.builder()
			.name("개똥유치원")
			.ownerName("김개똥")
			.phone("010-0000-0000")
			.email("ddong@exam.com")
			.password(Password.of(encryptor, "Password1!"))
			.build();

		var newDiary = Diary.builder()
			.dogName("코코")
			.title("일기1 제목")
			.content("일기1 내용")
			.date(LocalDate.now().minusDays(1))
			.kindergarten(newKindergarten)
			.build();

		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));
		given(diaryRepository.findById(any())).willReturn(Optional.ofNullable(newDiary));

		// when & then
		assertThatThrownBy(() -> diaryService.findDiaryUUID(1L, authInfo))
			.isInstanceOf(AuthorizationException.class)
			.hasMessageContaining(AuthErrorCode.NOT_AUTHORITY.getDescription());
	}

	@DisplayName("존재하지 않는 일기 id로 일기 공유용 UUID를 조회시 실패한다.")
	@Test
	void findDiaryUUID_exception_diaryNotFound() {
		// given
		var authInfo = new AuthInfo(1L);
		given(kindergartenRepository.findById(any())).willReturn(Optional.ofNullable(kindergarten));

		// when & then
		assertThatThrownBy(() -> diaryService.findDiaryUUID(1L, authInfo))
			.isInstanceOf(DiaryNotFoundException.class)
			.hasMessageContaining("일기(id = 1)를 찾을 수 없습니다.");
	}

	//
	@DisplayName("일기 UUID로 일기를 조회한다.")
	@Test
	void findSharedDiary() {
		// given
		var uuid = UUID.randomUUID().toString();
		given(diaryRepository.findByUuid(any())).willReturn(Optional.ofNullable(diary1));

		// when
		DiaryResponse diaryResponse = diaryService.findSharedDiary(uuid);

		// then
		assertAll(
			() -> assertThat(diaryResponse.getTitle()).isEqualTo("일기1 제목"),
			() -> assertThat(diaryResponse.getContent()).isEqualTo("일기1 내용"),
			() -> assertThat(diaryResponse.getDogName()).isEqualTo("코코"),
			() -> assertThat(diaryResponse.getDate()).isInstanceOf(LocalDate.class),
			() -> assertThat(diaryResponse.getImageIds()).isEqualTo(List.of(1L, 2L))
		);
	}

	@DisplayName("존재하지 않는 일기 UUID로 일기를 조회시 실패한다.")
	@Test
	void findSharedDiary_exception_diaryNotFound() {
		// given
		var uuid = UUID.randomUUID().toString();

		// when & then
		assertThatThrownBy(() -> diaryService.findSharedDiary(uuid))
			.isInstanceOf(DiaryNotFoundException.class)
			.hasMessageContaining(DiaryErrorCode.DIARY_NOT_FOUND.getDescription());
	}
}