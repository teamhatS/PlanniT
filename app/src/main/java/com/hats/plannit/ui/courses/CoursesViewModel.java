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

    public void addCourses(ArrayList<Course> availableCourseList, ArrayList<Integer> selectedItems)
    {
        for(int i: selectedItems)
        {
            CoursesFragment.coursesList.add(availableCourseList.get(i));
        }
    }
}