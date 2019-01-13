package com.example.raymond.counsellingapp;

public class Event {
    private String eventID;
    private String eventName;
    private String eventDesc;
    private String eventDate;
    private String eventTime;
    private String eventVenue;
    private int eventFee;
    private String eventImg;

    public Event(String eventID, String eventName, String eventDesc, String eventDate,
                 String eventTime, String eventVenue, int eventFee, String eventImg) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDesc = eventDesc;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventVenue = eventVenue;
        this.eventFee = eventFee;
        this.eventImg = eventImg;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public int getEventFee() {
        return eventFee;
    }

    public String getEventImg() {
        return eventImg;
    }
}
