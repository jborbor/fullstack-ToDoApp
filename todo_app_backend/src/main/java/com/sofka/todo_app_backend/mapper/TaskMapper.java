package com.sofka.todo_app_backend.mapper;

import com.sofka.todo_app_backend.domain.entity.Task;
import com.sofka.todo_app_backend.dto.request.TaskRequestDTO;
import com.sofka.todo_app_backend.dto.response.TaskResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TaskMapper {
    private final ModelMapper modelMapper;

    public TaskResponseDTO toResponseDTO(Task task) {
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    public List<TaskResponseDTO> toResponseDtoList(List<Task> task) {
        return task.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Task toResponseEntity(TaskRequestDTO taskRequestDTO) {
        return modelMapper.map(taskRequestDTO, Task.class);
    }

}
