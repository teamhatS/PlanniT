package com.hats.plannit.ui.login;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hats.plannit.R;
import com.hats.plannit.ui.signup.SignUpView;

public class LoginView extends AppCompatActivity
{
    private TextView sign_up_text;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

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
}
