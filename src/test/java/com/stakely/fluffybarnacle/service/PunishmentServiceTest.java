package com.stakely.fluffybarnacle.service;

import com.stakely.fluffybarnacle.dto.punishment.PunishmentRequestDto;
import com.stakely.fluffybarnacle.dto.punishment.PunishmentResponseDto;
import com.stakely.fluffybarnacle.model.Punishment;
import com.stakely.fluffybarnacle.repository.HabitRepository;
import com.stakely.fluffybarnacle.repository.PunishmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PunishmentServiceTest {
  private PunishmentService punishmentService;
  @Mock private PunishmentRepository punishmentRepository;

  @BeforeEach
  public void setUp() {
    punishmentService = new PunishmentService(punishmentRepository);
  }

  @Test
  public void testGetPunishments_empty() {
    when(punishmentRepository.findAll()).thenReturn(List.of());
    List<PunishmentResponseDto> punishments = punishmentService.getPunishments();
    assertTrue(punishments.isEmpty());
  }

  @Test
  public void testGetPunishments_multiplePunishments() {
    List<Punishment> list = List.of(new Punishment(), new Punishment());
    when(punishmentRepository.findAll()).thenReturn(list);
    List<PunishmentResponseDto> punishments = punishmentService.getPunishments();
    assertEquals(2, punishments.size());
  }

  @Test
  public void testCreatePunishment() {
    Punishment punishment = new Punishment();
    punishment.setType("Test Type");
    punishment.setDetails("Test Details");

    PunishmentRequestDto requestDto = new PunishmentRequestDto("Test Type", "Test Details");
    when(punishmentRepository.save(punishment)).thenReturn(punishment);

    PunishmentResponseDto responseDto = punishmentService.createPunishment(requestDto);

    assertEquals(punishment.getType(), responseDto.getType());
    assertEquals(punishment.getDetails(), responseDto.getDetails());
  }

  @Test
  public void testGetPunishmentById_notFound_returnsNull() {
    java.util.UUID id = java.util.UUID.randomUUID();
    when(punishmentRepository.findById(id)).thenReturn(java.util.Optional.empty());
    Punishment punishment = punishmentService.getPunishmentById(id);
    assertTrue(punishment == null);
  }

  @Test
  public void testGetPunishmentById_found() {
    java.util.UUID id = java.util.UUID.randomUUID();
    Punishment punishment = new Punishment();
    punishment.setId(id);
    when(punishmentRepository.findById(id)).thenReturn(java.util.Optional.of(punishment));
    Punishment result = punishmentService.getPunishmentById(id);
    assertEquals(punishment, result);
  }

  @Test
  public void testDeleteAllPunishments() {
    punishmentService.deleteAllPunishments();
    // Verify that the deleteAll method was called on the repository
    verify(punishmentRepository).deleteAll();
  }
}
