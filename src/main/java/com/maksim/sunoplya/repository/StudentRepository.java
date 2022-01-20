package com.maksim.sunoplya.repository;

import com.maksim.sunoplya.dto.StudentDto;
import com.maksim.sunoplya.entity.Discipline;
import com.maksim.sunoplya.entity.Student;
import com.maksim.sunoplya.exception.StudentAlreadyExistsException;
import com.maksim.sunoplya.exception.StudentNotFoundException;
import com.maksim.sunoplya.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StudentRepository {
    private final StudentMapper studentMapper;
    private final Map<Student, List<Discipline>> educationalPerformance;

    public StudentDto create(StudentDto studentDto) {
        Student student = studentMapper.toStudent(studentDto);
        if (educationalPerformance.containsKey(student)) {
            throw new StudentAlreadyExistsException(studentDto.getSurname());
        }
        educationalPerformance.put(student, new ArrayList<>());
        return studentDto;
    }

    public StudentDto update(String surname, StudentDto studentDto) {
        Student student = findStudentBySurname(surname);
        student.setSurname(studentDto.getSurname());
        return studentDto;
    }

    public String delete(String surname) {
        Student student = findStudentBySurname(surname);
        educationalPerformance.remove(student);
        return "Student " + surname + " deleted.";
    }

    public Student findStudentBySurname(String surname) {
        return educationalPerformance
                .entrySet()
                .stream()
                .filter(s -> s.getKey().getSurname().equalsIgnoreCase(surname))
                .findAny().orElseThrow(() -> new StudentNotFoundException(surname))
                .getKey();
    }

    public Map<Student, List<Discipline>> findAll() {
        return educationalPerformance;
    }

    public Map<Student, List<Discipline>> findAllBySurname(String surname) {
        Student student = findStudentBySurname(surname);
        List<Discipline> disciplines = educationalPerformance.get(student);
        Map<Student, List<Discipline>> educationalPerformance = new HashMap<>();
        educationalPerformance.put(student, disciplines);
        return educationalPerformance;
    }
}