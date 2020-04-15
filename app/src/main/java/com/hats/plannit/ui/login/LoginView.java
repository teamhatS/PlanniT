package com.hats.plannit.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hats.plannit.MainActivity;
import com.hats.plannit.R;
import com.hats.plannit.ui.signup.SignUpView;

public class LoginView extends AppCompatActivity
{
    private static final String TAG = "LoginView";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseFirestore db;
    DocumentReference reference;

    private TextView sign_up_text;

    private EditText nEmail, nPassword;
    private Button btnLogIn;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        nEmail = findViewById(R.id.email_edit_text);
        nPassword = findViewById(R.id.password_edit_text);
        btnLogIn = findViewById(R.id.login_button);

        sign_up_text = findViewById(R.id.sign_up_text);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // Login
                if(user != null){
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with " + user.getEmail());
                } else{
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out:");
                    //toastMessage("Successfully signed out.");
                }
            }
        };

        btnLogIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                userLogin();
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

    private void userLogin(){
        final String email = nEmail.getText().toString().trim();
        String password = nPassword.getText().toString().trim();
        if(!email.equals("") && !password.equals("")){ //both fields must have something inputted
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Logging in...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginView.this, new OnCompleteListener<AuthResult>() { //checks for email and password
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                reference = db.collection("Student").document(user.getEmail());
                                reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot.exists()){
                                            progressDialog.dismiss();
                                            String username = documentSnapshot.getString("username");
                                            Intent intent = new Intent(getApplication(), MainActivity.class); //if successful it goes into app
                                            startActivity(intent);
                                            toastMessage("Welcome back " + username);
                                        }else{
                                            toastMessage("User does not exist.");
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        toastMessage("Error.");
                                    }
                                });
                            } else {
                                toastMessage("Sign in failed.");
                            }
                        }
                    });
        }else{
            toastMessage("You didn't fill in all the fields.");
        }
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
