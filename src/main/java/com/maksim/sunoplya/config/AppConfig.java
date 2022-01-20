package com.maksim.sunoplya.config;

import com.maksim.sunoplya.entity.Discipline;
import com.maksim.sunoplya.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class AppConfig {
    @Bean
    public List<Student> students() {
        return new ArrayList<>();
    }

    @Bean
    public Map<Student, List<Discipline>> educationalPerformance() {
        return new HashMap<>();
    }
}