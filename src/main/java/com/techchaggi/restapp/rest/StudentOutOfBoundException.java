package com.techchaggi.restapp.rest;

public class StudentOutOfBoundException extends RuntimeException{
    public StudentOutOfBoundException(String message) {
        super(message);
    }

    public StudentOutOfBoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentOutOfBoundException(Throwable cause) {
        super(cause);
    }
}
