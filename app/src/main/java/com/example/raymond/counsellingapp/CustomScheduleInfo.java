package com.example.raymond.counsellingapp;

import com.example.raymond.counsellingapp.Model.ScheduleInfo;

public class CustomScheduleInfo extends ScheduleInfo {
    private String location;
    private String student;
    private String counselor;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getCounselor() {
        return counselor;
    }

    public void setCounselor(String counselor) {
        this.counselor = counselor;
    }
}
