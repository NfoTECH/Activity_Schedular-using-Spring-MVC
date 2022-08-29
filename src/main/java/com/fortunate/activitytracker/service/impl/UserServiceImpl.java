package com.fortunate.activitytracker.service.impl;

import com.fortunate.activitytracker.dto.TaskDTO;
import com.fortunate.activitytracker.dto.UserDTO;
import com.fortunate.activitytracker.exception.UserNotFoundException;
import com.fortunate.activitytracker.model.Task;
import com.fortunate.activitytracker.model.User;
import com.fortunate.activitytracker.repository.TaskRepository;
import com.fortunate.activitytracker.repository.UserRepository;
import com.fortunate.activitytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public User register(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("This user was not found"));
    }

    @Override
    public String loginUser(String email, String password) {
        User user = getUserByEmail(email);
        return (user.getPassword().equals(password)) ? "success" : "Invalid password";
    }

    @Override
    public Task createTask(TaskDTO taskDTO) {
        Task task =  new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        return taskRepository.save(task);
    }

    @Override
    public Task updateTitleAndDescription(TaskDTO taskDTO, int id) {
        Task task = getTaskById(id);
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        return taskRepository.save(task);
    }

    @Override
    public boolean updateTaskStatus(String status, int id) {
        return taskRepository.updateTaskByIdAndStatus(status, id);
    }

    @Override
    public List<Task> viewAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This task was not found"));
    }

    @Override
    public List<Task> viewAllTasksByStatus(String status) {
        return taskRepository.listOfTasksByStatus(status);
    }

    @Override
    public boolean deleteById(int id) {
        taskRepository.deleteById(id);
        return true;
    }
}
