package com.hats.plannit.ui.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.hats.plannit.R;
import java.util.Calendar;

/*
@author- Howard Chen
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private FloatingActionButton addAssignmentFab;
    private TextView dueDateTextView;
    private TextView dueTimeTextView;
    private TextInputLayout textInputCourseName;
    private TextInputLayout textInputAssignmentName;
    private TextInputLayout textInputAssignmentDesc;
    private ImageView dueDateIconImageView;
    private ImageView dueTimeIconImageView;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    Dialog myDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        addAssignmentFab = root.findViewById(R.id.fab_add_assignment);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        myDialog = new Dialog(getContext());

        addAssignmentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAssignmentPopup(v);
            }
        });


        return root;
    }

    private void showAddAssignmentPopup(View v) {
        myDialog.setContentView(R.layout.fragment_add_assignments);
        dueDateIconImageView = (ImageView) myDialog.findViewById(R.id.iv_due_date_icon);
        dueDateTextView = (TextView) myDialog.findViewById(R.id.tv_due_date);
        dueTimeIconImageView = (ImageView) myDialog.findViewById(R.id.iv_due_time_icon);
        dueTimeTextView = (TextView) myDialog.findViewById(R.id.tv_due_time);
        dueDateIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerPopup(v);
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: " + year + "/");
                dueDateTextView.setText(year + "/" + month + "/" + dayOfMonth );
            }
        };

        dueTimeIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerPopup(v);
            }
        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d(TAG, "onDateSet: " + minute + "/");
                dueTimeTextView.setText(hourOfDay + ":" + minute);
            }
        };

        myDialog.show();
    }

    private void showDatePickerPopup(View v){
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        Integer month = cal.get(Calendar.MONTH);
        Integer day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                android.R.style.Theme_Material_Dialog_MinWidth, onDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        dialog.show();
    }
    
    private void showTimePickerPopup(View v){
        Calendar cal = Calendar.getInstance();
        Integer hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        Integer minute = cal.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(getContext(),
                android.R.style.Theme_Material_Dialog_MinWidth, onTimeSetListener,
                hourOfDay, minute, true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        dialog.show();
    }


}