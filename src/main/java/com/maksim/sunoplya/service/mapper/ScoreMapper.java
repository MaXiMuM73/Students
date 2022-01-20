package com.maksim.sunoplya.service.mapper;

import com.maksim.sunoplya.dto.ScoreDto;
import com.maksim.sunoplya.entity.Score;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScoreMapper {
    private Integer count = 0;

    public Score toScore(ScoreDto scoreDto) {
        count++;
        Score score = new Score();
        score.setId(count);
        score.setValue(scoreDto.getValue());
        score.setDateTime(LocalDateTime.now());
        return score;
    }

    public ScoreDto toScoreDto(Score score) {
        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setId(score.getId());
        scoreDto.setValue(score.getValue());
        scoreDto.setDateTime(score.getDateTime());
        return scoreDto;
    }
}