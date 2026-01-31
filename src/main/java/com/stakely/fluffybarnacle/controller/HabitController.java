package com.stakely.fluffybarnacle.controller;

import com.stakely.fluffybarnacle.dto.HabitCompletionResponseDto;
import com.stakely.fluffybarnacle.dto.HabitRequestDto;
import com.stakely.fluffybarnacle.dto.HabitResponseDto;
import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.service.HabitCompletionService;
import com.stakely.fluffybarnacle.service.HabitService;
import jakarta.validation.Valid;
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
  public List<HabitResponseDto> getHabits() {
    return habitService.getHabits();
  }

  @GetMapping("/{id}")
  public HabitResponseDto getHabitById(@PathVariable UUID id) {
    return habitService.getHabitById(id);
  }

  @PostMapping
  public ResponseEntity<HabitResponseDto> createHabit(@Valid @RequestBody HabitRequestDto habit) {

    HabitResponseDto newHabit = habitService.createHabit(habit);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newHabit.getId())
            .toUri();
    return ResponseEntity.created(location).body(newHabit);
  }

  @GetMapping("/{id}/completions")
  public List<HabitCompletionResponseDto> getCompletions(@PathVariable UUID id) {
    return completionService.findByHabitId(id);
  }

  @PostMapping("/{id}/completions")
  public ResponseEntity<List<HabitCompletionResponseDto>> markCompletion(
      @PathVariable UUID id, @RequestBody HabitCompletionResponseDto request) {
    HabitCompletion habitCompletion =
        completionService.markHabitCompleted(id, request.getDateCompleted());
    HabitCompletionResponseDto responseDto =
        new HabitCompletionResponseDto(habitCompletion.getDateCompleted());
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{completionId}")
            .buildAndExpand(habitCompletion.getId())
            .toUri();
    return ResponseEntity.created(location).body(List.of(responseDto));
  }
}
