package com.stakely.fluffybarnacle.dto.habit;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record HabitCompletionResponseDto(@NotNull @PastOrPresent LocalDate dateCompleted) {}
