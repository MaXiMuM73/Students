package com.maksim.sunoplya.controller;

import com.maksim.sunoplya.dto.DisciplineDto;
import com.maksim.sunoplya.dto.DisciplinePerformanceDto;
import com.maksim.sunoplya.dto.ScoreDto;
import com.maksim.sunoplya.service.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students/{surname}/disciplines")
public class DisciplineController {
    private final DisciplineService disciplineService;

    @PostMapping
    public ResponseEntity<DisciplineDto> create(@PathVariable String surname,
                                                @RequestBody DisciplineDto disciplineDto) {
        return ResponseEntity.ok(disciplineService.create(surname, disciplineDto));
    }

    @PutMapping("/{discipline}")
    public ResponseEntity<DisciplineDto> update(@RequestBody DisciplineDto disciplineDto,
                                                @PathVariable String surname,
                                                @PathVariable String discipline) {
        return ResponseEntity.ok(disciplineService.update(disciplineDto, surname, discipline));
    }

    @DeleteMapping("/{discipline}")
    public ResponseEntity<DisciplinePerformanceDto> delete(@PathVariable String surname,
                                                           @PathVariable String discipline) {
        return ResponseEntity.ok(disciplineService.delete(surname, discipline));
    }

    @PostMapping("/{discipline}")
    public ResponseEntity<ScoreDto> addScore(@RequestBody @Validated ScoreDto scoreDto,
                                             @PathVariable String surname,
                                             @PathVariable String discipline) {
        return ResponseEntity.ok(disciplineService.addScoreToDiscipline(scoreDto, surname, discipline));
    }

    @DeleteMapping("/{discipline}/{id}")
    public ResponseEntity<ScoreDto> deleteScore(@PathVariable Integer id,
                                                @PathVariable String surname,
                                                @PathVariable String discipline) {
        return ResponseEntity.ok(disciplineService.deleteScore(id, surname, discipline));
    }

    @PutMapping("/{discipline}/{id}")
    public ResponseEntity<ScoreDto> updateScore(@RequestBody @Validated ScoreDto scoreDto,
                                                @PathVariable Integer id,
                                                @PathVariable String surname,
                                                @PathVariable String discipline) {
        return ResponseEntity.ok(disciplineService.updateScore(scoreDto, id, surname, discipline));
    }
}