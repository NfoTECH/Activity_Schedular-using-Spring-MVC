package com.fortunate.activitytracker.exception;

import java.io.Serial;

public class TaskNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    public TaskNotFoundException(String message) {
        super(message);
    }
}
