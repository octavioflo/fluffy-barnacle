package com.stakely.fluffybarnacle.controller;

import com.stakely.fluffybarnacle.dto.HabitCompletionRequestDto;
import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.service.HabitCompletionService;
import com.stakely.fluffybarnacle.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
  public ResponseEntity<Habit> createHabit(@RequestBody Habit habit) {

    Habit newHabit = habitService.createHabit(habit);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newHabit.getId())
            .toUri();
    return ResponseEntity.created(location).body(habit);
  }

  @GetMapping("/{id}/completions")
  public List<HabitCompletion> getCompletions(@PathVariable UUID id) {
    return completionService.findByHabitId(id);
  }

  @PostMapping("/{id}/completions")
  public ResponseEntity<List<HabitCompletionRequestDto>> markCompletion(
      @PathVariable UUID id, @RequestBody HabitCompletionRequestDto request) {
    HabitCompletion habitCompletion =
        completionService.markHabitCompleted(id, request.getDateCompleted());
    HabitCompletionRequestDto responseDto =
        new HabitCompletionRequestDto(habitCompletion.getDateCompleted());
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{completionId}")
            .buildAndExpand(habitCompletion.getId())
            .toUri();
    return ResponseEntity.created(location).body(List.of(responseDto));
  }
}
