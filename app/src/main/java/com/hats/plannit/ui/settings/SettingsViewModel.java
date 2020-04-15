package com.hats.plannit.ui.settings;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hats.plannit.models.Student;
import com.hats.plannit.repos.StudentRepo;

public class SettingsViewModel extends ViewModel {

    private static final String TAG = "SettingsViewModel";
    private MutableLiveData<Student> mLoggedInStudent;
    private Student loggedInStudent;
    private StudentRepo sRepo;

    public void init(){
        if(sRepo != null){
            return;
        }
        sRepo = StudentRepo.getInstance();
        loggedInStudent = sRepo.getLoggedInStudent();
        mLoggedInStudent.setValue(loggedInStudent);

    }

    public void updateStudentAccount(Context context, Student student){
        sRepo.updateStudentAccount(context, student);
    }

    public MutableLiveData<Student> getmLoggedInStudent() {
        return mLoggedInStudent;
    }

}