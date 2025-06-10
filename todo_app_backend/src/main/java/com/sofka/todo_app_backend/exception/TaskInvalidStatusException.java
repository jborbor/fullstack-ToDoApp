package com.sofka.todo_app_backend.exception;

public class TaskInvalidStatusException extends RuntimeException {
    public TaskInvalidStatusException(String message) {
        super(message);
    }
}
