package com.stakely.fluffybarnacle.service;

import com.stakely.fluffybarnacle.dto.HabitCompletionRequestDto;
import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.repository.HabitCompletionRepository;
import com.stakely.fluffybarnacle.repository.HabitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HabitCompletionService {

  private final HabitCompletionRepository habitCompletionRepository;
  private final HabitRepository habitRepository;

  public HabitCompletionService(
      HabitCompletionRepository habitCompletionRepository, HabitRepository habitRepository) {
    this.habitCompletionRepository = habitCompletionRepository;
    this.habitRepository = habitRepository;
  }

  public List<HabitCompletion> findByHabitId(UUID habitId) {
    return habitCompletionRepository.findByHabitId(habitId);
  }

  @Transactional
  public HabitCompletion markHabitCompleted(UUID habitId, LocalDate dateCompleted) {
    Optional<HabitCompletion> existingCompletion =
        habitCompletionRepository.findByHabitIdAndDateCompleted(habitId, dateCompleted);
    if (existingCompletion.isPresent()) {
      return existingCompletion.get();
    }

    Habit habit = habitRepository.findById(habitId).orElseThrow();
    HabitCompletion completion = new HabitCompletion();
    completion.setHabit(habit);
    completion.setDateCompleted(dateCompleted);
    return habitCompletionRepository.save(completion);
  }
}
