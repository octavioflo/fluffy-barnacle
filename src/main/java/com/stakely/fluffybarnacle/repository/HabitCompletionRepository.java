package com.stakely.fluffybarnacle.repository;

import com.stakely.fluffybarnacle.model.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, Long> {
  List<HabitCompletion> findByHabitId(Long habitId);

  boolean existsByHabitIdAndDateCompleted(Long habitId, LocalDate date);

  Optional<HabitCompletion> findByHabitIdAndDateCompleted(Long habitId, LocalDate dateCompleted);
}
