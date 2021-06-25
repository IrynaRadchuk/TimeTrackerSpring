package com.example.exception;

public class PasswordMismatchException extends TimeTrackerException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
