package com.meommu.meommuapi.diary.infrastructure;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meommu.meommuapi.diary.domain.Diary;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

	List<Diary> findByKindergartenAndDateBetweenOrderByDateDescCreatedAtDesc(Kindergarten kindergarten, LocalDate startDate, LocalDate endDate);

	List<Diary> findByKindergartenOrderByDateDescCreatedAtDesc(Kindergarten kindergarten);

	Optional<Diary> findByUuid(String uuid);
}
