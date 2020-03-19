package com.hats.plannit.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlertReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, final Intent intent)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String receiver = intent.getStringExtra("receiver");
                    String course = intent.getStringExtra("course");
                    String assignment = intent.getStringExtra("assignment");
                    String date = intent.getStringExtra("date");
                    String time = intent.getStringExtra("time");
                    String subject = course + " Reminder";
                    String message = course + " - \"" + assignment + "\" assignment is due on " + date + " at " + time;
                    EMailSender sender = new EMailSender(receiver, subject, message);
                    sender.execute();
                }
                catch (Exception e)
                {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        }).start();
    }
}
