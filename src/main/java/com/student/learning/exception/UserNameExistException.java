package com.student.learning.exception;


public class UserNameExistException extends RuntimeException {
    public UserNameExistException(String username_does_not_exist) {
        super(username_does_not_exist);
    }
}
