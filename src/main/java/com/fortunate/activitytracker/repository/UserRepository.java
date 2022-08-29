package com.fortunate.activitytracker.repository;

import com.fortunate.activitytracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
