package com.sofka.todo_app_backend.service;

import com.sofka.todo_app_backend.dto.request.TaskRequestDTO;
import com.sofka.todo_app_backend.dto.response.TaskResponseDTO;

import java.util.List;

public interface TaskService {
    List<TaskResponseDTO> getAllTasks();
    TaskResponseDTO getTaskById(Long id);
    TaskResponseDTO saveTask(TaskRequestDTO taskRequestDTO);
    TaskResponseDTO deleteTaskById(Long id);
    TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO);
}
