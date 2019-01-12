package com.example.raymond.counsellingapp;

public class Counselor {
    private String counselorName;
    private String counselorDOB;
    private String counselorType;
    private String counselorDesc;
    private String counselorContact;
    private String counselorEmail;
    private String counselorVenue;

    public Counselor(){

    }

    //use at counselor list
    public Counselor(String counselorName, String counselorType) {
        this.counselorName = counselorName;
        this.counselorType = counselorType;
    }

    //use at counselor detail
    public Counselor(String counselorName, String counselorDOB, String counselorType, String counselorDesc, String counselorContact, String counselorEmail, String counselorVenue) {
        this.counselorName = counselorName;
        this.counselorDOB = counselorDOB;
        this.counselorType = counselorType;
        this.counselorDesc = counselorDesc;
        this.counselorContact = counselorContact;
        this.counselorEmail = counselorEmail;
        this.counselorVenue = counselorVenue;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public String getCounselorDOB() {
        return counselorDOB;
    }

    public String getCounselorType() {
        return counselorType;
    }

    public String getCounselorDesc() {
        return counselorDesc;
    }

    public String getCounselorContact() {
        return counselorContact;
    }

    public String getCounselorEmail() {
        return counselorEmail;
    }

    public String getCounselorVenue() {
        return counselorVenue;
    }
}
