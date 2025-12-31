package com.stakely.fluffybarnacle.repository;

import com.stakely.fluffybarnacle.model.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, UUID> {
  List<HabitCompletion> findByHabitId(UUID habitId);

  Optional<HabitCompletion> findByHabitIdAndDateCompleted(UUID habitId, LocalDate dateCompleted);
}
