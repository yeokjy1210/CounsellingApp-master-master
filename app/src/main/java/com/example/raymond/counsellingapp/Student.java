package com.example.raymond.counsellingapp;

public class Student {

    private String studentID;
    private String studentPass;
    private String studentName;
    private String studentPhone;
    private String studentEmail;
    private String studentDOB;


    public Student(String studentID, String studentPass, String studentName, String studentPhone, String studentEmail, String studentDOB) {
        this.studentID = studentID;
        this.studentPass = studentPass;
        this.studentName = studentName;
        this.studentPhone = studentPhone;
        this.studentEmail = studentEmail;
        this.studentDOB = studentDOB;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentPass() {
        return studentPass;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentPhone(){ return studentPhone;}

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentDOB() {
        return studentDOB;
    }
}
