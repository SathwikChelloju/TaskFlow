package com.sathwik.taskflow.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sathwik.taskflow.entity.Task;
import com.sathwik.taskflow.entity.User;
import com.sathwik.taskflow.repository.TaskRepository;
import com.sathwik.taskflow.repository.UserRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserRepository userRepository;

    // Returns the currently logged-in user
    private User getLoggedInUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Show only logged-in user's tasks
    public List<Task> getAllTasks() {

        return repository.findByUser(getLoggedInUser());

    }

    // Save task for logged-in user
    public Task saveTask(Task task) {

        User user = getLoggedInUser();

        if (task.getId() != null) {

            Task existing = repository.findById(task.getId())
                    .orElseThrow(() -> new RuntimeException("Task not found"));

            if (!existing.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized");
            }

            task.setUser(existing.getUser());

        } else {

            task.setUser(user);

        }

        return repository.save(task);
    }

    // Delete only own task
    public void deleteTask(Long id) {

        User user = getLoggedInUser();

        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        repository.delete(task);

    }

    // Edit only own task
    public Task getTask(Long id) {

        User user = getLoggedInUser();

        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return task;

    }

    public List<Task> getTasksByStatus(String status) {

        return repository.findByUserAndStatus(getLoggedInUser(), status);

    }

    public List<Task> getOverdueTasks() {

        return repository.findByUserAndDueDateBefore(
                getLoggedInUser(),
                LocalDate.now());

    }

}