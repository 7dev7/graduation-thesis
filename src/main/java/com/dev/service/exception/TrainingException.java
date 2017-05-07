package com.dev.service.exception;

public class TrainingException extends ServiceException {

    public TrainingException() {
        super();
    }

    public TrainingException(String message) {
        super(message);
    }

    public TrainingException(String message, Throwable cause) {
        super(message, cause);
    }
}
