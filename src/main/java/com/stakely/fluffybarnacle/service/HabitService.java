package com.stakely.fluffybarnacle.service;

import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HabitService {

  private final HabitRepository habitRepository;

  public HabitService(HabitRepository habitRepository) {
    this.habitRepository = habitRepository;
  }

  public List<Habit> getHabits() {
    return habitRepository.findAll();
  }

  public Habit getHabitById(UUID id) {
    return habitRepository.findById(id).orElse(null);
  }

  public Habit createHabit(Habit habit) {
    return habitRepository.save(habit);
  }
}
