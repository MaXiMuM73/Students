package com.maksim.sunoplya.exception;

public class StudentAlreadyExistsException extends AppException {
    public StudentAlreadyExistsException(String surname) {
        super("Student with surname " + surname + " already exists.");
    }
}