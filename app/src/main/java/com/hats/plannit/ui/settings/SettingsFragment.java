package com.hats.plannit.ui.settings;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.hats.plannit.R;
import com.hats.plannit.models.Student;

import java.util.List;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private Button buttonAccount;
    private Button btnAbout;
    private Button buttonNotification;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextEmail;
    private TextInputLayout editTextLayoutUsername;
    private TextInputLayout editTextLayoutPassword;
    private TextInputLayout editTextLayoutConfirmPassword;
    private TextInputLayout editTextLayoutEmail;
    private Button backAccountSettingsButton;
    private Button submitAccountSettingsButton;
    private Student loggedInStudent;
    private Button btnContact;


    Dialog myDialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        buttonAccount = root.findViewById(R.id.btn_account_settings);
        buttonNotification = root.findViewById(R.id.btn_notification);
        btnAbout = root.findViewById(R.id.btn_about);
        btnContact = root.findViewById(R.id.btn_contact);

        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        settingsViewModel.init();

        settingsViewModel.getMStudentList().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                if(students.size() > 0) {
                    for (Student student : students) {
                        if (student.getLoggedIn()) {
                            loggedInStudent = student;
                            editTextLayoutUsername.getEditText().setText(student.getUsername());
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

        buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationPopup(v);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAboutPopup(v);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactPopup(v);
            }
        });

        return root;

    }

    public void showAccountPopup(View v){
        myDialog.setContentView(R.layout.fragment_account_settings);
        editTextLayoutUsername = myDialog.findViewById(R.id.til_settings_username);
        editTextLayoutConfirmPassword = myDialog.findViewById(R.id.til_settings_confirm_password);
        editTextLayoutPassword = myDialog.findViewById(R.id.til_settings_password);
        editTextLayoutEmail= myDialog.findViewById(R.id.til_settings_email);

        editTextUsername = myDialog.findViewById(R.id.et_settings_username);
        editTextEmail = myDialog.findViewById(R.id.et_settings_email);
        editTextPassword = myDialog.findViewById(R.id.et_settings_password);
        editTextConfirmPassword = myDialog.findViewById(R.id.et_settings_confirm_password);
        submitAccountSettingsButton = myDialog.findViewById(R.id.btn_account_settings_submit);
        backAccountSettingsButton = myDialog.findViewById(R.id.btn_account_settings_back);
        submitAccountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextLayoutUsername.getEditText().getText().toString().trim();
                String password = editTextLayoutPassword.getEditText().getText().toString();
                String passwordConfirm = editTextLayoutConfirmPassword.getEditText().getText().toString();
                String emailInput = editTextLayoutEmail.getEditText().getText().toString().trim();

                boolean validatedInputs = validateInput(v, userName , password, passwordConfirm, emailInput ); //validate inputs
                if(validatedInputs){
                    loggedInStudent.setUsername(userName);
                    loggedInStudent.setEmail(emailInput);
                    loggedInStudent.setPassword(password);
                    settingsViewModel.updateStudentAccount(getContext(), loggedInStudent);
                }


            }
        });
        myDialog.show();

        backAccountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
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


    public void showAboutPopup(View v){
        myDialog.setContentView(R.layout.fragment_about_settings);
        myDialog.show();
    }

    public void showContactPopup(View v){
        myDialog.setContentView(R.layout.fragment_contact_settings);
        myDialog.show();
    }


    public boolean validateInput(View v, String username, String password, String passwordConfirm, String email) {
        if(!validateUsername(username) | !validatePassword(password, passwordConfirm) | !validateEmail(email)) {
            return false;
        }
        return true;
    }
    private boolean validateUsername(String username) {

        if(username.isEmpty()) {
            editTextLayoutUsername.setError("Field can't be empty");
            return false;
        } else {
            editTextLayoutUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String password, String passwordConfirm) {

        if(password.isEmpty()) {
            editTextLayoutPassword.setError("Field can't be empty");
            return false;
        } else if (!password.equals(passwordConfirm)){
            editTextLayoutPassword.setError("Passwords should match!");
            editTextLayoutConfirmPassword.setError("Passwords should match!");
            return false;
        }else if(password.length() != 10 && passwordConfirm.length() != 10){
            editTextLayoutPassword.setError("Password needs to be 10 characters long");
            editTextConfirmPassword.setError("Password needs to be 10 characters long");
            return false;
        }else {
            editTextLayoutPassword.setError(null);
            editTextLayoutConfirmPassword.setError(null);
            return true;
        }
    }

    private boolean validateEmail(String emailInput) {
        if(emailInput.isEmpty()) {
            editTextLayoutEmail.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            editTextLayoutEmail.setError("Invalid email address");
            return false;
        } else {
            editTextLayoutEmail.setError(null);
            return true;
        }
    }



}