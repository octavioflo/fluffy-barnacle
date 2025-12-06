package com.stakely.fluffybarnacle.repository;

import com.stakely.fluffybarnacle.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {}
