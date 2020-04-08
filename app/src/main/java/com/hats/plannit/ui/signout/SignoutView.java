package com.hats.plannit.ui.signout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hats.plannit.R;
import com.hats.plannit.ui.login.LoginViewModel;

public class SignoutView extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button btnSignOut;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signout);

        btnSignOut = findViewById(R.id.sign_out_button);

        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                // Login
//                if(user != null){
//                    // User is signed in
//                    //toastMessage("Successfully signed in with " + user.getEmail());
//                } else{
//                    // User is signed out
//                    //toastMessage("Successfully signed out.");
//                }
//            }
//        };

        btnSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                toastMessage("Signing out...");
                Intent intent = new Intent(getApplication(), LoginViewModel.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        FirebaseAuth.getInstance().signOut();
        /*
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
        */
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
