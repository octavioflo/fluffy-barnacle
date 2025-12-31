package com.stakely.fluffybarnacle.controller;

import com.stakely.fluffybarnacle.dto.HabitCompletionRequestDto;
import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.service.HabitCompletionService;
import com.stakely.fluffybarnacle.service.HabitService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/habits")
public class HabitController {

  private final HabitService habitService;
  private final HabitCompletionService completionService;

  public HabitController(HabitService habitService, HabitCompletionService completionService) {
    this.completionService = completionService;
    this.habitService = habitService;
  }

  @GetMapping
  public List<Habit> getHabits() {
    return habitService.getHabits();
  }

  @GetMapping("/{id}")
  public Habit getHabitById(@PathVariable UUID id) {
    return habitService.getHabitById(id);
  }

  @PostMapping
  public Habit createHabit(@RequestBody Habit habit) {
    return habitService.createHabit(habit);
  }

  @GetMapping("/{id}/completions")
  public List<HabitCompletion> getCompletions(@PathVariable UUID id) {
    return completionService.findByHabitId(id);
  }

  @PostMapping("/{id}/completions")
  public HabitCompletion markCompletion(
      @PathVariable UUID id, @RequestBody HabitCompletionRequestDto request) {
    return completionService.markHabitCompleted(id, request.getDateCompleted());
  }
}
