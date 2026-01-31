package com.stakely.fluffybarnacle.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class HabitCompletionRequestDto {
  private LocalDate dateCompleted;

  public HabitCompletionRequestDto(LocalDate dateCompleted) {
    this.dateCompleted = dateCompleted;
  }
}
