package com.maksim.sunoplya.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentPerformanceDto {
    private String surname;

    @Override
    public String toString() {
        return surname;
    }
}