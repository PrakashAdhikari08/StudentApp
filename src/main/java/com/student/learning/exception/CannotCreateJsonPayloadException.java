package com.student.learning.exception;

public class CannotCreateJsonPayloadException extends RuntimeException{

    public CannotCreateJsonPayloadException(String message) {
        super(message);
    }
}
