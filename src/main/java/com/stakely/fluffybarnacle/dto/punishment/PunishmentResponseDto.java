package com.stakely.fluffybarnacle.dto.punishment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PunishmentResponseDto {
  private UUID id;
  private String type;
  private String details;
}
