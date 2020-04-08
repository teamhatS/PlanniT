package com.hats.plannit.ui.search;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hats.plannit.models.Assignment;
import com.hats.plannit.models.Course;
import com.hats.plannit.repos.AssignmentRepo;
import com.hats.plannit.repos.AvailableCourseRepo;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Assignment>> mAssignmentList;
    private AssignmentRepo aRepo;
    private MutableLiveData<List<Course>> mRegisteredCourseList;
    private AvailableCourseRepo availableCourseRepo;

    public void init(){
        if(aRepo != null){
            return;
        }
        aRepo = AssignmentRepo.getInstance();
        mAssignmentList = aRepo.getAssignments();

        if(availableCourseRepo != null) {
            return;
        }
        availableCourseRepo = AvailableCourseRepo.getInstance();
        mRegisteredCourseList = availableCourseRepo.getRegisteredCourses();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<Assignment> currentAssignments = mAssignmentList.getValue();
                mAssignmentList.postValue(currentAssignments);
                List<Course> currentCourses = mRegisteredCourseList.getValue();
                mRegisteredCourseList.postValue(currentCourses);

            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

    public MutableLiveData<List<Assignment>> getmAssignmentList() {
        return mAssignmentList;
    }

    public MutableLiveData<List<Course>> getmRegisteredCourseList()
    {
        return mRegisteredCourseList;
    }
}