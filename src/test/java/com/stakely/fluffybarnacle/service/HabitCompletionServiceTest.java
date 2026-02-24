package com.stakely.fluffybarnacle.service;

import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.repository.HabitCompletionRepository;
import com.stakely.fluffybarnacle.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitCompletionServiceTest {
  @Mock private HabitCompletionRepository habitCompletionRepository;
  @Mock private HabitRepository habitRepository;

  private HabitCompletionService service;

  @BeforeEach
  void setUp() {
    service = new HabitCompletionService(habitCompletionRepository, habitRepository);
  }

  @Test
  void testFindByHabitId() {
    UUID id = UUID.randomUUID();
    HabitCompletion completion = new HabitCompletion();
    completion.setDateCompleted(LocalDate.of(2023, 1, 1));
    when(habitCompletionRepository.findByHabitId(id)).thenReturn(List.of(completion));

    var dtos = service.findByHabitId(id);
    assertEquals(1, dtos.size());
    assertEquals(completion.getDateCompleted(), dtos.get(0).dateCompleted());
  }

  @Test
  void testMarkHabitCompleted_whenExists() {
    UUID id = UUID.randomUUID();
    LocalDate date = LocalDate.of(2023, 1, 2);
    HabitCompletion existing = new HabitCompletion();
    existing.setDateCompleted(date);
    when(habitCompletionRepository.findByHabitIdAndDateCompleted(id, date))
        .thenReturn(Optional.of(existing));

    HabitCompletion result = service.markHabitCompleted(id, date);
    assertSame(existing, result);
    verify(habitCompletionRepository, never()).save(any());
  }

  @Test
  void testMarkHabitCompleted_whenNotExists_creates() {
    UUID id = UUID.randomUUID();
    LocalDate date = LocalDate.of(2023, 1, 3);
    when(habitCompletionRepository.findByHabitIdAndDateCompleted(id, date))
        .thenReturn(Optional.empty());

    Habit habit = new Habit();
    habit.setId(id);
    when(habitRepository.findById(id)).thenReturn(Optional.of(habit));

    HabitCompletion toSave = new HabitCompletion();
    toSave.setDateCompleted(date);
    toSave.setId(UUID.randomUUID());
    when(habitCompletionRepository.save(any())).thenReturn(toSave);

    HabitCompletion result = service.markHabitCompleted(id, date);
    assertEquals(date, result.getDateCompleted());
    verify(habitCompletionRepository).save(any(HabitCompletion.class));

    ArgumentCaptor<HabitCompletion> captor = ArgumentCaptor.forClass(HabitCompletion.class);
    verify(habitCompletionRepository).save(captor.capture());
    HabitCompletion savedArg = captor.getValue();
    assertEquals(date, savedArg.getDateCompleted());
    assertNotNull(savedArg.getHabit());
  }
}
