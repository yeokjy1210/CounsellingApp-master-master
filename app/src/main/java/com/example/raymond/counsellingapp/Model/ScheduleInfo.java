package com.example.raymond.counsellingapp.Model;

public class ScheduleInfo {
    private String name = null;
    private String[] times = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalTimes(){
        int result = times.length;
        return result;
    }

    public String[] getTimes() {
        return times;
    }

    public void setScheduleTime(String[] scheduleTimes) {
        this.times = scheduleTimes;
    }

    public void setScheduleTime(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {
        this.times = new String[]{monday, tuesday, wednesday, thursday, friday, saturday, sunday};
    }
}