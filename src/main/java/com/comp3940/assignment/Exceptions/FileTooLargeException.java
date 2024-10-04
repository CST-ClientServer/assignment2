package com.comp3940.assignment.Exceptions;

public class FileTooLargeException extends Exception {
    public FileTooLargeException(String errorMessage) {
        super(errorMessage);
    }
}
