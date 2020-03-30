package com.hats.plannit.models;

import java.util.List;

public class Subject
{
    private String subjectId;
    private String subjectName;
    private List<Course> courseList;

    public Subject(){};

    public Subject(String subjectName, List<Course> courseList)
    {
        this.subjectName = subjectName;
        this.courseList = courseList;
    }

    public String getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(String subjectId)
    {
        this.subjectId = subjectId;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public List<Course> getCourseList()
    {
        return courseList;
    }

    public void setCourseList(List<Course> courseList)
    {
        this.courseList = courseList;
    }
}
