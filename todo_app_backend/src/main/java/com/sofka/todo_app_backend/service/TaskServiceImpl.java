package com.sofka.todo_app_backend.service;

import com.sofka.todo_app_backend.domain.entity.Task;
import com.sofka.todo_app_backend.domain.enums.TaskStatus;
import com.sofka.todo_app_backend.dto.request.TaskRequestDTO;
import com.sofka.todo_app_backend.dto.response.TaskResponseDTO;
import com.sofka.todo_app_backend.exception.TaskInvalidStatusException;
import com.sofka.todo_app_backend.exception.TaskNotFoundException;
import com.sofka.todo_app_backend.mapper.TaskMapper;
import com.sofka.todo_app_backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.toResponseDtoList(tasks);
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.toResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO saveTask(TaskRequestDTO taskRequestDTO) {
        Task task = taskMapper.toResponseEntity(taskRequestDTO);
        taskRepository.save(task);
        return taskMapper.toResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO deleteTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.deleteById(id);
        return taskMapper.toResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        // Actualizar campos si no son nulos
        if (taskRequestDTO.getTitle() != null) {
            task.setTitle(taskRequestDTO.getTitle());
        }

        if (taskRequestDTO.getDescription() != null) {
            task.setDescription(taskRequestDTO.getDescription());
        }

        // Validar transición de estado solo si viene un nuevo status
        if (taskRequestDTO.getStatus() != null && taskRequestDTO.getStatus() != task.getStatus()) {
            TaskStatus current = task.getStatus();
            TaskStatus newStatus = taskRequestDTO.getStatus();

            if (current == TaskStatus.PENDING && newStatus != TaskStatus.IN_PROGRESS) {
                throw new TaskInvalidStatusException("Solo puede pasar de PENDING a IN_PROGRESS");
            }
            if (current == TaskStatus.IN_PROGRESS && newStatus != TaskStatus.DONE) {
                throw new TaskInvalidStatusException("Solo puede pasar de IN_PROGRESS a DONE");
            }
            if (current == TaskStatus.DONE) {
                throw new TaskInvalidStatusException("No se puede cambiar el estado desde DONE");
            }

            task.setStatus(newStatus);
        }

        taskRepository.save(task);
        return taskMapper.toResponseDTO(task);
    }

}
