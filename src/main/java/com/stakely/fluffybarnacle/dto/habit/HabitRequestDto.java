package com.stakely.fluffybarnacle.dto.habit;

import com.stakely.fluffybarnacle.dto.punishment.PunishmentRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record HabitRequestDto(
    @NotEmpty(message = "Name cannot be null or empty")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name,
    @NotEmpty(message = "Description cannot be null or empty")
        @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
        String description,
    PunishmentRequestDto punishment) {}
