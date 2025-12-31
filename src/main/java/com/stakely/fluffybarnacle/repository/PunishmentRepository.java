package com.stakely.fluffybarnacle.repository;

import com.stakely.fluffybarnacle.model.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PunishmentRepository extends JpaRepository<Punishment, UUID> {}
