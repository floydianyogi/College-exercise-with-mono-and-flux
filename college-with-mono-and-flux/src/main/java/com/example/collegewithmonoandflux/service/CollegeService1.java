package com.example.collegewithmonoandflux.service;

import com.example.collegewithmonoandflux.service.entity.DepartmentEntity;
import com.example.collegewithmonoandflux.service.entity.FacultyEntity;
import com.example.collegewithmonoandflux.service.entity.StudentEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class CollegeService1 {

    public List<FacultyEntity> faculties = new ArrayList<>();
    public List<DepartmentEntity> departments = new ArrayList<>();
    public List<StudentEntity> students = new LinkedList<>();


    public int addFaculty(String facultyName) { // TESTED
        FacultyEntity faculty = new FacultyEntity(createFacultyCode(facultyName), facultyName);
        faculties.add(faculty);
        return faculty.getFacultyCode();
    }

    public int createFacultyCode(String facultyName) { // TESTED
        switch (facultyName) {
            case "Faculty of Engineering":
                return 10;
            case "Faculty of Arts and Sciences":
                return 11;
            case "Faculty of Law":
                return 12;
            default:
                return 99;
        }
    }

    public List<FacultyEntity> getAllFaculties() { // TESTED
        return faculties;
    }

    public Optional<FacultyEntity> getFacultyByCode(int facultyCode) { // TESTED
        return faculties.stream()
                .filter(f -> f.getFacultyCode() == facultyCode)
                .findFirst();
    }

    public int addDepartment(int facultyCode, String departmentName) { // TESTED
        return faculties.stream()
                .filter(f -> f.getFacultyCode() == facultyCode)
                .map(f -> createDepartment(f, departmentName))
                .findFirst()
                .map(DepartmentEntity::getDepartmentCode)
                .orElseThrow();
    }

    public DepartmentEntity createDepartment(FacultyEntity f, String departmentName) { // TESTED
        DepartmentEntity department = new DepartmentEntity(
                createDepartmentCode(departmentName),
                departmentName,
                f.getFacultyCode()
        );

        departments.add(department);
        return department;
    }

    public int createDepartmentCode(String departmentName) { // TESTED
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

    public List<DepartmentEntity> getAllDepartments() { // TESTED
        return departments;
    }

    public Optional<DepartmentEntity> getDepartmentByDepartmentCode(int departmentCode) { // TESTED
        return departments.stream()
                .filter(d -> d.getDepartmentCode() == departmentCode)
                .findFirst();
    }

    public List<DepartmentEntity> getDepartmentsByFacultyCode(int facultyCode) { // TESTED
        return departments.stream()
                .filter(d -> d.getFacultyCode() == facultyCode)
                .collect(Collectors.toList());
    }

    public String addStudent( // TESTED
                              int departmentCode,
                              String firstName,
                              String lastName,
                              int entryRanking,
                              String phoneNumber,
                              String address
    ) {
        return departments.stream()
                .filter(d -> d.getDepartmentCode() == departmentCode)
                .map(d -> createStudent(d, firstName, lastName, entryRanking, phoneNumber, address))
                .findFirst()
                .map(this::createStudentNumber)
                .orElseThrow();
    }

    public StudentEntity createStudent( // TESTED
                                        DepartmentEntity d,
                                        String firstName,
                                        String lastName,
                                        Integer entryRanking,
                                        String phoneNumber,
                                        String address
    ) {
        StudentEntity studentEntity = new StudentEntity();

        studentEntity.firstName = firstName;
        studentEntity.lastName = lastName;
        studentEntity.phoneNumber = phoneNumber;
        studentEntity.address = address;
        studentEntity.email = createStudentEmailAddress(firstName, lastName);
        studentEntity.entryRanking = entryRanking;
        studentEntity.entryYear = LocalDateTime.now().getYear();
        studentEntity.facultyCode = d.getFacultyCode();
        studentEntity.departmentCode = d.getDepartmentCode();
        studentEntity.isActive = true;

        students.add(studentEntity);
        return studentEntity;

    }

    public String createStudentNumber(StudentEntity studentEntity) { // TESTED
        return String.format("%02d%03d%02d%03d",
                studentEntity.entryYear - 2000,
                studentEntity.facultyCode,
                studentEntity.departmentCode,
                studentEntity.entryRanking
        );
    }

    public String createStudentEmailAddress(String firstName, String lastName) { // TESTED
        return String.format("%s.%s@college.com",
                firstName,
                lastName);
    } // TODO - English character, lowercase letters...

    public void disableStudent(String studentNumber) { // TESTED
        updateStudent(studentNumber, s -> s.isActive = false);
    }

    public void enableStudent(String studentNumber) { // TESTED
        updateStudent(studentNumber, s -> s.isActive = true);
    }

    public void updateStudent(String studentNumber, Consumer<StudentEntity> updater) { // TESTED
        getStudentByStudentNumber(studentNumber)
                .ifPresentOrElse(
                        updater,
                        () -> {
                            throw new NoSuchElementException();
                        });
    }

    public void removeStudent(String studentNumber) { // TESTED
        getStudentByStudentNumber(studentNumber).ifPresentOrElse(
                s -> students.remove(s),
                () -> {
                    throw new NoSuchElementException();
                }
        );
    }

    public List<StudentEntity> getAllStudents() { // TESTED
        return students;
    }

    public Optional<StudentEntity> getStudentByStudentNumber(String studentNumber) { // TESTED
        if (studentNumber.length() != 10) {
            return Optional.empty();
        }
        int entryYear = 2000 + Integer.parseInt(studentNumber.substring(0, 2));
        int facultyCode = Integer.parseInt(studentNumber.substring(2, 5));
        int departmentCode = Integer.parseInt(studentNumber.substring(5, 7));
        int entryRanking = Integer.parseInt(studentNumber.substring(7));

        return students.stream()
                .filter(s -> (s.entryYear == entryYear &&
                        s.facultyCode == facultyCode &&
                        s.departmentCode == departmentCode &&
                        s.entryRanking == entryRanking))
                .findFirst();

    }

//    public Integer getStudentsCountByDepartment() { // TESTED
//        return departments.stream()
//                .map(d -> getStudentsByDepartmentCode(d.getDepartmentCode()))
//                .map(List::size)
//                .reduce(0, Integer::sum);
//    }

//    public Integer getStudentsCountNew() {
//        return students.stream()
//                .map(s -> 1)
//                .reduce(0, Integer::sum);
//
//
//    }

    public List<StudentEntity> getStudentsByFirstName(String firstName) { // TESTED
        return students.stream()
                .filter(s -> s.firstName.equals(firstName))
                .collect(Collectors.toList());
    }

    public List<StudentEntity> getStudentsByLastName(String lastName) { // TESTED
        return students.stream()
                .filter(s -> s.lastName.equals(lastName))
                .collect(Collectors.toList());
    }

    public Optional<StudentEntity> getStudentByPhoneNumber(String phoneNumber) { // TESTED
        return students.stream()
                .filter(s -> s.phoneNumber.equals(phoneNumber))
                .findFirst();
    }

    public Optional<StudentEntity> getStudentByAddress(String address) { // TESTED
        return students.stream()
                .filter(s -> s.address.equals(address))
                .findFirst();
    }

    public Optional<StudentEntity> getStudentByEmail(String email) { // TESTED
        return students.stream()
                .filter(s -> s.email.equals(email))
                .findFirst();
    }

    public Optional<StudentEntity> getStudentByEntryRanking(int entryRanking) { // TESTED
        return students.stream()
                .filter(s -> s.entryRanking == entryRanking)
                .findFirst();
    }

    public List<StudentEntity> getStudentsByEntryYear(int entryYear) { // TESTED
        return students.stream()
                .filter(s -> s.entryYear == entryYear)
                .collect(Collectors.toList());
    }

    public List<StudentEntity> getStudentsByFacultyCode(int facultyCode) { // TESTED
        return students.stream()
                .filter(s -> s.getFacultyCode() == facultyCode)
                .collect(Collectors.toList());
    }

    public List<StudentEntity> getStudentsByDepartmentCode(int departmentCode) { // TESTED
        return students.stream()
                .filter(s -> s.departmentCode == departmentCode)
                .collect(Collectors.toList());
    }

    public List<StudentEntity> getActiveStudents() { // TESTED
        return students.stream()
                .filter(s -> s.isActive)
                .collect(Collectors.toList());
    }

    public List<StudentEntity> getPassiveStudents() { // TESTED
        return students.stream()
                .filter(s -> !s.isActive)
                .collect(Collectors.toList());
    }


}

