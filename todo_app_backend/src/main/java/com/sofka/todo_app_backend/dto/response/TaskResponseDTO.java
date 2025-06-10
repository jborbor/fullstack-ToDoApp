package com.sofka.todo_app_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sofka.todo_app_backend.domain.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Bogota")
    private Date createdDate;

    private TaskStatus status;
}
