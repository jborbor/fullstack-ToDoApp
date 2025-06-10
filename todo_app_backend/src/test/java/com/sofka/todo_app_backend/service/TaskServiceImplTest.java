package com.sofka.todo_app_backend.service;

import com.sofka.todo_app_backend.domain.entity.Task;
import com.sofka.todo_app_backend.domain.enums.TaskStatus;
import com.sofka.todo_app_backend.dto.request.TaskRequestDTO;
import com.sofka.todo_app_backend.dto.response.TaskResponseDTO;
import com.sofka.todo_app_backend.exception.TaskInvalidStatusException;
import com.sofka.todo_app_backend.exception.TaskNotFoundException;
import com.sofka.todo_app_backend.mapper.TaskMapper;
import com.sofka.todo_app_backend.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_ReturnsMappedList() {
        List<Task> tasks = List.of(new Task(), new Task());
        List<TaskResponseDTO> responseDTOs = List.of(new TaskResponseDTO(), new TaskResponseDTO());

        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.toResponseDtoList(tasks)).thenReturn(responseDTOs);

        List<TaskResponseDTO> result = taskService.getAllTasks();

        assertEquals(responseDTOs, result);
        verify(taskRepository).findAll();
        verify(taskMapper).toResponseDtoList(tasks);
    }

    @Test
    void getTaskById_WhenTaskExists_ReturnsMappedTask() {
        Task task = new Task();
        TaskResponseDTO dto = new TaskResponseDTO();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toResponseDTO(task)).thenReturn(dto);

        TaskResponseDTO result = taskService.getTaskById(1L);

        assertEquals(dto, result);
    }

    @Test
    void getTaskById_WhenTaskDoesNotExist_ThrowsException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void saveTask_SavesAndReturnsDTO() {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        Task task = new Task();
        TaskResponseDTO responseDTO = new TaskResponseDTO();

        when(taskMapper.toResponseEntity(requestDTO)).thenReturn(task);
        when(taskMapper.toResponseDTO(task)).thenReturn(responseDTO);

        TaskResponseDTO result = taskService.saveTask(requestDTO);

        verify(taskRepository).save(task);
        assertEquals(responseDTO, result);
    }

    @Test
    void deleteTaskById_WhenTaskExists_DeletesAndReturnsDTO() {
        Task task = new Task();
        TaskResponseDTO dto = new TaskResponseDTO();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toResponseDTO(task)).thenReturn(dto);

        TaskResponseDTO result = taskService.deleteTaskById(1L);

        verify(taskRepository).deleteById(1L);
        assertEquals(dto, result);
    }

    @Test
    void deleteTaskById_WhenTaskDoesNotExist_ThrowsException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(1L));
    }

    @Test
    void updateTask_WhenTaskExistsAndValidStatusChange_UpdatesAndReturnsDTO() {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("New Title");
        requestDTO.setStatus(TaskStatus.IN_PROGRESS);

        Task task = new Task();
        task.setStatus(TaskStatus.PENDING);

        TaskResponseDTO dto = new TaskResponseDTO();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toResponseDTO(task)).thenReturn(dto);

        TaskResponseDTO result = taskService.updateTask(1L, requestDTO);

        verify(taskRepository).save(task);
        assertEquals("New Title", task.getTitle());
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        assertEquals(dto, result);
    }

    @Test
    void updateTask_WhenInvalidStatusChange_ThrowsException() {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setStatus(TaskStatus.DONE);

        Task task = new Task();
        task.setStatus(TaskStatus.PENDING);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskInvalidStatusException ex = assertThrows(TaskInvalidStatusException.class,
                () -> taskService.updateTask(1L, requestDTO));
        assertTrue(ex.getMessage().contains("Solo puede pasar de PENDING a IN_PROGRESS"));
    }

    @Test
    void updateTask_WhenTaskNotFound_ThrowsException() {
        TaskRequestDTO requestDTO = new TaskRequestDTO();

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, requestDTO));
    }

}
