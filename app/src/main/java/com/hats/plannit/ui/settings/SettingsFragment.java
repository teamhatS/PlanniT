package com.hats.plannit.ui.settings;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;
import com.hats.plannit.models.Student;

import org.w3c.dom.Text;

import java.util.List;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private Button buttonAccount;
    private Button btnAbout;
    private Button buttonNotification;
    private EditText editTextNickname;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextEmail;
    private Button submitAccountSettingsButton;
    private Student loggedInStudent;
    private String studentNickname;
    private String studentEmail;

    Dialog myDialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        buttonAccount = root.findViewById(R.id.btn_account_settings);
        buttonNotification = root.findViewById(R.id.btn_notification);
        btnAbout = root.findViewById(R.id.btn_about);

        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        settingsViewModel.init();

        settingsViewModel.getMStudentList().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                if(students.size() > 0) {
                    for (Student student : students) {
                        if (student.getLoggedIn()) {
                            editTextNickname.setText(student.getNickname());
                            editTextEmail.setText(student.getEmail());
                        }
                    }
                }
            }
        });

        settingsViewModel.getmLoggedInStudent().observe(getViewLifecycleOwner(), new Observer<Student>() {
            @Override
            public void onChanged(Student student) {

            }
        });

        myDialog = new Dialog(getContext());

        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccountPopup(v);
            }
        });

//        btnAbout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationPopup(v);
            }
        });


        return root;

    }

    public void showAccountPopup(View v){
        myDialog.setContentView(R.layout.fragment_account_settings);
        editTextNickname = myDialog.findViewById(R.id.et_settings_nickname);
        editTextEmail = myDialog.findViewById(R.id.et_settings_email);
        editTextPassword = myDialog.findViewById(R.id.et_settings_password);
        editTextConfirmPassword = myDialog.findViewById(R.id.et_settings_confirm_password);
        submitAccountSettingsButton = myDialog.findViewById(R.id.btn_account_settings_submit);
        submitAccountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:: store changes into viewmodel repo.
                if(!editTextNickname.getText().toString().equals("")){

                }
                if(!editTextConfirmPassword.getText().toString().equals("")){

                }
            }
        });
        myDialog.show();
    }

    public void showNotificationPopup(View v){
        TextView exitText;
        myDialog.setContentView(R.layout.fragment_notification_settings);
        exitText = (TextView) myDialog.findViewById(R.id.tv_exit);
        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void showSoundPopup(View v){
        TextView exitText;
        myDialog.setContentView(R.layout.fragment_sound_settings);
        exitText = (TextView) myDialog.findViewById(R.id.tv_exit);
        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
}