package com.stakely.fluffybarnacle.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HabitCompletionRequestDto {
  private LocalDate date;
}
