package com.hats.plannit.ui.courses;

import android.widget.CheckedTextView;

import androidx.lifecycle.ViewModelProvider;

import com.hats.plannit.models.Course;
import com.hats.plannit.models.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseAsset
{
    protected static CoursesViewModel courseViewModel;
    protected static List<Subject> availableSubjectList;
    protected static List<Course> registeredCourseList;
    protected static List<ArrayList<Map<Course, CheckedTextView>>> courseListToBeAdded;
    protected static List<ArrayList<CheckedTextView>> checkedTextViewList;
    protected static List<Course> courses;

    public static void courseInit(CoursesFragment coursesFragment)
    {
        registeredCourseList = new ArrayList<>();
        courseListToBeAdded = new ArrayList<>();
        checkedTextViewList = new ArrayList<>();
        courses = new ArrayList<>();

        courseViewModel = new ViewModelProvider(coursesFragment).get(CoursesViewModel.class);
        courseViewModel.init();

        availableSubjectList = courseViewModel.getmAvailableSubjectList().getValue();
        registeredCourseList = courseViewModel.getmRegisteredCourseList().getValue();
    }
}
