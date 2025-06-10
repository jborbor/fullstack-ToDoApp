package com.sofka.todo_app_backend.dto.request;

import com.sofka.todo_app_backend.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class TaskRequestDTO {
    private Long id;

    @NotBlank(message="title is mandatory")
    private String title;

    @NotBlank(message="Description is mandatory")
    private String description;

    private Date createdDate;
    private TaskStatus status;
}
