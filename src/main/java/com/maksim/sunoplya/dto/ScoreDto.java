package com.maksim.sunoplya.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScoreDto {
    private Integer id;

    @NotNull(message = "Score value cannot be empty.")
    @Min(value = 2, message = "Score value should be between 2 and 5")
    @Max(value = 5, message = "Score value should be between 2 and 5")
    private Integer value;
    private LocalDateTime dateTime;
}