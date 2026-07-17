package com.sathwik.taskflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sathwik.taskflow.entity.Task;
import com.sathwik.taskflow.service.TaskService;

@Controller
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String due,
            Model model) {

        List<Task> tasks;

        // Due Date Filter
        if ("Overdue".equals(status)) {

            tasks = service.getOverdueTasks();

        }
        // Status Filter
        else if (status == null || status.equals("All")) {

            tasks = service.getAllTasks();

        }
        else {

            tasks = service.getTasksByStatus(status);

        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());

        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedDue", due);

        return "index";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task) {

        service.saveTask(task);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {

        service.deleteTask(id);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {

        Task task = service.getTask(id);

        model.addAttribute("task", task);

        return "edit";
    }
    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {

        service.saveTask(task);

        return "redirect:/";
    }
    
    
}