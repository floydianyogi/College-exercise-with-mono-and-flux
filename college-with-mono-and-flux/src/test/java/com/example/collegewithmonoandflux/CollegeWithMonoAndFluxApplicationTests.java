package com.example.collegewithmonoandflux;

import com.example.collegewithmonoandflux.controller.model.Department;
import com.example.collegewithmonoandflux.controller.model.Faculty;
import com.example.collegewithmonoandflux.service.CollegeService1;
import com.example.collegewithmonoandflux.service.CollegeService2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.NoSuchElementException;

@SpringBootTest
class CollegeWithMonoAndFluxApplicationTests {

    @Autowired
    CollegeService1 collegeService1;

    @Autowired
    CollegeService2 collegeService2;

    // Tests For CollegeService2

    @Test
    void addFacultyTest2() {
        int fid1 = collegeService2.addFaculty("Engineering");
        int fid2 = collegeService2.addFaculty("Arts and Sciences");
        int fid3 = collegeService2.addFaculty("Law");

        Flux<Faculty> faculties = collegeService2.getFaculties()
                .log();

        StepVerifier.create(faculties)
                .expectNext(new Faculty(10, "Engineering"), new Faculty(11, "Arts and Sciences"), new Faculty(12, "Law"))
                .verifyComplete();
    }

    @Test
    void addDepartmentTest2() {
        collegeService2.addFaculty("Engineering");
        collegeService2.addFaculty("Arts and Sciences");
        collegeService2.addFaculty("Law");

        collegeService2.addDepartment(10, "Electrical and Electronics Engineering");
        collegeService2.addDepartment(10, "Computer Science");
        collegeService2.addDepartment(11, "Psychology");
        collegeService2.addDepartment(11, "Sociology");
        collegeService2.addDepartment(12, "Public Law");
        collegeService2.addDepartment(12, "Private Law");

        Flux<Department> departments = collegeService2.getDepartments()
                .log();

        StepVerifier.create(departments)
                .expectNext(new Department(20, "Electrical and Electronics Engineering", 10),
                            new Department(21, "Computer Science", 10),
                            new Department(22, "Psychology", 11),
                            new Department(23, "Sociology", 11),
                            new Department(24, "Public Law", 12),
                            new Department(25, "Private Law", 12))
                .verifyComplete();


    }

    @Test
    void addDepartmentToInvalidFacultyTest2() {
        try {
            collegeService2.addDepartment(55, "History");
        } catch (NoSuchElementException e) {
            Assertions.assertEquals("No value present", e.getMessage());
        }
    }

    @Test
    void getFacultiesTest2() {
        collegeService2.addFaculty("Engineering");
        collegeService2.addFaculty("Arts and Sciences");

        Flux<Faculty> faculties = collegeService2.getFaculties()
                .log();

        StepVerifier.create(faculties)
                .expectNext(new Faculty(10, "Engineering"), new Faculty(11, "Arts and Sciences"))
                .verifyComplete();

    }

    @Test
    void getDepartmentsTest2() {
        collegeService2.addFaculty("Engineering");
        collegeService2.addFaculty("Arts and Sciences");

        collegeService2.addDepartment(10, "Computer Science");
        collegeService2.addDepartment(11, "Psychology");

        Flux<Department> departments = collegeService2.getDepartments()
                .log();

        StepVerifier.create(departments)
                .expectNext(new Department(21, "Computer Science", 10), new Department(22, "Psychology", 11))
                .verifyComplete();
    }

    @Test
    void addStudentTest2() {
        collegeService2.addFaculty("Engineering");
        collegeService2.addDepartment(10, "Computer Science");
        int sid = collegeService2.addStudent("Berk", "Çınar", 27, "5555555","İstanbul", 21);




    }


    @Test
    void contextLoads() {
    }

}
