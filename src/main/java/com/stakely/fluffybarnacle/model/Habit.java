package com.stakely.fluffybarnacle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habit {

  @Id @GeneratedValue private UUID id;

  private String name;
  private String description;
  private LocalDate dateCreated;

  @ManyToOne private Punishment punishment;

  @OneToMany(mappedBy = "habit")
  private List<HabitCompletion> completions;

  public Habit(
      String name,
      String description,
      LocalDate dateCreated,
      Punishment punishment,
      List<HabitCompletion> completions) {
    this.name = name;
    this.description = description;
    this.dateCreated = dateCreated;
    this.punishment = punishment;
    this.completions = completions;
  }
}
