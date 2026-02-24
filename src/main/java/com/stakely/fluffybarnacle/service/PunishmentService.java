package com.stakely.fluffybarnacle.service;

import com.stakely.fluffybarnacle.dto.punishment.PunishmentRequestDto;
import com.stakely.fluffybarnacle.dto.punishment.PunishmentResponseDto;
import com.stakely.fluffybarnacle.model.Punishment;
import com.stakely.fluffybarnacle.repository.PunishmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PunishmentService {

  PunishmentRepository punishmentRepository;

  public PunishmentService(PunishmentRepository punishmentRepository) {
    this.punishmentRepository = punishmentRepository;
  }

  public List<PunishmentResponseDto> getPunishments() {
    List<Punishment> punishments = punishmentRepository.findAll();
    return punishments.stream()
        .map(
            punishment ->
                new PunishmentResponseDto(
                    punishment.getId(), punishment.getType(), punishment.getDetails()))
        .toList();
  }

  public PunishmentResponseDto createPunishment(PunishmentRequestDto punishment) {
    Punishment newPunishment = new Punishment(punishment.type(), punishment.details());
    punishmentRepository.save(newPunishment);
    return new PunishmentResponseDto(
        newPunishment.getId(), newPunishment.getType(), newPunishment.getDetails());
  }

  public Punishment getPunishmentById(UUID id) {
    return punishmentRepository.findById(id).orElse(null);
  }

  public void deleteAllPunishments() {
    punishmentRepository.deleteAll();
  }
}
