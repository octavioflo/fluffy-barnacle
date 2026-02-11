package com.stakely.fluffybarnacle.controller.habit;

import com.stakely.fluffybarnacle.controller.HabitController;
import com.stakely.fluffybarnacle.dto.habit.HabitCompletionResponseDto;
import com.stakely.fluffybarnacle.dto.habit.HabitRequestDto;
import com.stakely.fluffybarnacle.dto.habit.HabitResponseDto;
import com.stakely.fluffybarnacle.dto.punishment.PunishmentRequestDto;
import com.stakely.fluffybarnacle.model.HabitCompletion;
import com.stakely.fluffybarnacle.model.Punishment;
import com.stakely.fluffybarnacle.service.HabitCompletionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HabitController.class)
public class HabitControllerWebMVCTest {

  @Autowired private MockMvc mockMvc;

  private HabitResponseDto habitResponseDto;
  private HabitRequestDto habitRequestDto;
  private HabitCompletion habitCompletion;
  private HabitCompletionResponseDto habitCompletionResponseDto;
  private UUID habitUuid;

  @MockitoBean private HabitController habitController;
  @MockitoBean private HabitCompletionService habitCompletionService;

  @BeforeEach
  void setup() {
    habitCompletion = new HabitCompletion();
    habitUuid = UUID.randomUUID();
    habitRequestDto = new HabitRequestDto("Test Habit", "Test Habit", new PunishmentRequestDto());
    habitResponseDto =
        new HabitResponseDto(
            habitUuid, "Test Habit", "Test Habit", LocalDate.now(), new Punishment(), List.of());
    habitResponseDto.setCompletions(List.of(habitCompletion));
    habitResponseDto.setPunishment(new Punishment());

    habitCompletionResponseDto = new HabitCompletionResponseDto(LocalDate.now());
  }

  @Test
  void testGetHabits() throws Exception {
    given(habitController.getHabits()).willReturn(List.of(habitResponseDto));

    mockMvc
        .perform(get("/api/v1/habits"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Test Habit"))
        .andExpect(jsonPath("$[0].description").value("Test Habit"));

    verify(habitController).getHabits();
  }

  @Test
  void testGetHabitById() throws Exception {
    given(habitController.getHabitById(habitResponseDto.getId())).willReturn(habitResponseDto);

    mockMvc
        .perform(get("/api/v1/habits/" + habitResponseDto.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Test Habit"))
        .andExpect(jsonPath("$.description").value("Test Habit"));

    verify(habitController).getHabitById(habitResponseDto.getId());
  }

  @Test
  void testCreateHabit() throws Exception {
    given(habitController.createHabit(any(HabitRequestDto.class)))
        .willReturn(
            ResponseEntity.created(URI.create("/api/v1/habits/" + habitResponseDto.getId()))
                .body(habitResponseDto));

    ObjectMapper mapper = new ObjectMapper();
    String habitAsJson = mapper.writeValueAsString(habitResponseDto);

    mockMvc
        .perform(
            post("/api/v1/habits").contentType(MediaType.APPLICATION_JSON).content(habitAsJson))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/v1/habits/" + habitResponseDto.getId()))
        .andExpect(jsonPath("$.name").value("Test Habit"))
        .andExpect(jsonPath("$.description").value("Test Habit"));

    verify(habitController).createHabit(habitRequestDto);
  }

  @Test
  void testGetCompletions() throws Exception {
    given(habitController.getCompletions(habitResponseDto.getId()))
        .willReturn(List.of(habitCompletionResponseDto));

    mockMvc
        .perform(get("/api/v1/habits/" + habitResponseDto.getId() + "/completions"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$[0].dateCompleted")
                .value(habitCompletionResponseDto.getDateCompleted().toString()));

    verify(habitController).getCompletions(habitResponseDto.getId());
  }

  @Test
  void testMarkCompletion() throws Exception {
    given(
            habitController.markCompletion(
                habitResponseDto.getId(),
                new HabitCompletionResponseDto(habitCompletionResponseDto.getDateCompleted())))
        .willReturn(
            ResponseEntity.created(
                    URI.create("/api/v1/habits/" + habitResponseDto.getId() + "/completions"))
                .body(
                    List.of(
                        new HabitCompletionResponseDto(
                            habitCompletionResponseDto.getDateCompleted()))));

    ObjectMapper mapper = new ObjectMapper();
    String completionRequestAsJson =
        mapper.writeValueAsString(
            new HabitCompletionResponseDto(habitCompletionResponseDto.getDateCompleted()));

    mockMvc
        .perform(
            post("/api/v1/habits/" + habitResponseDto.getId() + "/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(completionRequestAsJson))
        .andExpect(status().isCreated())
        .andExpect(
            header()
                .string("Location", "/api/v1/habits/" + habitResponseDto.getId() + "/completions"))
        .andExpect(
            jsonPath("$[0].dateCompleted")
                .value(habitCompletionResponseDto.getDateCompleted().toString()));

    verify(habitController)
        .markCompletion(
            habitResponseDto.getId(),
            new HabitCompletionResponseDto(habitCompletionResponseDto.getDateCompleted()));
  }
}
