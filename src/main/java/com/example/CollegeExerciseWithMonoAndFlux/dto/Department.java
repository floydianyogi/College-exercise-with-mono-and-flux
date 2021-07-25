package com.example.CollegeExerciseWithMonoAndFlux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    public int did;
    public int fid;
    public String name;
}
