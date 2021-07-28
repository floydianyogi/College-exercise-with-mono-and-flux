package com.example.collegewithmonoandflux.controller.model;

import lombok.ToString;
import lombok.Value;

@ToString
@Value
public class Department {
    public Integer did;
    public String departmentName;
    public Integer fid;
}
