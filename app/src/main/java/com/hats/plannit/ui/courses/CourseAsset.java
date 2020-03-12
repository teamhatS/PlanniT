package com.hats.plannit.ui.courses;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.hats.plannit.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseAsset
{
    protected static CoursesViewModel courseViewModel;
    protected static List<Course> availableCourseList;
    protected static List<Course> registeredCourseList;
    protected static List<Course> courseListToBeAdded;

    public static void courseInit(CoursesFragment coursesFragment)
    {
        registeredCourseList = new ArrayList<>();
        courseListToBeAdded = new ArrayList<>();

        courseViewModel = new ViewModelProvider(coursesFragment).get(CoursesViewModel.class);
        courseViewModel.init();
        //coursesViewModel.addAllAvailableCourses(getContext());
        availableCourseList = CourseAsset.courseViewModel.getmAvailableCourseList().getValue();
    }
}
