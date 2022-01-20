package com.maksim.sunoplya.service.impl;

import com.maksim.sunoplya.dto.DisciplinePerformanceDto;
import com.maksim.sunoplya.dto.ScoreDto;
import com.maksim.sunoplya.dto.StudentDto;
import com.maksim.sunoplya.dto.StudentPerformanceDto;
import com.maksim.sunoplya.entity.Discipline;
import com.maksim.sunoplya.entity.Student;
import com.maksim.sunoplya.repository.StudentRepository;
import com.maksim.sunoplya.service.StudentService;
import com.maksim.sunoplya.service.mapper.DisciplineMapper;
import com.maksim.sunoplya.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final DisciplineMapper disciplineMapper;

    @Override
    public StudentDto create(StudentDto studentDto) {
        StudentDto studentCreated = studentRepository.create(studentDto);

        log.info("Student " + studentCreated.getSurname() + " created.");

        return studentCreated;
    }

    @Override
    public StudentDto update(String surname, StudentDto studentDto) {
        StudentDto studentUpdated = studentRepository.update(surname, studentDto);

        log.info("Student " + surname + " update to " + studentUpdated.getSurname() + ".");

        return studentUpdated;
    }

    @Override
    public String delete(String surname) {
        String message = studentRepository.delete(surname);

        log.info(message);

        return message;
    }

    @Override
    public String findAll() {
        Map<Student, List<Discipline>> educationalPerformance = studentRepository.findAll();

        Map<StudentPerformanceDto, List<DisciplinePerformanceDto>> educationalPerformanceDto =
                getStudentPerformanceDtoMap(educationalPerformance);

        Map<StudentPerformanceDto, List<DisciplinePerformanceDto>> sortedEducationalPerformance =
                new TreeMap<>(Comparator.comparing(StudentPerformanceDto::getSurname));
        sortedEducationalPerformance.putAll(educationalPerformanceDto);

        log.info("Educational performance requested.");

        csv(sortedEducationalPerformance);
        return csv(sortedEducationalPerformance);
    }

    private Map<StudentPerformanceDto, List<DisciplinePerformanceDto>> getStudentPerformanceDtoMap(Map<Student, List<Discipline>> educationalPerformance) {
        return educationalPerformance.entrySet()
                .stream().collect(Collectors.toMap(
                        e -> studentMapper.toStudentPerformanceDto(e.getKey()),
                        e -> {
                            List<Discipline> disciplines = e.getValue();
                            return disciplines.stream()
                                    .map(disciplineMapper::disciplinePerformanceDto)
                                    .sorted(Comparator.comparing(DisciplinePerformanceDto::getName))
                                    .collect(Collectors.toList());
                        }
                ));
    }

    @Override
    public String findAllBySurname(String surname) {
        Map<Student, List<Discipline>> educationalPerformance = studentRepository.findAllBySurname(surname);

        Map<StudentPerformanceDto, List<DisciplinePerformanceDto>> educationalPerformanceDto = getStudentPerformanceDtoMap(educationalPerformance);

        Map<StudentPerformanceDto, List<DisciplinePerformanceDto>> sortedEducationalPerformance =
                new TreeMap<>(Comparator.comparing(StudentPerformanceDto::getSurname));
        sortedEducationalPerformance.putAll(educationalPerformanceDto);

        return csv(sortedEducationalPerformance);
    }

    private String csv(Map<StudentPerformanceDto, List<DisciplinePerformanceDto>> sortedEducationalPerformance) {
        StringBuilder csvReport = new StringBuilder();

        processReport(sortedEducationalPerformance, csvReport);

        return csvReport.toString();
    }

    private void processReport(Map<StudentPerformanceDto, List<DisciplinePerformanceDto>> sortedEducationalPerformance, StringBuilder csvReport) {
        sortedEducationalPerformance
                .forEach((student, disciplinePerformance) -> {
                    csvReport.append(student.getSurname()).append(",");
                    disciplinePerformance.forEach((disciplinePerformanceDto) -> appendPerformance(csvReport, disciplinePerformanceDto));
                    if (csvReport.length() > 0) csvReport.setLength(csvReport.length() - 1);
                    csvReport.append("\n");
                });
    }

    private void appendPerformance(StringBuilder csvReport, DisciplinePerformanceDto disciplinePerformance) {
        List<ScoreDto> scores = disciplinePerformance.getScores();
        double average = 0;

        if (scores.size() > 0) {
            Integer sum = scores.stream().map((ScoreDto::getValue)).reduce(0, Integer::sum);
            average = (double) sum / scores.size();
        }

        csvReport.append(disciplinePerformance.getName())
                .append(",")
                .append(average)
                .append(",");
    }
}