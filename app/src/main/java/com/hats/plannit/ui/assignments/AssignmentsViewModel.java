package com.hats.plannit.ui.assignments;
/**
 * @authot: Howard Chen
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hats.plannit.models.Assignment;
import com.hats.plannit.notifications.AlertReceiver;
import com.hats.plannit.repos.AssignmentRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class AssignmentsViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private MutableLiveData<List<Assignment>> mAssignmentList;
    private AssignmentRepo aRepo;

    public void init(){
        if(aRepo != null){
            return;
        }
        aRepo = AssignmentRepo.getInstance();
        mAssignmentList = aRepo.getAssignments();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<Assignment> currentAssignments = mAssignmentList.getValue();
                mAssignmentList.postValue(currentAssignments);

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

    }

    public boolean addAssignment(Assignment newAssignment, Context context){
        return aRepo.addAssignment(newAssignment, context);
    }
    public MutableLiveData<List<Assignment>> getmAssignmentList() {
        return mAssignmentList;
    }

    public void delAssignment(Assignment assignmentToDel, Context context){
        aRepo.delAssignment(assignmentToDel, context);
    }

    public void completeAssignment(Assignment assignment, Context context, Boolean isChecked){
        aRepo.completeAssignment(assignment, context, isChecked);
    }

    public void setReminder(final Context context, final String email, final Assignment newAssignment)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    Intent intent = new Intent(context, AlertReceiver.class);
                    intent.putExtra("receiver", email);
                    intent.putExtra("course", newAssignment.getCourseName());
                    intent.putExtra("assignment", newAssignment.getAssignmentName());
                    intent.putExtra("date", newAssignment.getDate());
                    intent.putExtra("time", newAssignment.getTime());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)System.currentTimeMillis(), intent, 0);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

                    String dateAndTime = newAssignment.getDate() + " " + newAssignment.getTime() + ":00";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(dateAndTime);

                    long dateAndTimeInMillis = date.getTime();
                    long currentTimeInMillis = System.currentTimeMillis();
                    long oneDayInMillis = 86400000;
                    long oneHourInMillis = 3600000;

                    if(dateAndTimeInMillis - currentTimeInMillis > oneDayInMillis)
                    {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, dateAndTimeInMillis - oneDayInMillis, pendingIntent);
                    }
                    else if(dateAndTimeInMillis - currentTimeInMillis > oneHourInMillis)
                    {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, dateAndTimeInMillis - oneHourInMillis, pendingIntent);
                    }
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}