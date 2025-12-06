package com.stakely.fluffybarnacle.controller;

import com.stakely.fluffybarnacle.model.Punishment;
import com.stakely.fluffybarnacle.service.PunishmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/punishments")
public class PunishmentController {

  PunishmentService punishmentService;

  public PunishmentController(PunishmentService punishmentService) {
    this.punishmentService = punishmentService;
  }

  @GetMapping
  public List<Punishment> getPunishments() {
    return punishmentService.getPunishments();
  }

  @PostMapping
  public Punishment createPunishment(Punishment punishment) {
    return punishmentService.createPunishment(punishment);
  }
}
