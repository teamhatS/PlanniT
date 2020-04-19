package com.hats.plannit.ui.settings;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;
import com.hats.plannit.models.Student;
import com.hats.plannit.ui.login.LoginView;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";
    private SettingsViewModel settingsViewModel;
    private Button buttonAccount;
    private Button btnAbout;
    private Button buttonNotification;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    //private EditText editTextEmail;
    private TextInputLayout editTextLayoutUsername;
    private TextInputLayout editTextLayoutPassword;
    private TextInputLayout editTextLayoutConfirmPassword;
   // private TextInputLayout editTextLayoutEmail;
    private Button backAccountSettingsButton;
    private Button submitAccountSettingsButton;
    private Button btnContact;
    private String oldPw;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
   //private String currentUserEmail;
    //private List<Student> dataSet = new ArrayList<>();
    private static Student loggedInStudent; // the student logged in;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference studentRef = db.collection("Student")
            .document(currentUser.getEmail());


    Dialog myDialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loggedInStudent = getLoggedInStudent();

        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        buttonAccount = root.findViewById(R.id.btn_account_settings);
        buttonNotification = root.findViewById(R.id.btn_notification);
        btnAbout = root.findViewById(R.id.btn_about);
        btnContact = root.findViewById(R.id.btn_contact);

        myDialog = new Dialog(getContext());

        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
                showAccountPopup(v);
            }
        });

        buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
                showNotificationPopup(v);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
                showAboutPopup(v);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
                showContactPopup(v);
            }
        });

        return root;

    }

    public void showAccountPopup(View v){
        myDialog.setContentView(R.layout.fragment_account_settings);
        editTextLayoutConfirmPassword = myDialog.findViewById(R.id.til_settings_confirm_password);
        editTextLayoutPassword = myDialog.findViewById(R.id.til_settings_password);
        //editTextLayoutEmail= myDialog.findViewById(R.id.til_settings_email);
        editTextLayoutUsername = myDialog.findViewById(R.id.til_settings_username);

        editTextUsername = myDialog.findViewById(R.id.et_settings_username);
        //editTextEmail = myDialog.findViewById(R.id.et_settings_email);
        editTextPassword = myDialog.findViewById(R.id.et_settings_password);
        editTextConfirmPassword = myDialog.findViewById(R.id.et_settings_confirm_password);
        submitAccountSettingsButton = myDialog.findViewById(R.id.btn_account_settings_submit);
        backAccountSettingsButton = myDialog.findViewById(R.id.btn_account_settings_back);

        //editTextEmail.setText(loggedInStudent.getEmail());
        editTextUsername.setText(loggedInStudent.getUsername());
        submitAccountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
                String userName = editTextLayoutUsername.getEditText().getText().toString().trim();
                String password = editTextLayoutPassword.getEditText().getText().toString();
                String passwordConfirm = editTextLayoutConfirmPassword.getEditText().getText().toString();
               // String emailInput = editTextLayoutEmail.getEditText().getText().toString().trim();

                boolean validatedInputs = validateInput(v, userName , password, passwordConfirm ); //validate inputs
                if(validatedInputs){
                    loggedInStudent.setUsername(userName);
                  //  loggedInStudent.setEmail(emailInput);
                    oldPw = loggedInStudent.getPassword();
                    loggedInStudent.setPassword(password);
                    updateAccountsettings(loggedInStudent);
                }


            }
        });
        myDialog.show();

        backAccountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
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


    public boolean validateInput(View v, String username, String password, String passwordConfirm) {
        if(!validateUsername(username) | !validatePassword(password, passwordConfirm) ) {
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
        }else if(password.length() < 6 && passwordConfirm.length() < 6 ){
            editTextLayoutPassword.setError("Password needs to be 10 characters long");
            editTextConfirmPassword.setError("Password needs to be 10 characters long");
            return false;
        }else {
            editTextLayoutPassword.setError(null);
            editTextLayoutConfirmPassword.setError(null);
            return true;
        }
    }


    private Student getLoggedInStudent(){
//        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();
//        studentRef = db.collection("Student")
//                .document(currentUser.getEmail());
        studentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot){
                Log.d(TAG, "onSuccess: went into document getter");
                Student student = documentSnapshot.toObject(Student.class);
                Log.d(TAG, "onSuccess: " + student.getEmail());
                loggedInStudent = student;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d(TAG, "getLoggedInStudent: " + loggedInStudent.getEmail());

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

        return loggedInStudent;
    }

    private void updateAccountsettings(final Student student){
        studentRef.set(student)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Student Account Updated.", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString() );

            }
        });

        AuthCredential credential = EmailAuthProvider
                .getCredential(loggedInStudent.getEmail(), oldPw);

        currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    currentUser.updatePassword(student.getPassword()).addOnCompleteListener
                            (new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "password updated");
                            }else{
                                Log.d(TAG, "Error password not updated ");
                            }
                        }
                    });
                }
            }
        });
    }

}