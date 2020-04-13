package com.hats.plannit.models;

public class Student {

    private String studentId;//documentID
    private String email;
    private String password;
    private String username;

    public Student(){}

    public Student(String studentID, String email, String password, String username) {
        this.studentId = studentID;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getStudentID() {
        return studentId;
    }

    public void setStudentID(String studentID) {
        this.studentId = studentID;
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
