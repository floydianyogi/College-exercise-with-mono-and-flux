package com.example.CollegeExerciseWithMonoAndFlux.controller;

import com.example.CollegeExerciseWithMonoAndFlux.dto.Student;
import com.example.CollegeExerciseWithMonoAndFlux.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping
    public List<Student> getAllStudents() {
        return service.loadAllStudents();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getAllStudentsStream() {
        return service.loadAllStudentsStream();
    }
}
