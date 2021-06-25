package com.example.exception;

public class RecordExistException extends TimeTrackerException {
    public RecordExistException(String message) {
        super(message);
    }
}
