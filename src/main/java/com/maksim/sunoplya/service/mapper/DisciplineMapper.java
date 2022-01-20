package com.maksim.sunoplya.service.mapper;

import com.maksim.sunoplya.dto.DisciplineDto;
import com.maksim.sunoplya.dto.DisciplinePerformanceDto;
import com.maksim.sunoplya.entity.Discipline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DisciplineMapper {
    private final ScoreMapper scoreMapper;

    public Discipline toDiscipline(DisciplineDto disciplineDto) {
        Discipline discipline = new Discipline();
        discipline.setName(disciplineDto.getName());
        return discipline;
    }

    public DisciplinePerformanceDto disciplinePerformanceDto(Discipline discipline) {
        DisciplinePerformanceDto disciplinePerformanceDto = new DisciplinePerformanceDto();
        disciplinePerformanceDto.setName(discipline.getName());
        disciplinePerformanceDto.setScores(discipline.getScores()
                .stream().map(scoreMapper::toScoreDto)
                .collect(Collectors.toList()));
        return disciplinePerformanceDto;
    }
}