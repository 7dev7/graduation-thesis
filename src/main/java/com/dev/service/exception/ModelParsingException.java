package com.dev.service.exception;

public class ModelParsingException extends ServiceException {
    public ModelParsingException() {
        super();
    }

    public ModelParsingException(String message) {
        super(message);
    }

    public ModelParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
