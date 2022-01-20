package com.maksim.sunoplya.service.impl;

import com.maksim.sunoplya.dto.StudentDto;

public class TestData {
    public static StudentDto getStudentDtoActual() {
        StudentDto studentDto = new StudentDto();
        studentDto.setSurname("Test");
        return studentDto;
    }

    public static StudentDto getStudentDtoUpdate() {
        StudentDto studentDto = new StudentDto();
        studentDto.setSurname("TestUpdate");
        return studentDto;
    }

    public static String getIncorrectSurname() {
        return "IncorrectSurname";
    }
}