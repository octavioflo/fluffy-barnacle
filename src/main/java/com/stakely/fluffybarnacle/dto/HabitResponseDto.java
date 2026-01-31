package com.stakely.fluffybarnacle.dto;

import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.model.Punishment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class HabitResponseDto {
  private UUID id;
  private String name;
  private String description;
  private LocalDate dateCreated;
  private Punishment punishment;
  private List<HabitCompletion> completions;
}
