package com.stakely.fluffybarnacle.service;

import com.stakely.fluffybarnacle.dto.habit.HabitRequestDto;
import com.stakely.fluffybarnacle.dto.habit.HabitResponseDto;
import com.stakely.fluffybarnacle.dto.punishment.PunishmentRequestDto;
import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.model.Punishment;
import com.stakely.fluffybarnacle.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitServiceTest {
  private HabitService habitService;

  @Mock private HabitRepository habitRepository;

  @BeforeEach
  public void setUp() {
    habitService = new HabitService(habitRepository);
  }

  @Test
  public void testGetHabits_empty() {
    when(habitRepository.findAll()).thenReturn(List.of());
    List<HabitResponseDto> habits = habitService.getHabits();
    assertTrue(habits.isEmpty());
  }

  @Test
  public void testGetHabits_multipleHabits() {
    List<Habit> list = List.of(new Habit(), new Habit());
    when(habitRepository.findAll()).thenReturn(list);
    List<HabitResponseDto> habits = habitService.getHabits();
    assertEquals(2, habits.size());
  }

  @Test
  public void testGetHabitById_notFound_returnsNull() {
    UUID id = UUID.randomUUID();
    when(habitRepository.findById(id)).thenReturn(Optional.empty());
    HabitResponseDto habit = habitService.getHabitById(id);
    assertNull(habit);
  }

  @Test
  public void testGetHabitById_found() {
    UUID id = UUID.randomUUID();
    Habit h = new Habit();
    h.setId(id);
    h.setName("name");
    h.setDescription("desc");
    h.setPunishment(new Punishment("type", "details"));
    when(habitRepository.findById(id)).thenReturn(Optional.of(h));

    HabitResponseDto habit = habitService.getHabitById(id);
    assertNotNull(habit);
    assertEquals(id, habit.getId());
    assertEquals("name", habit.getName());
  }

  @Test
  public void testCreateHabit() {
    HabitRequestDto request = new HabitRequestDto();
    request.setName("newname");
    request.setDescription("newdesc");
    PunishmentRequestDto p = new PunishmentRequestDto();
    p.setType("ptype");
    p.setDetails("pdetails");
    request.setPunishment(p);

    Habit saved = new Habit();
    UUID id = UUID.randomUUID();
    saved.setId(id);
    saved.setName(request.getName());
    saved.setDescription(request.getDescription());
    saved.setPunishment(new Punishment(p.getType(), p.getDetails()));

    when(habitRepository.save(any(Habit.class))).thenReturn(saved);

    HabitResponseDto response = habitService.createHabit(request);
    assertNotNull(response);
    assertEquals(id, response.getId());

    ArgumentCaptor<Habit> captor = ArgumentCaptor.forClass(Habit.class);
    verify(habitRepository).save(captor.capture());
    Habit toSave = captor.getValue();
    assertEquals(request.getName(), toSave.getName());
    assertEquals(request.getDescription(), toSave.getDescription());
    assertNotNull(toSave.getPunishment());
  }
}
