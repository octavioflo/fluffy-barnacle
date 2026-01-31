package com.stakely.fluffybarnacle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class HabitCompletionResponseDto {
  private LocalDate dateCompleted;
}
