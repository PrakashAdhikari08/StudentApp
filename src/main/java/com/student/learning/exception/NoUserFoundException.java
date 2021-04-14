package com.student.learning.exception;

public class NoUserFoundException extends RuntimeException {
    public NoUserFoundException(String format) {
        super(format);
    }
}
