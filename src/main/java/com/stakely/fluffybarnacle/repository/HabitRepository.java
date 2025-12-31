package com.stakely.fluffybarnacle.repository;

import com.stakely.fluffybarnacle.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HabitRepository extends JpaRepository<Habit, UUID> {}
