package com.stakely.fluffybarnacle.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Data
public class Habit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private LocalDate dateCreated;

  //  @ManyToOne private Punishment punishment;

  public Habit() {}
}
