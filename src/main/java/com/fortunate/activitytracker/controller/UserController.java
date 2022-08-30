package com.fortunate.activitytracker.controller;

import com.fortunate.activitytracker.dto.TaskDTO;
import com.fortunate.activitytracker.dto.UserDTO;
import com.fortunate.activitytracker.model.Task;
import com.fortunate.activitytracker.model.User;
import com.fortunate.activitytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService service;
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/dashboard")
    public String index(Model model) {
        List<Task> allTasks = service.viewAllTasks();
        model.addAttribute("listTasks", allTasks);
        return "dashboard";
    }

    @GetMapping(value = "/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegistration", new UserDTO());
        return "register";
    }

    @PostMapping(value = "/userRegistration")
    public String registerUser(@ModelAttribute("userRegistration") UserDTO userDTO) {
      User registeredUser = service.register(userDTO);
      if (registeredUser != null) {
          return "redirect:/login";
        } else {
          return "redirect:/register";
        }
    }

    @GetMapping(value = "login")
        public String displayLoginPage(Model model) {
            model.addAttribute("userDetails", new UserDTO());
            return "login";
        }


    @PostMapping(value = "loginUser")
    public String loginUsers (@RequestParam String email, @RequestParam String password,
                              HttpSession session, Model model) {
        String message = service.loginUser(email, password);
        if(message.equals("success")) {
            User user = service.getUserByEmail(email);
            session.setAttribute("email", email);
            session.setAttribute("id", user.getId());
            session.setAttribute("name", user.getName());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("errorMessage", message);
            return "redirect:/login";
        }
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Task> allTasks = service.viewAllTasks();
        model.addAttribute("listTasks", allTasks);
        return "viewAllTask";
    }

    @GetMapping(value = "/task/{status}")
    public String taskByStatus(@PathVariable(name = "status") String status, Model model) {
        List<Task> allTasksByStatus = service.viewAllTasksByStatus(status);
        model.addAttribute("taskByStatus", allTasksByStatus);
        return "taskByStatus";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable(name = "id") Integer id) {
        service.deleteById(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/editPage/{id}")
    public String editPage(@PathVariable(name = "id") Integer id, Model model) {
        Task task = service.getTaskById(id);
        model.addAttribute("singleTask", task);
        model.addAttribute("taskBody", new TaskDTO());
        return "editTask";
    }

    @PostMapping(value="/edit/{id}")
    public String editTask(@PathVariable(name = "id") Integer id, @ModelAttribute TaskDTO taskDTO) {
        service.updateTitleAndDescription(taskDTO, id);
        return "redirect:/dashboard";
    }

    @GetMapping("/addNewTask")
    public String addTask(Model model) {
        model.addAttribute("newTask", new TaskDTO());
        return "addTask";
    }

    @PostMapping("/addTask")
    public String CreateTask(@ModelAttribute TaskDTO taskDTO) {
        service.createTask(taskDTO);
        return "redirect:/dashboard";
    }
}
