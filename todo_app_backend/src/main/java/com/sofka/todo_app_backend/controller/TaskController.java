package com.sofka.todo_app_backend.controller;

import com.sofka.todo_app_backend.dto.request.TaskRequestDTO;
import com.sofka.todo_app_backend.dto.response.TaskResponseDTO;
import com.sofka.todo_app_backend.service.TaskServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin({"http://localhost:5173","http://localhost:4200"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TaskController {

    private final TaskServiceImpl taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO task = taskService.saveTask(taskRequestDTO);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> deleteTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.deleteTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO task = taskService.updateTask(id, taskRequestDTO);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

}
