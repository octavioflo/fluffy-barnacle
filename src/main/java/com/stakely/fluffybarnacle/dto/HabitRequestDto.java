package com.stakely.fluffybarnacle.dto;

import com.stakely.fluffybarnacle.model.Punishment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HabitRequestDto {
  @NotNull(message = "Name cannot be null")
  @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
  private String name;

  @NotNull(message = "Description cannot be null")
  @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
  private String description;

  private Punishment punishment;
}
