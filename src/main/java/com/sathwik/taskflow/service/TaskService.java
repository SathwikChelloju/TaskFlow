package com.sathwik.taskflow.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sathwik.taskflow.entity.Task;
import com.sathwik.taskflow.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task saveTask(Task task) {
        return repository.save(task);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public Task getTask(Long id) {
        return repository.findById(id).orElse(null);
    }
    public List<Task> getTasksByStatus(String status){

        return repository.findByStatus(status);

    }
    public List<Task> getOverdueTasks() {

        return repository.findByDueDateBefore(LocalDate.now());

    }
}