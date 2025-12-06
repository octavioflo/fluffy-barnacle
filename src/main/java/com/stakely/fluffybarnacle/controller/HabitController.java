package com.stakely.fluffybarnacle.controller;

import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.service.HabitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/habits")
public class HabitController {

  HabitService habitService;

  public HabitController(HabitService habitService) {
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
}
