package com.sofka.todo_app_backend.domain.entity;

import com.sofka.todo_app_backend.domain.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task() {
        this.createdDate = new Date();
        this.status = TaskStatus.PENDING;
    }
}