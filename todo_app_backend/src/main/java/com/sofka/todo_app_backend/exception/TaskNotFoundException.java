package com.sofka.todo_app_backend.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("No se encontró la tarea con ID: " + id);
    }
}
