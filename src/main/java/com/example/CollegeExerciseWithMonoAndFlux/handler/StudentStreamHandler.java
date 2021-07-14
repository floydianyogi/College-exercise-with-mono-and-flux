package com.example.CollegeExerciseWithMonoAndFlux.handler;

import com.example.CollegeExerciseWithMonoAndFlux.dao.StudentDao;
import com.example.CollegeExerciseWithMonoAndFlux.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentStreamHandler {

    @Autowired
    private StudentDao dao;

    public Mono<ServerResponse> getStudents(ServerRequest request) {
        Flux<Student> studentsStream = dao.getStudentsStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(studentsStream, Student.class);
    }
}
