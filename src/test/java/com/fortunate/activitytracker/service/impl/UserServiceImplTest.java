package com.fortunate.activitytracker.service.impl;

import com.fortunate.activitytracker.dto.UserDTO;
import com.fortunate.activitytracker.model.Task;
import com.fortunate.activitytracker.model.User;
import com.fortunate.activitytracker.repository.TaskRepository;
import com.fortunate.activitytracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        LocalDateTime time = LocalDateTime.of(2020, Month.AUGUST, 1, 5, 30, 40, 3000);
        List<Task> taskList = new ArrayList<>();
        user = new User(1, "Fortunate", "fortunenwachukwu@gmail.com", "password", taskList);
        Task task = new Task(1, "Task 1", "Learn Spring Boot", "Pending", time, time, time, user);

        userDTO = new UserDTO("Fortunate", "fortunenwachukwu@gmail.com", "password");
        when(userRepository.save(user)).thenReturn(user);
        when(taskRepository.save(task)).thenReturn(task);
        when(userRepository.findUserByEmail("fortunenwachukwu@gmail.com")).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        when(taskRepository.findAll()).thenReturn(taskList);
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
    }

    @Test
    void registerUser() {
        when(userServiceImpl.register(userDTO)).thenReturn(user);
        var actual = userServiceImpl.register(userDTO);
        var expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void loginUserSuccess() {
        String message = "success";
        assertEquals(message, userServiceImpl.loginUser("fortunenwachukwu@gmail.com", "password"));
    }

    @Test
    void loginUserUnsuccessful() {
        String message = "Invalid password";
        assertEquals(message, userServiceImpl.loginUser("fortunenwachukwu@gmail.com", "12345"));
    }

    @Test
    void createTask() {
    }

    @Test
    void updateTitleAndDescription() {
    }

    @Test
    void updateTaskStatus() {
    }

    @Test
    void viewAllTasks() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void viewAllTasksByStatus() {
    }

    @Test
    void deleteById() {
    }
}