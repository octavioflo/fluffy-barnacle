package com.stakely.fluffybarnacle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Habit {

  @Id @GeneratedValue private UUID id;

  private String name;
  private String description;
  private LocalDate dateCreated;

  @ManyToOne private Punishment punishment;

  @OneToMany(mappedBy = "habit")
  private List<HabitCompletion> completions = new ArrayList<>();

  public Habit() {}
}
