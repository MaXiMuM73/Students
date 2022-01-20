package com.maksim.sunoplya.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DisciplinePerformanceDto {
    private String name;
    private List<ScoreDto> scores;
}