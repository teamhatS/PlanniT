package com.hats.plannit.ui.courses;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hats.plannit.models.Course;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Howard chen
 */

public class CoursesViewModel extends ViewModel
{
    private AvailableCourseRepo availableCourseRepo;
    private MutableLiveData<List<Course>> mAvailableCourseList;

    public void init()
    {
        if(availableCourseRepo != null)
        {
            return;
        }
        availableCourseRepo = AvailableCourseRepo.getInstance();
        mAvailableCourseList = availableCourseRepo.getAvailableCourses();

//        new AsyncTask<Void, Void, Void>()
//        {
//            @Override
//            protected Void doInBackground(Void... voids)
//            {
//                try
//                {
//                    Thread.sleep(1000);
//                }
//                catch (InterruptedException e)
//                {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        }.execute();
    }

    public MutableLiveData<List<Course>> getmAvailableCourseList()
    {
        return mAvailableCourseList;
    }

    public ArrayList<Integer> addCourses(List<Course> availableCourseList, List<Course> selectedItems)
    {
        ArrayList<Integer> duplicateCourses = new ArrayList<>();
        ArrayList<Course> courseListTemp = new ArrayList<>();

        for(int i: selectedItems)
        {
            if(CourseAsset.registeredCourseList.contains(availableCourseList.get(i)))
            {
                duplicateCourses.add(i);
            }
            else
            {
                courseListTemp.add(availableCourseList.get(i));
            }
        }

        if(duplicateCourses.isEmpty())
        {
            CourseAsset.registeredCourseList.addAll(courseListTemp);
        }
        return duplicateCourses;
    }

    public void addAllAvailableCourses(Context context)
    {
        Course course1 = new Course("CECS 445", "Software Design and Architecture", "M/W 3:30PM- 4:45PM", "ECS  Room 308", 6299);
        Course course2 = new Course("CECS 327", "Introduction to Networks and Distributed Computing", "T/F 12:30PM- 1:45PM", "VEC  Room 408", 5558);
        Course course3 = new Course("CECS 378", "Introduction to Computer Security Principles", "M/W 1:20PM- 2:45PM", "ECS  Room 228", 7315);
        Course course4 = new Course("CECS 475", "Software Framework", "T/Th 8:00AM- 10:15AM", "ECS  Room 302", 1165);
        Course course5 = new Course("MATH 303", "Reflections in Space and Time", "M/W 11:00AM- 12:15PM", "LA5  Room 259", 6575);

        List<Course> courseList = new ArrayList<>();
        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList.add(course4);
        courseList.add(course5);

        availableCourseRepo.addCourse(courseList, context);
    }
}