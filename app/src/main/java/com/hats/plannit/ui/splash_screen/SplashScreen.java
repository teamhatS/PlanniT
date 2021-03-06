package com.hats.plannit.ui.splash_screen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hats.plannit.ui.courses.CoursesFragment;
import com.hats.plannit.ui.login.LoginView;

public class SplashScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable()
        {
            public void run()
            {
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplication(), LoginView.class);
                startActivity(intent);

                finish();
            }
        }).start();
    }
}
