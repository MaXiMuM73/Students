package com.maksim.sunoplya.exception;

public class DisciplineNotFoundException extends AppException {
    public DisciplineNotFoundException(String disciplineName) {
        super("Discipline " + disciplineName + " not found.");
    }
}