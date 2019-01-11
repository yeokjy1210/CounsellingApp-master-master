package com.example.raymond.counsellingapp.Model;

import java.util.ArrayList;

public class StudentSchedule {
    private ArrayList<ScheduleInfo> courseList = null;

    public ArrayList<ScheduleInfo> getScheduleList() {
        return courseList;
    }

    public void setScheduleList(ArrayList<ScheduleInfo> courseList) {
        this.courseList = courseList;
    }


}