package com.maksim.sunoplya.exception;

public class DisciplineAlreadyExistsException extends AppException {
    public DisciplineAlreadyExistsException(String studentSurname, String name) {
        super("Discipline with name " + name + " has already added to student " + studentSurname + ".");
    }
}