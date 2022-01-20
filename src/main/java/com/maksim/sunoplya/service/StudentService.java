package com.maksim.sunoplya.service;

import com.maksim.sunoplya.dto.StudentDto;

public interface StudentService {
    StudentDto create(StudentDto studentDto);

    StudentDto update(String surname, StudentDto studentDto);

    String report(String surname);

    String delete(String surname);
}