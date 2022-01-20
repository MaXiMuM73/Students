package com.maksim.sunoplya.service.impl;

import com.maksim.sunoplya.dto.StudentDto;
import com.maksim.sunoplya.entity.Discipline;
import com.maksim.sunoplya.entity.Student;
import com.maksim.sunoplya.exception.StudentAlreadyExistsException;
import com.maksim.sunoplya.exception.StudentNotFoundException;
import com.maksim.sunoplya.repository.StudentRepository;
import com.maksim.sunoplya.service.mapper.DisciplineMapper;
import com.maksim.sunoplya.service.mapper.ScoreMapper;
import com.maksim.sunoplya.service.mapper.StudentMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    private static StudentRepository studentRepository;
    private static StudentMapper studentMapper;
    private static DisciplineMapper disciplineMapper;
    private static StudentDto studentDtoActual;

    @BeforeAll
    static void init() {
        studentMapper = new StudentMapper();
        disciplineMapper = new DisciplineMapper(new ScoreMapper());
        studentDtoActual = TestData.getStudentDtoActual();
    }

    @BeforeEach
    void initRepository() {
        studentRepository = new StudentRepository(studentMapper, new HashMap<>());
        studentRepository.create(studentDtoActual);
    }

    @Test
    void givenStudentDto_whenCreate_thenReturnStudentCreated() {
        StudentDto newStudent = new StudentDto();
        newStudent.setSurname("New student");
        StudentDto studentDtoExpected = studentRepository.create(newStudent);

        assertEquals(studentDtoExpected, newStudent);
    }

    @Test
    void givenStudentDto_whenCreate_thenReturnStudentAlreadyExistsException() {
        StudentAlreadyExistsException studentAlreadyExistsException =
                assertThrows(StudentAlreadyExistsException.class,
                        () -> studentRepository.create(studentDtoActual));

        assertTrue(studentAlreadyExistsException.getMessage().contains("already exists."));
    }

    @Test
    void givenSurnameAndStudentDto_whenUpdate_thenReturnStudentUpdated() {
        StudentDto studentDtoUpdate = TestData.getStudentDtoUpdate();
        StudentDto studentDtoExpected = studentRepository
                .update(studentDtoActual.getSurname(), studentDtoUpdate);

        assertEquals(studentDtoExpected, studentDtoUpdate);
    }

    @Test
    void givenIncorrectSurname_whenUpdate_thenReturnStudentNotFoundException() {
        StudentNotFoundException studentNotFoundException = assertThrows(StudentNotFoundException.class, () -> studentRepository
                .update(TestData.getIncorrectSurname(), TestData.getStudentDtoUpdate()));

        assertTrue(studentNotFoundException.getMessage().contains("not found."));
    }

    @Test
    void givenSurname_whenDelete_thenReturnResponse() {
        String message = studentRepository.delete(studentDtoActual.getSurname());

        assertTrue(message.contains("deleted."));
    }

    @Test
    void givenIncorrectSurname_whenDelete_thenReturnStudentNotFoundException() {
        StudentNotFoundException studentNotFoundException = assertThrows(StudentNotFoundException.class, () -> studentRepository
                .delete(TestData.getIncorrectSurname()));

        assertTrue(studentNotFoundException.getMessage().contains("not found."));
    }

    @Test
    void givenNothing_whenFindAll_thenReturnNoEmptyAndContainsTestStudent() {
        Map<Student, List<Discipline>> educationalPerformance = studentRepository.findAll();

        assertFalse(educationalPerformance.isEmpty());
        assertTrue(educationalPerformance.containsKey(studentMapper.toStudent(studentDtoActual)));
    }

    @Test
    void givenSurname_whenFindStudentBySurname_thenReturnStudent() {
        Student studentExpected = studentRepository.findStudentBySurname(studentDtoActual.getSurname());

        assertEquals(studentExpected.getSurname(), studentDtoActual.getSurname());
    }

    @Test
    void givenIncorrectSurname_whenFindStudentBySurname_thenReturnStudentNotFoundException() {
        StudentNotFoundException studentNotFoundException = assertThrows(StudentNotFoundException.class, () -> studentRepository
                .findStudentBySurname(TestData.getIncorrectSurname()));

        assertTrue(studentNotFoundException.getMessage().contains("not found."));
    }
}