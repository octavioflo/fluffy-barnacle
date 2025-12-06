package com.stakely.fluffybarnacle.service;

import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.repository.HabitCompletionRepository;
import com.stakely.fluffybarnacle.repository.HabitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitCompletionService {

  private final HabitCompletionRepository habitCompletionRepository;
  private final HabitRepository habitRepository;

  public HabitCompletionService(
      HabitCompletionRepository habitCompletionRepository, HabitRepository habitRepository) {
    this.habitCompletionRepository = habitCompletionRepository;
    this.habitRepository = habitRepository;
  }

  public List<HabitCompletion> findByHabitId(Long habitId) {
    return habitCompletionRepository.findByHabitId(habitId);
  }

  @Transactional
  public HabitCompletion markHabitCompleted(Long habitId, LocalDate date) {
    if (habitId == null) {
      throw new IllegalArgumentException("habitId must not be null");
    }
    if (date == null) {
      throw new IllegalArgumentException("date must not be null");
    }

    Habit habit =
        habitRepository
            .findById(habitId)
            .orElseThrow(() -> new IllegalArgumentException("Habit not found: " + habitId));

    Optional<HabitCompletion> existing =
        habitCompletionRepository.findByHabitIdAndDateCompleted(habitId, date);
    if (existing.isPresent()) {
      return existing.get();
    }

    HabitCompletion completion = new HabitCompletion();
    completion.setHabit(habit);
    completion.setDateCompleted(date);

    return habitCompletionRepository.save(completion);
  }
}
