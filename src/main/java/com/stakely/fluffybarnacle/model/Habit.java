package com.stakely.fluffybarnacle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Habit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  private String name;
  private String description;
  private LocalDate dateCreated;

  @ManyToOne private Punishment punishment;

  @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL)
  private List<HabitCompletion> completions = new ArrayList<>();

  public Habit() {}
}
