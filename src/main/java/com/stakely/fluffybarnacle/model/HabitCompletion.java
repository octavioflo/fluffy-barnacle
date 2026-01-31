package com.stakely.fluffybarnacle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class HabitCompletion {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private LocalDate dateCompleted;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Habit habit;
}
