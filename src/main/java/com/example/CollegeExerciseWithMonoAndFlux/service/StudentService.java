package com.example.CollegeExerciseWithMonoAndFlux.service;

import com.example.CollegeExerciseWithMonoAndFlux.dao.StudentDao;
import com.example.CollegeExerciseWithMonoAndFlux.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentDao dao;

    public List<Student> loadAllStudents() {
        long start = System.currentTimeMillis();
        List<Student> students = dao.getStudents();
        long end = System.currentTimeMillis();
        System.out.println("Total execution time : " + (end - start));
        return students;
    }

    public Flux<Student> loadAllStudentsStream() {
        long start = System.currentTimeMillis();
        Flux<Student> students = dao.getStudentsStream();
        long end = System.currentTimeMillis();
        System.out.println("Total execution time : " + (end - start));
        return students;
    }
}
