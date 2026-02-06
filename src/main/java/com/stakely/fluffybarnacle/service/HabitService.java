package com.stakely.fluffybarnacle.service;

import com.stakely.fluffybarnacle.dto.habit.HabitRequestDto;
import com.stakely.fluffybarnacle.dto.habit.HabitResponseDto;
import com.stakely.fluffybarnacle.dto.punishment.PunishmentRequestDto;
import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.model.Punishment;
import com.stakely.fluffybarnacle.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HabitService {

  private final HabitRepository habitRepository;

  public HabitService(HabitRepository habitRepository) {
    this.habitRepository = habitRepository;
  }

  public List<HabitResponseDto> getHabits() {
    List<Habit> habits = habitRepository.findAll();
    List<HabitResponseDto> habitResponseDtos = new ArrayList<>();
    for (Habit habit : habits) {
      habitResponseDtos.add(
          new HabitResponseDto(
              habit.getId(),
              habit.getName(),
              habit.getDescription(),
              habit.getDateCreated(),
              habit.getPunishment(),
              habit.getCompletions()));
    }
    return habitResponseDtos;
  }

  public HabitResponseDto getHabitById(UUID id) {
    Habit habit = habitRepository.findById(id).orElse(null);
    if (habit == null) {
      return null;
    }
    return new HabitResponseDto(
        habit.getId(),
        habit.getName(),
        habit.getDescription(),
        habit.getDateCreated(),
        habit.getPunishment(),
        habit.getCompletions());
  }

  public HabitResponseDto createHabit(HabitRequestDto habit) {
    PunishmentRequestDto punishmentRequestDto = habit.getPunishment();

    Habit newHabit =
        habitRepository.save(
            new Habit(
                habit.getName(),
                habit.getDescription(),
                LocalDate.now(),
                new Punishment(punishmentRequestDto.getType(), punishmentRequestDto.getDetails()),
                new ArrayList<>()));
    return new HabitResponseDto(
        newHabit.getId(),
        newHabit.getName(),
        newHabit.getDescription(),
        newHabit.getDateCreated(),
        newHabit.getPunishment(),
        newHabit.getCompletions());
  }
}
