package com.stakely.fluffybarnacle.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class HabitCompletionRequestDto {
  private UUID id;
  private LocalDate dateCompleted;
}
