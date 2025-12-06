package com.stakely.fluffybarnacle.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/habits")
public class HabitController {

  @GetMapping("/{id}")
  public String getHabitById(@PathVariable String id) {
    return "This is a placeholder for getting a habit by ID.";
  }
}
