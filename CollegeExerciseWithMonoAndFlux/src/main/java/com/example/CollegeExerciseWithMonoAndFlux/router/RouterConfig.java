package com.example.CollegeExerciseWithMonoAndFlux.router;

import com.example.CollegeExerciseWithMonoAndFlux.handler.StudentHandler;
import com.example.CollegeExerciseWithMonoAndFlux.handler.StudentStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private StudentHandler handler;

    @Autowired
    private StudentStreamHandler streamHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/students", handler::loadStudents)
                .GET("/router/students/stream", streamHandler::getStudents)
                .GET("/router/student/{input}", handler::findStudent)
                .GET("/router/student/save", handler::saveStudent)
                .build();
    }
}
