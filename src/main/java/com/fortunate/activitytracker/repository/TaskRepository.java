package com.fortunate.activitytracker.repository;

import com.fortunate.activitytracker.model.Task;
import com.fortunate.activitytracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(value = "SELECT * FROM tasks WHERE status = ?1", nativeQuery = true)
    List<Task> listOfTasksByStatus(String status);

    @Query(value = "SELECT * FROM tasks WHERE user_id = ?1", nativeQuery = true)
    List<Task> listOfTasksByUserId(int id);

    @Modifying
    @Query(value = "UPDATE tasks SET status = ?1 WHERE id = ?2", nativeQuery = true)
    boolean updateTaskByIdAndStatus(String status, int id);

    @Query(value = "SELECT * FROM tasks WHERE user_id = ?1 AND status = ?2", nativeQuery = true)
    List<Task> findAllByUser_idAndStatus(int user_id, String status);
}

