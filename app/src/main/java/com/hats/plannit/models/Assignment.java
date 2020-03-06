package com.hats.plannit.models;

import com.google.firebase.firestore.Exclude;

public class Assignment {
    private String documentId;
    private String courseName;
    private String assignmentName;
    private String date;
    private String time;
    private String description;
    private Boolean complete;
    private Boolean expanded;

    public Assignment(){}

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Assignment(String courseName, String assignmentName, String date, String time, String description) {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public Assignment(String courseName, String assignmentName, String date, String time, String description, Boolean complete) {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
        this.date = date;
        this.time = time;
        this.description = description;
        this.complete = complete;
    }

    public Assignment(String courseName, String assignmentName, String date, String time, String description, Boolean complete, Boolean expanded) {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
        this.date = date;
        this.time = time;
        this.description = description;
        this.complete = complete;
        this.expanded = expanded;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}

