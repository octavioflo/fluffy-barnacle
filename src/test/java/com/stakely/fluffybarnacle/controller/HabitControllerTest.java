package com.stakely.fluffybarnacle.controller;

import com.stakely.fluffybarnacle.model.Habit;
import com.stakely.fluffybarnacle.model.Punishment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

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

  @MockitoBean private HabitController habitController;

  @BeforeEach
  void setup() {
    habit = new Habit();
    UUID uuid = UUID.randomUUID();
    habit.setId(uuid);
    habit.setName("Test Habit");
    habit.setDescription("Test Habit");
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
            ResponseEntity.created(java.net.URI.create("/api/v1/habits/" + habit.getId()))
                .body(habit));

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
}
