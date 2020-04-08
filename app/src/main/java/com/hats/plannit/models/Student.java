package com.hats.plannit.models;

public class Student {

    private String studentID;
    private String email; //documentID
    private String password;
    private String username;
    private Boolean loggedIn;

    public Student(){}

    public Student(String studentID, String email, String password, String username, Boolean loggedIn) {
        this.studentID = studentID;
        this.email = email;
        this.password = password;
        this.username = username;
        this.loggedIn = loggedIn;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
