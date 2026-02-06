package com.stakely.fluffybarnacle.controller;

import com.stakely.fluffybarnacle.dto.punishment.PunishmentRequestDto;
import com.stakely.fluffybarnacle.dto.punishment.PunishmentResponseDto;
import com.stakely.fluffybarnacle.service.PunishmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/punishments")
public class PunishmentController {

  @Autowired PunishmentService punishmentService;

  @GetMapping
  public List<PunishmentResponseDto> getPunishments() {
    return punishmentService.getPunishments();
  }

  @PostMapping
  public ResponseEntity<PunishmentResponseDto> createPunishment(
      @Valid PunishmentRequestDto punishment) {
    PunishmentResponseDto responseDto = punishmentService.createPunishment(punishment);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(responseDto.getId())
            .toUri();
    return ResponseEntity.created(location).body(responseDto);
  }
}
