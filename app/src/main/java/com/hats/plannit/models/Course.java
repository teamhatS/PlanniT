package com.hats.plannit.models;

public class Course {

    private String name;
    private String time;
    private String location;

    public Course(){}

    public Course(String name, String time, String location) {
        this.name = name;
        this.time = time;
        this.location = location;
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
}
