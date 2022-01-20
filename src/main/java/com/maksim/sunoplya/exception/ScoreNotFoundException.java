package com.maksim.sunoplya.exception;

public class ScoreNotFoundException extends AppException {
    public ScoreNotFoundException(Integer id) {
        super("Score with id " + id + " not found.");
    }
}