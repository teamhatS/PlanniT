package com.hats.plannit.ui.settings;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hats.plannit.models.Assignment;
import com.hats.plannit.models.Student;
import com.hats.plannit.repos.AssignmentRepo;
import com.hats.plannit.repos.StudentRepo;

import java.util.List;

public class SettingsViewModel extends ViewModel {

    private static final String TAG = "SettingsViewModel";
    private MutableLiveData<List<Student>> mStudentList;
    private MutableLiveData<Student> mLoggedInStudent;
    private Student loggedInStudent;
    private StudentRepo sRepo;


    public void init(){
        if(sRepo != null){
            return;
        }
        sRepo = StudentRepo.getInstance();
        mStudentList = sRepo.getStudents();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<Student> currentStudents = mStudentList.getValue();
                mStudentList.postValue(currentStudents);

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

        setmLoggedInStudent();
    }

    public void setmLoggedInStudent(){
        mLoggedInStudent = new MutableLiveData<Student>();
        List<Student> studentList = mStudentList.getValue();
        Student loggedInStudent = null;
        for(Student student : studentList){
            if(student.getLoggedIn()){
                loggedInStudent = student;
            }
        }
        mLoggedInStudent.setValue(loggedInStudent);
    }
    public void updateStudentAccount(Context context, Student student){
        sRepo.updateStudentAccount(context, student);
    }

    public MutableLiveData<Student> getmLoggedInStudent() {
        return mLoggedInStudent;
    }

    public MutableLiveData<List<Student>> getMStudentList() {
        return mStudentList;
    }

}