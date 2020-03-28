package com.hats.plannit.models;

import com.google.firebase.firestore.Exclude;

public class Student {
    private String documentId;
    private String email;
    private String password;
    private String studentId;
    private String username;

    public Student(){}

    @Exclude
    public String getDocumentId(){return documentId;}

    public void setDocumentId(String documentId){ this.documentId = documentId;}

    public Student(String email, String password, String studentId, String username){
        this.email = email;
        this.password = password;
        this.studentId = studentId;
        this.username = username;
    }

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

    public String getStudentId(){return studentId;}
    public void setStudentId(String studentId){this.studentId = studentId;}

    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}
}
