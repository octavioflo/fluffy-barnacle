package com.stakely.fluffybarnacle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Punishment {

  @Id @GeneratedValue private UUID id;

  private String type; // "SHAME_POST", "BAD_CHARITY_DONATION", etc.
  private String details;

  public Punishment() {}
}
