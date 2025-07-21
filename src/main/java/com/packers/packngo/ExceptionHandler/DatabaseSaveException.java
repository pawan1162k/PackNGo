package com.packers.packngo.ExceptionHandler;

public class DatabaseSaveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DatabaseSaveException(String message) {
        super(message);
    }

    public DatabaseSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
