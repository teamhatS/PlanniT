package com.hats.plannit.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hats.plannit.MainActivity;
import com.hats.plannit.R;
import com.hats.plannit.ui.signup.SignUpView;

public class LoginView extends AppCompatActivity
{
    //private static final String TAG = "LoginView";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView sign_up_text;

    private EditText nEmail, nPassword;
    private Button btnLogIn;

    private CalendarView c;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        nEmail = findViewById(R.id.username_edit_text);
        nPassword = findViewById(R.id.password_edit_text);
        btnLogIn = findViewById(R.id.login_button);

        sign_up_text = findViewById(R.id.sign_up_text);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // Login
                if(user != null){
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with " + user.getEmail());
                } else{
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out:");
                    //toastMessage("Successfully signed out.");
                }
            }
        };

        btnLogIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = nEmail.getText().toString();
                String password = nPassword.getText().toString();
                if(!email.equals("") && !password.equals("")){
                    mAuth.signInWithEmailAndPassword(email, password);
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    toastMessage("Successfully signed in.");
                }else{
                    toastMessage("You didn't fill in all the fields.");
                }
            }
        });

        sign_up_text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplication(), SignUpView.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
