package com.stakely.fluffybarnacle.dto.punishment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PunishmentRequestDto(
    @NotEmpty(message = "Punishment type cannot be null or empty")
        @Size(min = 1, max = 50, message = "Punishment type must be between 1 and 50 characters")
        String type,
    @NotEmpty(message = "Punishment details cannot be null or empty")
        @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
        String details) {}
