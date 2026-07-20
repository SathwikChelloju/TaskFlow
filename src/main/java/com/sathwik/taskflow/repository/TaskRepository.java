package com.sathwik.taskflow.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sathwik.taskflow.entity.Task;
import com.sathwik.taskflow.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(String status);

    List<Task> findByDueDateBefore(LocalDate date);

    List<Task> findByUser(User user);

    List<Task> findByUserAndStatus(User user, String status);

    List<Task> findByUserAndDueDateBefore(User user, LocalDate date);

}