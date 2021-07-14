package com.example.CollegeExerciseWithMonoAndFlux.handler;

import com.example.CollegeExerciseWithMonoAndFlux.dao.StudentDao;
import com.example.CollegeExerciseWithMonoAndFlux.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentHandler {

    @Autowired
    private StudentDao dao;

    public Mono<ServerResponse> loadStudents(ServerRequest request) {
        Flux<Student> studentList = dao.getStudentList();
        return ServerResponse.ok().body(studentList, Student.class);
    }

    public Mono<ServerResponse> findStudent(ServerRequest request) {
        int studentId = Integer.parseInt(request.pathVariable("input"));
        Mono<Student> studentMono = dao.getStudentList()
                .filter(c -> c.getId() == studentId)
                .next();
        return ServerResponse.ok().body(studentMono, Student.class);
    }

    public Mono<ServerResponse> saveStudent(ServerRequest request) {
        Mono<Student> studentMono = request.bodyToMono(Student.class);
        Mono<String> saveResponse = studentMono
                .map(dto -> dto.getId() + "i" + dto.getName());
        return ServerResponse.ok().body(saveResponse, String.class);
    }

}
