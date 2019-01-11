package com.example.raymond.counsellingapp;

public class Student {

    private String studentID;
    private String studentPass;
    private String studentName;
    private String studentEmail;
    private String studentDOB;

    public Student(){

    }

    public Student(String studentID, String studentPass, String studentName, String studentEmail, String studentDOB) {
        this.studentID = studentID;
        this.studentPass = studentPass;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentDOB = studentDOB;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setStudentPass(String studentPass) {
        this.studentPass = studentPass;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setStudentDOB(String studentDOB) {
        this.studentDOB = studentDOB;
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

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentDOB() {
        return studentDOB;
    }
}
