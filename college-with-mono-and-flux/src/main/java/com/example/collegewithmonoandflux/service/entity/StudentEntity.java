package com.example.collegewithmonoandflux.service.entity;

import lombok.Data;

@Data
public class StudentEntity {
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String address;
    public String email;
    public Integer entryRanking;
    public Integer entryYear;
    public Integer facultyCode;
    public Integer departmentCode;
    public boolean isActive;
}
