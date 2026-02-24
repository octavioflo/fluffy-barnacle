package com.stakely.fluffybarnacle.dto.punishment;

import java.util.UUID;

public record PunishmentResponseDto(UUID id, String type, String details) {}
