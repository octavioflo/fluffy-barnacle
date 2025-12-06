package com.stakely.fluffybarnacle.repository;

import com.stakely.fluffybarnacle.model.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PunishmentRepository extends JpaRepository<Punishment, Long> {}
