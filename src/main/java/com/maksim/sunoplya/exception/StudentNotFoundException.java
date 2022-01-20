package com.maksim.sunoplya.exception;

public class StudentNotFoundException extends AppException {
    public StudentNotFoundException(String surname) {
        super("Student with surname " + surname + " not found.");
    }
}