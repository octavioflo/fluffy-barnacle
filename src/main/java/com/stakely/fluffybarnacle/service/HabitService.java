package com.stakely.fluffybarnacle.service;

import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {

  private final HabitRepository habitRepository;

  public HabitService(HabitRepository habitRepository) {
    this.habitRepository = habitRepository;
  }

  public List<Habit> getHabits() {
    return habitRepository.findAll();
  }

  public Habit createHabit(Habit habit) {
    return habitRepository.save(habit);
  }
}
