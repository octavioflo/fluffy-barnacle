package com.stakely.fluffybarnacle.controller.habit;

import com.stakely.fluffybarnacle.controller.HabitController;
import com.stakely.fluffybarnacle.dto.habit.HabitCompletionResponseDto;
import com.stakely.fluffybarnacle.dto.habit.HabitRequestDto;
import com.stakely.fluffybarnacle.dto.habit.HabitResponseDto;
import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.service.HabitCompletionService;
import com.stakely.fluffybarnacle.service.HabitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HabitControllerTest {
  private HabitController habitController;
  @Mock private HabitService habitService;
  @Mock private HabitCompletionService habitCompletionService;

  @BeforeEach
  void setUp() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    habitController = new HabitController(habitService, habitCompletionService);
  }

  @Test
  void testGetHabits() {
    when(habitService.getHabits()).thenReturn(List.of());
    List<HabitResponseDto> habits = habitController.getHabits();
    assertTrue(habits.isEmpty());
  }

  @Test
  void testGetHabitById() {
    HabitResponseDto responseDto = new HabitResponseDto();
    UUID id = UUID.randomUUID();
    when(habitService.getHabitById(id)).thenReturn(responseDto);
    HabitResponseDto habit = habitController.getHabitById(id);
    assertEquals(habit, responseDto);
  }

  @Test
  void testCreateHabit() {
    HabitRequestDto habitRequestDto = new HabitRequestDto();
    HabitResponseDto responseDto = new HabitResponseDto();
    UUID id = UUID.randomUUID();
    responseDto.setId(id);
    when(habitService.createHabit(habitRequestDto)).thenReturn(responseDto);
    ResponseEntity<HabitResponseDto> response = habitController.createHabit(habitRequestDto);
    assertEquals(response.getBody(), responseDto);
    assertTrue(response.getHeaders().getLocation().toString().endsWith("/" + id));
  }

  @Test
  void testGetCompletions() {
    UUID id = UUID.randomUUID();
    when(habitCompletionService.findByHabitId(id))
        .thenReturn(List.of(new HabitCompletionResponseDto(LocalDate.now())));
    List<HabitCompletionResponseDto> completions = habitController.getCompletions(id);
    assertEquals(1, completions.size());
  }

  @Test
  void testMarkCompletion() {
    UUID id = UUID.randomUUID();
    LocalDate date = LocalDate.of(2023, 1, 1);
    HabitCompletion saved = new HabitCompletion();
    UUID completionId = UUID.randomUUID();
    saved.setId(completionId);
    saved.setDateCompleted(date);
    when(habitCompletionService.markHabitCompleted(id, date)).thenReturn(saved);

    HabitCompletionResponseDto request = new HabitCompletionResponseDto(date);
    ResponseEntity<List<HabitCompletionResponseDto>> response =
        habitController.markCompletion(id, request);
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    assertTrue(response.getHeaders().getLocation().toString().endsWith("/" + completionId));
  }
}
