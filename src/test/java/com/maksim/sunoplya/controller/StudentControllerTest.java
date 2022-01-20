package com.maksim.sunoplya.controller;

import com.maksim.sunoplya.dto.StudentDto;
import com.maksim.sunoplya.service.StudentService;
import com.maksim.sunoplya.service.impl.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentControllerTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("studentController"));
    }

    @Test
    void givenNothing_whenFindAll_thenReturnCsvReport() {
        String report = studentService.findAll();
        assertNotNull(report);
    }

    @Test
    void givenStudentDto_whenCreate_thenReturnStudentDto() {
        StudentDto studentDto = new StudentDto();
        studentDto.setSurname("New user");
        StudentDto studentExpected = studentService.create(studentDto);

        assertEquals(studentExpected, studentDto);
    }

    @Test
    void givenStudentDto_whenUpdate_thenReturnStudentDtoUpdated() {
        StudentDto studentDtoUpdate = TestData.getStudentDtoUpdate();
        studentService.create(TestData.getStudentDtoActual());
        StudentDto studentExpected =
                studentService.update(TestData.getStudentDtoActual().getSurname(), studentDtoUpdate);

        assertEquals(studentExpected, studentDtoUpdate);
    }

    @Test
    void givenSurname_whenDelete_thenReturnMessage() {
        studentService.create(TestData.getStudentDtoActual());
        String message =
                studentService.delete(TestData.getStudentDtoActual().getSurname());

        assertTrue(message.contains("deleted."));
    }

    @Test
    void findAll() {
        studentService.create(TestData.getStudentDtoActual());
        String csvReport = studentService.findAll();

        assertNotNull(csvReport);
        assertTrue(csvReport.contains(TestData.getStudentDtoActual().getSurname()));

        studentService.delete(TestData.getStudentDtoActual().getSurname());
    }
}