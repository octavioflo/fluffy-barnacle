package com.stakely.fluffybarnacle.dto.habit;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class HabitCompletionResponseDto {
  @NotNull @PastOrPresent private LocalDate dateCompleted;
}
