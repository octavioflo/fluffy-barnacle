package com.stakely.fluffybarnacle.controller;

import com.stakely.fluffybarnacle.dto.HabitCompletionRequestDto;
import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.service.HabitCompletionService;
import com.stakely.fluffybarnacle.service.HabitService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

  @PostMapping
  public Habit createHabit(@RequestBody Habit habit) {
    return habitService.createHabit(habit);
  }

  @GetMapping("/{id}/completions")
  public List<HabitCompletion> getCompletions(@PathVariable Long id) {
    return completionService.findByHabitId(id);
  }

  @PostMapping("/{id}/completions")
  public HabitCompletion markCompletion(
      @PathVariable Long id, @RequestBody HabitCompletionRequestDto request) {
    // TODO: There's a bug where the completion date is added recursively.
    return completionService.markHabitCompleted(id, request.getDate());
  }
}
