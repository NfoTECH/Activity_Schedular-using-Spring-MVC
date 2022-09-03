package com.fortunate.activitytracker.controller;

import com.fortunate.activitytracker.dto.TaskDTO;
import com.fortunate.activitytracker.dto.UserDTO;
import com.fortunate.activitytracker.model.Task;
import com.fortunate.activitytracker.model.User;
import com.fortunate.activitytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    private final UserService service;
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String landingPage(Model model, HttpSession session) {
        model.addAttribute("userDetails", new UserDTO());
        session.setAttribute("userDetails", new UserDTO());
        return "index";
    }

    @GetMapping("/dashboard")
    public String index(Model model, HttpSession session) {
        if(session.getAttribute("id") == null){
            return "redirect:/login";
        }else {
            List<Task> allTasks = service.showTaskByUser((Integer) session.getAttribute("id"));
            model.addAttribute("listTasks", allTasks);

            session.setAttribute("listTasks", allTasks);
            return "dashboard";
        }
    }

    @GetMapping(value = "/register")
    public String showRegisterForm(Model model, HttpSession session) {
        model.addAttribute("userRegistration", new UserDTO());
        session.setAttribute("userRegistration", new UserDTO());
        return "register";
    }

    @GetMapping(value = "/signUpSuccess")
    public String showSignUpSuccess(){
        return "signUpSuccess";
    }


    @PostMapping(value = "/userRegistration")
    public String registerUser(@ModelAttribute("userRegistration") UserDTO userDTO) {
      User registeredUser = service.register(userDTO);
      if (registeredUser != null) {
          return "redirect:/signUpSuccess";
        } else {
          return "redirect:/register";
        }
    }


    @GetMapping(value = "login")
        public String displayLoginPage(Model model, HttpSession session) {
        if (session.getAttribute("id") != null) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("userDetails", new UserDTO());
            session.setAttribute("userDetails", new UserDTO());
            return "login";
        }
    }


    @PostMapping(value = "/loginUser")
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

    @GetMapping("/task/{status}")
    public String taskByStatus(@PathVariable(name = "status") String status) {
        if(status.equals("PENDING")) {
           return "pendingTasks";
        } else if (status.equals("IN_PROGRESS")) {
            return "tasksInProgress";
        } else {
            return "doneTasks";
        }
    }

    @GetMapping("/editPage/{id}")
    public String editPage(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
        Task task = service.getTaskById(id);
        model.addAttribute("singleTask", task);
        session.setAttribute("singleTask", task);
        return "editTask";
    }


    @PostMapping("/edit")
    public String editSingleTask(@ModelAttribute("singleTask") TaskDTO taskDTO, @RequestParam("hide") String id){
        service.updateTitleAndDescription(taskDTO, Integer.parseInt(id));
        return "redirect:/dashboard";
    }

    @GetMapping("/viewTask/{id}")
    public String viewSingleTask(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
        if (session.getAttribute("id") != null) {
            Task task = service.getTaskById(id);
            model.addAttribute("singleTask", task);
            session.setAttribute("singleTask", task);
            return "viewSingleTask";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/addNewTask")
    public String addTask(Model model, HttpSession session) {
        model.addAttribute("newTask", new TaskDTO());
        session.setAttribute("newTask", new TaskDTO());
        return "addNewTask";
    }

    @PostMapping("/addTask")
    public String CreateTask(@ModelAttribute TaskDTO taskDTO) {
        service.createTask(taskDTO);
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/arrow-right/{id}")
    public String moveStatusForward(@PathVariable(name = "id") int id){
        service.moveForward(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/arrow-left/{id}")
    public String moveStatusBackward(@PathVariable(name = "id") int id){
        service.moveBackward(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable(name = "id") Integer id) {
        service.deleteById(id);
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/logout")
    public String logoutUser(HttpSession session) {
        session.removeAttribute("userDetails");
        session.invalidate();
        return "redirect:/";
    }
}
