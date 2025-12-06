package com.stakely.fluffybarnacle.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Punishment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String type; // "SHAME_POST", "BAD_CHARITY_DONATION", etc.
  private String details;

  public Punishment() {}
}
