package com.hats.plannit.ui.courses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hats.plannit.models.Course;

import java.util.ArrayList;

/*
 * author: Howard chen
 */

public class CoursesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CoursesViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<Integer> addCourses(ArrayList<Course> availableCourseList, ArrayList<Integer> selectedItems)
    {
        ArrayList<Integer> duplicateCourses = new ArrayList<>();
        ArrayList<Course> courseListTemp = new ArrayList<>();

        for(int i: selectedItems)
        {
            if(CoursesFragment.coursesList.contains(availableCourseList.get(i)))
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
            CoursesFragment.coursesList.addAll(courseListTemp);
        }
        return duplicateCourses;
    }
}