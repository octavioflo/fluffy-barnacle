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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
    habitRequestDto =
        new HabitRequestDto(
            "Test Habit", "Test Habit", new PunishmentRequestDto("ptype", "pdetails"));
    habitResponseDto =
        new HabitResponseDto(
            habitUuid, "Test Habit", "Test Habit", LocalDate.now(), new Punishment(), List.of());
    // cannot call setters on record, but create new instances with required values
    habitResponseDto =
        new HabitResponseDto(
            habitResponseDto.id(),
            habitResponseDto.name(),
            habitResponseDto.description(),
            habitResponseDto.dateCreated(),
            new Punishment(),
            List.of(habitCompletion));

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
    given(habitController.getHabitById(habitResponseDto.id())).willReturn(habitResponseDto);

    mockMvc
        .perform(get("/api/v1/habits/" + habitResponseDto.id()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Test Habit"))
        .andExpect(jsonPath("$.description").value("Test Habit"));

    verify(habitController).getHabitById(habitResponseDto.id());
  }

  @Test
  void testCreateHabit() throws Exception {
    given(habitController.createHabit(any(HabitRequestDto.class)))
        .willReturn(
            ResponseEntity.created(URI.create("/api/v1/habits/" + habitResponseDto.id()))
                .body(habitResponseDto));

    ObjectMapper mapper = new ObjectMapper();
    // register JavaTimeModule so LocalDate serializes properly in tests
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    String habitAsJson = mapper.writeValueAsString(habitRequestDto);

    mockMvc
        .perform(
            post("/api/v1/habits").contentType(MediaType.APPLICATION_JSON).content(habitAsJson))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/v1/habits/" + habitResponseDto.id()))
        .andExpect(jsonPath("$.name").value("Test Habit"))
        .andExpect(jsonPath("$.description").value("Test Habit"));

    verify(habitController).createHabit(any(HabitRequestDto.class));
  }

  @Test
  void testGetCompletions() throws Exception {
    given(habitController.getCompletions(habitResponseDto.id()))
        .willReturn(List.of(habitCompletionResponseDto));

    mockMvc
        .perform(get("/api/v1/habits/" + habitResponseDto.id() + "/completions"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$[0].dateCompleted")
                .value(habitCompletionResponseDto.dateCompleted().toString()));

    verify(habitController).getCompletions(habitResponseDto.id());
  }

  @Test
  void testMarkCompletion() throws Exception {
    given(
            habitController.markCompletion(
                habitResponseDto.id(),
                new HabitCompletionResponseDto(habitCompletionResponseDto.dateCompleted())))
        .willReturn(
            ResponseEntity.created(
                    URI.create("/api/v1/habits/" + habitResponseDto.id() + "/completions"))
                .body(
                    List.of(
                        new HabitCompletionResponseDto(
                            habitCompletionResponseDto.dateCompleted()))));

    ObjectMapper mapper = new ObjectMapper();
    // register JavaTimeModule so LocalDate serializes properly in tests
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    String completionRequestAsJson =
        mapper.writeValueAsString(
            new HabitCompletionResponseDto(habitCompletionResponseDto.dateCompleted()));

    mockMvc
        .perform(
            post("/api/v1/habits/" + habitResponseDto.id() + "/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(completionRequestAsJson))
        .andExpect(status().isCreated())
        .andExpect(
            header().string("Location", "/api/v1/habits/" + habitResponseDto.id() + "/completions"))
        .andExpect(
            jsonPath("$[0].dateCompleted")
                .value(habitCompletionResponseDto.dateCompleted().toString()));

    verify(habitController)
        .markCompletion(
            habitResponseDto.id(),
            new HabitCompletionResponseDto(habitCompletionResponseDto.dateCompleted()));
  }
}
