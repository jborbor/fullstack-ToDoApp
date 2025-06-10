package com.sofka.todo_app_backend.repository;

import com.sofka.todo_app_backend.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
