package com.fortunate.activitytracker.repository;

import com.fortunate.activitytracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}

