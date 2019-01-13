package com.example.raymond.counsellingapp;

public class Counselor {
    private String counselorID;
    private String counselorName;
    private int counselorAge;
    private String counselorType;
    private String counselorDesc;
    private String counselorContact;
    private String counselorEmail;
    private String counselorImg;
    private String counselorVenue;
    private int counselorExp;


    //use at counselor detail
    public Counselor(String counselorID, String counselorName, int counselorAge,
                     String counselorType, String counselorDesc, String counselorContact,
                     String counselorEmail, String counselorImg, String counselorVenue, int counselorExp) {
        this.counselorID = counselorID;
        this.counselorName = counselorName;
        this.counselorAge = counselorAge;
        this.counselorType = counselorType;
        this.counselorDesc = counselorDesc;
        this.counselorContact = counselorContact;
        this.counselorEmail = counselorEmail;
        this.counselorImg = counselorImg;
        this.counselorVenue = counselorVenue;
        this.counselorExp = counselorExp;
    }

    public String getCounselorID() {
        return counselorID;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public int getCounselorAge() {
        return counselorAge;
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

    public String getCounselorImg() {
        return counselorImg;
    }

    public String getCounselorVenue() {
        return counselorVenue;
    }

    public int getCounselorExp() {
        return counselorExp;
    }
}
