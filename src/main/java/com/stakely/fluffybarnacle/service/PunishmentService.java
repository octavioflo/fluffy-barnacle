package com.stakely.fluffybarnacle.service;

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

  public List<Punishment> getPunishments() {
    return punishmentRepository.findAll();
  }

  public Punishment createPunishment(Punishment punishment) {
    return punishmentRepository.save(punishment);
  }

  public Punishment getPunishmentById(UUID id) {
    return punishmentRepository.findById(id).orElse(null);
  }

  public void deleteAllPunishments() {
    punishmentRepository.deleteAll();
  }
}
