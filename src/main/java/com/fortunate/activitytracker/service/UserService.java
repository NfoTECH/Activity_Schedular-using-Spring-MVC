package com.fortunate.activitytracker.service;

import com.fortunate.activitytracker.dto.TaskDTO;
import com.fortunate.activitytracker.dto.UserDTO;
import com.fortunate.activitytracker.model.Task;
import com.fortunate.activitytracker.model.User;

import java.util.List;

public interface UserService {
    User register(UserDTO userDTO);
    User getUserByEmail(String email);
    String loginUser(String email, String password);
    Task createTask(TaskDTO taskDTO);
    Task updateTitleAndDescription(TaskDTO taskDTO, int id);
    boolean updateTaskStatus(String status, int id);
    List<Task> viewAllTasks();
    Task getTaskById(int id);
    List<Task> viewAllTasksByStatus(String status);
    boolean deleteById(int id);

    //List<Task> viewUserTask(User user);
}
