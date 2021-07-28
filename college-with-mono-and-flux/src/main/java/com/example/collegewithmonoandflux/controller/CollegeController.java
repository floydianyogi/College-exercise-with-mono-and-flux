package com.example.collegewithmonoandflux.controller;


import com.example.collegewithmonoandflux.controller.model.Faculty;
import com.example.collegewithmonoandflux.service.CollegeService1;
import com.example.collegewithmonoandflux.service.CollegeService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/college")
public class CollegeController {

    @Autowired
    public CollegeService1 collegeService1;

    @Autowired
    public CollegeService2 collegeService2;

    @GetMapping(value = "/faculties", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Faculty> getAllFaculties() {
        return collegeService2.getFaculties();
    }

}
