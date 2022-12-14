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
    boolean deleteById(int id);

    String moveForward(int id);

    String moveBackward(int id);

    User getUserById(int id);
    List<Task> showTaskByUser(int id);

    List<Task> findAllByUser_idAndStatus(int user_id , String status);
}
