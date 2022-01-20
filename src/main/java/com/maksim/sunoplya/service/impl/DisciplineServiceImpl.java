package com.maksim.sunoplya.service.impl;

import com.maksim.sunoplya.dto.DisciplineDto;
import com.maksim.sunoplya.dto.DisciplinePerformanceDto;
import com.maksim.sunoplya.dto.ScoreDto;
import com.maksim.sunoplya.entity.Discipline;
import com.maksim.sunoplya.entity.Score;
import com.maksim.sunoplya.entity.Student;
import com.maksim.sunoplya.exception.DisciplineAlreadyExistsException;
import com.maksim.sunoplya.exception.DisciplineNotFoundException;
import com.maksim.sunoplya.exception.ScoreNotFoundException;
import com.maksim.sunoplya.repository.StudentRepository;
import com.maksim.sunoplya.service.DisciplineService;
import com.maksim.sunoplya.service.mapper.DisciplineMapper;
import com.maksim.sunoplya.service.mapper.ScoreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisciplineServiceImpl implements DisciplineService {
    private final StudentRepository studentRepository;
    private final DisciplineMapper disciplineMapper;
    private final ScoreMapper scoreMapper;
    private final Map<Student, List<Discipline>> educationalPerformance;

    @Override
    public DisciplineDto create(String surname, DisciplineDto disciplineDto) {
        Student student = studentRepository.findStudentBySurname(surname);
        Discipline discipline = disciplineMapper.toDiscipline(disciplineDto);
        List<Discipline> disciplineList = educationalPerformance.get(student);
        if (disciplineList.contains(discipline)) {
            throw new DisciplineAlreadyExistsException(student.getSurname(), disciplineDto.getName());
        }
        disciplineList.add(discipline);

        log.info("Discipline " + disciplineDto.getName() + " added to student " + surname + ".");

        return disciplineDto;
    }

    @Override
    public DisciplineDto update(DisciplineDto disciplineDto, String surname, String disciplineName) {
        Discipline discipline = findDisciplineByStudentSurnameAndDisciplineName(surname, disciplineName);
        discipline.setName(disciplineDto.getName());

        log.info("Discipline " + disciplineName + " renamed to " + disciplineDto.getName() + ".");

        return disciplineDto;
    }

    @Override
    public DisciplinePerformanceDto delete(String surname, String disciplineName) {
        Student student = studentRepository.findStudentBySurname(surname);
        List<Discipline> disciplineList = educationalPerformance.get(student);
        Discipline discipline = findDisciplineByStudentSurnameAndDisciplineName(surname, disciplineName);
        disciplineList.remove(discipline);

        log.info("Discipline " + disciplineName + " deleted from student " + surname + ".");

        return disciplineMapper.disciplinePerformanceDto(discipline);
    }

    @Override
    public ScoreDto addScoreToDiscipline(ScoreDto scoreDto, String surname, String disciplineName) {
        Discipline discipline = findDisciplineByStudentSurnameAndDisciplineName(surname, disciplineName);
        Score score = scoreMapper.toScore(scoreDto);
        discipline.getScores().add(score);

        log.info("Score " + scoreDto.getValue() + " added to discipline " + disciplineName
                + " to student " + surname + ".");

        return scoreMapper.toScoreDto(score);
    }

    @Override
    public ScoreDto deleteScore(Integer id, String surname, String disciplineName) {
        Discipline discipline = findDisciplineByStudentSurnameAndDisciplineName(surname, disciplineName);
        Score score = findScoreById(discipline, id);
        discipline.getScores().remove(score);

        log.info("Score with id " + id + " deleted.");

        return scoreMapper.toScoreDto(score);
    }

    @Override
    public ScoreDto updateScore(ScoreDto scoreDto, Integer id, String surname, String disciplineName) {
        Discipline discipline = findDisciplineByStudentSurnameAndDisciplineName(surname, disciplineName);
        Score score = findScoreById(discipline, id);
        score.setValue(scoreDto.getValue());
        score.setDateTime(LocalDateTime.now());

        log.info("Score with id " + id + " updated to " + scoreDto.getValue()
                + " to student " + surname + ".");

        return scoreMapper.toScoreDto(score);
    }

    public Discipline findDisciplineByStudentSurnameAndDisciplineName(String surname, String disciplineName) {
        Student student = studentRepository.findStudentBySurname(surname);
        List<Discipline> disciplineList = educationalPerformance.get(student);
        return disciplineList
                .stream()
                .filter(s -> s.getName().equalsIgnoreCase(disciplineName))
                .findAny()
                .orElseThrow(() -> new DisciplineNotFoundException(disciplineName));
    }

    public Score findScoreById(Discipline discipline, Integer id) {
        return discipline.getScores()
                .stream()
                .filter(d -> d.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ScoreNotFoundException(id));
    }
}