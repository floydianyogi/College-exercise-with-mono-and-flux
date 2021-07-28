package com.example.collegewithmonoandflux.service;

import com.example.collegewithmonoandflux.controller.model.Department;
import com.example.collegewithmonoandflux.controller.model.Faculty;
import com.example.collegewithmonoandflux.controller.model.Student;
import com.example.collegewithmonoandflux.service.entity.FacultyEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Service
public class CollegeService2 {

    CollegeService1 collegeService1;

    public List<Faculty> faculties = new ArrayList<>();
    public List<Department> departments = new ArrayList<>();
    public List<Student> students = new LinkedList<>();


    public int addFaculty(String facultyName) { // TESTED
        Faculty faculty = new Faculty(createFid(facultyName),
                facultyName
        );

        faculties.add(faculty);
        return faculty.getFid();

    }

    public int addDepartment(int fid, String departmentName) { // TESTED
        return faculties.stream()
                .filter(f -> f.getFid() == fid)
                .map(f -> createDepartment(f, departmentName))
                .findFirst()
                .map(Department::getDid)
                .orElseThrow();
    }

    public Department createDepartment(Faculty f, String departmentName) { // TESTED
        Department department = new Department(createDid(departmentName),
                departmentName, f.getFid()
        );
        departments.add(department);
        return department;
    }

    public int createDid(String departmentName) { // TESTED
        switch (departmentName) {
            case "Electrical and Electronics Engineering":
                return 20;
            case "Computer Science":
                return 21;
            case "Psychology":
                return 22;
            case "Sociology":
                return 23;
            case "Public Law":
                return 24;
            case "Private Law":
                return 25;
            default:
                return 99;
        }
    }

    public int createFid(String facultyName) { // TESTED
        switch (facultyName) {
            case "Engineering":
                return 10;
            case "Arts and Sciences":
                return 11;
            case "Law":
                return 12;
            default:
                return 99;
        }
    }

//    public Flux<Faculty> getFaculties() {
//        List<FacultyEntity> allFaculties = collegeService1.getAllFaculties();
//        return Flux.range(0,9)
//                .doOnNext(i -> System.out.println("processing count in stream flow : " + i))
//                .map(i -> new Faculty(allFaculties.get(i).getFacultyCode(), allFaculties.get(i).getFacultyName()));
//    }

    public Flux<Faculty> getFaculties() { // TESTED
        return Flux.fromStream(faculties.stream());
    }

    public Flux<Department> getDepartments() { // TESTED
        return Flux.fromStream(departments.stream());
    }
    public Mono<String> addStudent(String firstName,
                          String lastName,
                          int entryRanking,
                          String phoneNumber,
                          String address,
                          int did) {
       return Mono.just(collegeService1.addStudent(did, firstName, lastName, entryRanking, phoneNumber, address));
    }
}
