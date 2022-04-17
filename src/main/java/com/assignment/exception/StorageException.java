package com.assignment.exception;

public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }
}
