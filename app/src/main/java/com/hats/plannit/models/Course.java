package com.hats.plannit.models;

import com.google.firebase.firestore.Exclude;

public class Course {

    private String documentID;
    private String name;
    private String time;
    private String location;
    private String title;

    public Course(){}

    public Course(String name, String title, String time, String location) {
        this.name = name;
        this.title = title;
        this.time = time;
        this.location = location;
    }

    @Exclude
    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
