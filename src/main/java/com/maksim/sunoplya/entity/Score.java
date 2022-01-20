package com.maksim.sunoplya.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Score {
    private Integer id;
    private Integer value;
    private LocalDateTime dateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        if (!id.equals(score.id)) return false;
        if (!value.equals(score.value)) return false;
        return dateTime.equals(score.dateTime);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + dateTime.hashCode();
        return result;
    }
}