package com.hats.plannit.ui.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hats.plannit.MainActivity;
import com.hats.plannit.R;
import com.hats.plannit.ui.login.LoginViewModel;

import java.util.HashMap;
import java.util.Map;

public class SignUpViewModel extends AppCompatActivity
{

    private static final String TAG = "SignUpViewModel";

    private ProgressDialog pDialog;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    DocumentReference reference;

    EditText userStudentId, userPassword, userConfirmedPassword, userEmail, userUsername;
    Button btnSignUp, btnBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userStudentId = findViewById(R.id.studentID_edit_text_signup);
        userPassword = findViewById(R.id.password_edit_text_signup);
        userConfirmedPassword = findViewById(R.id.confirm_password_edit_text_signup);
        userEmail = findViewById(R.id.email_edit_text_signup);
        userUsername = findViewById(R.id.username_edit_text_signup);

        btnSignUp = findViewById(R.id.sign_up_button);

        btnBack = findViewById(R.id.return_text);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //back button
                Intent intent = new Intent(getApplication(), LoginViewModel.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startUserRegistration();
            }
        });
    }

    private void startUserRegistration(){
        final String studentId = userStudentId.getText().toString().trim();
        final String password = userPassword.getText().toString().trim();
        String confirmedPassword = userConfirmedPassword.getText().toString().trim();
        final String email = userEmail.getText().toString().trim();
        final String username = userUsername.getText().toString().trim();
        if(studentId.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty() || email.isEmpty() || username.isEmpty()){
            toastMessage("You didn't fill in all the fields.");
        }else if(studentId.length() < 9 || studentId.length() > 10){
            toastMessage("Your student id must be 9 or 10 digits.");
        }else if(password.length() < 6){
            toastMessage("Password must be at least 6 characters.");
        }else if(!password.equals(confirmedPassword)){
            toastMessage("Your password doesn't match.");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ //valid email address
            toastMessage("Please enter a valid email address.");
        }else{
            final ProgressDialog progressDialog = new ProgressDialog(this); //essential a progress bar
            progressDialog.setMessage("Registering...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = authResult.getUser();
                    reference = db.collection("Student").document(user.getEmail()); //the unique identifier for the collection
                    Map<String, String> userData = new HashMap<>();
                    userData.put("studentId", studentId);
                    userData.put("password", password);
                    userData.put("username", username);
                    progressDialog.setMessage("Saving user data...");
                    reference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            toastMessage("Successfully registered " + email);
                            finish();
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            toastMessage("User signed up but unable to save student id.");
                            e.printStackTrace();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    toastMessage("Unable to register user.");
                    e.printStackTrace();
                }
            });
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
