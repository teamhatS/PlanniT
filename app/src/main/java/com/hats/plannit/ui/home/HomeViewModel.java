package com.hats.plannit.ui.home;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hats.plannit.models.Assignment;
import com.hats.plannit.repos.AssignmentRepo;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private MutableLiveData<List<Assignment>> mAssignmentList;
    private AssignmentRepo aRepo;

    public void init(){
        if(aRepo != null){
            return;
        }
        aRepo = AssignmentRepo.getInstance();
        mAssignmentList = aRepo.getAssignments();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<Assignment> currentAssignments = mAssignmentList.getValue();
                mAssignmentList.postValue(currentAssignments);

            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

    public boolean addAssignment(Assignment newAssignment, Context context){
        return aRepo.addAssignment(newAssignment, context);
    }
    public MutableLiveData<List<Assignment>> getmAssignmentList() {
        return mAssignmentList;
    }

}