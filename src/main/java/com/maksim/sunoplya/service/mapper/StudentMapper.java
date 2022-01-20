package com.maksim.sunoplya.service.mapper;

import com.maksim.sunoplya.dto.StudentDto;
import com.maksim.sunoplya.dto.StudentPerformanceDto;
import com.maksim.sunoplya.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student toStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setSurname(studentDto.getSurname());
        return student;
    }

    public StudentPerformanceDto toStudentPerformanceDto(Student student) {
        StudentPerformanceDto studentPerformanceDto = new StudentPerformanceDto();
        studentPerformanceDto.setSurname(student.getSurname());
        return studentPerformanceDto;
    }
}