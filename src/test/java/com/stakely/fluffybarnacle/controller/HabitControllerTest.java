package com.stakely.fluffybarnacle.controller;

import com.stakely.fluffybarnacle.dto.HabitCompletionRequestDto;
import com.stakely.fluffybarnacle.model.Habit;
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
public class HabitControllerTest {

  @Autowired private MockMvc mockMvc;

  private Habit habit;
  private HabitCompletion habitCompletion;

  @MockitoBean private HabitController habitController;
  @MockitoBean private HabitCompletionService habitCompletionService;

  @BeforeEach
  void setup() {
    habit = new Habit();
    habitCompletion = new HabitCompletion();

    UUID habitUuid = UUID.randomUUID();
    habit.setId(habitUuid);
    habit.setName("Test Habit");
    habit.setDescription("Test Habit");
    habit.setDateCreated(LocalDate.now());

    UUID habitCompletionUuid = UUID.randomUUID();
    habitCompletion.setId(habitCompletionUuid);
    habitCompletion.setHabit(habit);
    habitCompletion.setDateCompleted(LocalDate.now());

    habit.setCompletions(List.of(habitCompletion));
    habit.setPunishment(new Punishment());
  }

  @Test
  void testGetHabits() throws Exception {
    given(habitController.getHabits()).willReturn(List.of(habit));

    mockMvc
        .perform(get("/api/v1/habits"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Test Habit"))
        .andExpect(jsonPath("$[0].description").value("Test Habit"));

    verify(habitController).getHabits();
  }

  @Test
  void testGetHabitById() throws Exception {
    given(habitController.getHabitById(habit.getId())).willReturn(habit);

    mockMvc
        .perform(get("/api/v1/habits/" + habit.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Test Habit"))
        .andExpect(jsonPath("$.description").value("Test Habit"));

    verify(habitController).getHabitById(habit.getId());
  }

  @Test
  void testCreateHabit() throws Exception {
    given(habitController.createHabit(any(Habit.class)))
        .willReturn(
            ResponseEntity.created(URI.create("/api/v1/habits/" + habit.getId())).body(habit));

    ObjectMapper mapper = new ObjectMapper();
    String habitAsJson = mapper.writeValueAsString(habit);

    mockMvc
        .perform(
            post("/api/v1/habits").contentType(MediaType.APPLICATION_JSON).content(habitAsJson))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/v1/habits/" + habit.getId()))
        .andExpect(jsonPath("$.name").value("Test Habit"))
        .andExpect(jsonPath("$.description").value("Test Habit"));

    verify(habitController).createHabit(habit);
  }

  @Test
  void testGetCompletions() throws Exception {
    given(habitController.getCompletions(habit.getId())).willReturn(List.of(habitCompletion));

    mockMvc
        .perform(get("/api/v1/habits/" + habit.getId() + "/completions"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$[0].dateCompleted").value(habitCompletion.getDateCompleted().toString()));

    verify(habitController).getCompletions(habit.getId());
  }

  @Test
  void testMarkCompletion() throws Exception {
    given(
            habitController.markCompletion(
                habit.getId(), new HabitCompletionRequestDto(habitCompletion.getDateCompleted())))
        .willReturn(
            ResponseEntity.created(URI.create("/api/v1/habits/" + habit.getId() + "/completions"))
                .body(List.of(new HabitCompletionRequestDto(habitCompletion.getDateCompleted()))));

    ObjectMapper mapper = new ObjectMapper();
    String completionRequestAsJson =
        mapper.writeValueAsString(
            new HabitCompletionRequestDto(habitCompletion.getDateCompleted()));

    mockMvc
        .perform(
            post("/api/v1/habits/" + habit.getId() + "/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(completionRequestAsJson))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/v1/habits/" + habit.getId() + "/completions"))
        .andExpect(
            jsonPath("$[0].dateCompleted").value(habitCompletion.getDateCompleted().toString()));

    verify(habitController)
        .markCompletion(
            habit.getId(),
            new com.stakely.fluffybarnacle.dto.HabitCompletionRequestDto(
                habitCompletion.getDateCompleted()));
  }
}
