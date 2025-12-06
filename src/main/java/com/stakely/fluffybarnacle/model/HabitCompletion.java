package com.stakely.fluffybarnacle.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class HabitCompletion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate dateCompleted;

  @ManyToOne(optional = false)
  private Habit habit;
}
