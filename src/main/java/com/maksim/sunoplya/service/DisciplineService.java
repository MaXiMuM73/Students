package com.maksim.sunoplya.service;

import com.maksim.sunoplya.dto.DisciplineDto;
import com.maksim.sunoplya.dto.DisciplinePerformanceDto;
import com.maksim.sunoplya.dto.ScoreDto;

public interface DisciplineService {
    DisciplineDto create(String surname, DisciplineDto disciplineDto);

    DisciplineDto update(DisciplineDto disciplineDto, String surname, String disciplineName);

    ScoreDto addScoreToDiscipline(ScoreDto scoreDto, String surname, String discipline);

    ScoreDto deleteScore(Integer id, String surname, String disciplineName);

    ScoreDto updateScore(ScoreDto scoreDto, Integer id, String surname, String disciplineName);

    DisciplinePerformanceDto delete(String surname, String discipline);
}