package com.stakely.fluffybarnacle.dto.habit;

import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.model.Punishment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record HabitResponseDto(
    UUID id,
    String name,
    String description,
    LocalDate dateCreated,
    Punishment punishment,
    List<HabitCompletion> completions) {}
