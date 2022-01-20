package com.maksim.sunoplya.controller;

import com.maksim.sunoplya.dto.StudentDto;
import com.maksim.sunoplya.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.create(studentDto));
    }

    @PutMapping("/{surname}")
    public ResponseEntity<StudentDto> update(@PathVariable String surname,
                                             @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.update(surname, studentDto));
    }

    @DeleteMapping("/{surname}")
    public ResponseEntity<String> delete(@PathVariable String surname) {
        return ResponseEntity.ok(studentService.delete(surname));
    }

    @GetMapping("/report")
    public ResponseEntity<String> report(
            @PathParam(value = "surname") String surname) {
        return ResponseEntity.ok(studentService.report(surname));
    }
}