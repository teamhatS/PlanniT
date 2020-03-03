package com.hats.plannit.ui.search;

public class Assignment {

    private String courseName;
    private String assignmentName;
    private String date;
    private String time;
    private String description;

    public Assignment(String courseName, String assignmentName, String date, String time, String description) {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
        this.date = date;
        this.time = time;
        this.description = description;
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
}

