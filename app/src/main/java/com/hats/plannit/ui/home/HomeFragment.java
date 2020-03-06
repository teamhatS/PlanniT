package com.hats.plannit.ui.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hats.plannit.R;


import java.util.Calendar;

/*
@author- Howard Chen
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private FloatingActionButton addAssignmentFab;
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
        ImageView dueDateIconImageView;
        final TextView  dueDateTextView;
        myDialog.setContentView(R.layout.fragment_add_assignments);
        dueDateIconImageView = (ImageView) myDialog.findViewById(R.id.iv_due_date_icon);
        dueDateTextView = (TextView) myDialog.findViewById(R.id.tv_due_date);
        dueDateIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Integer year = cal.get(Calendar.YEAR);
                Integer month = cal.get(Calendar.MONTH);
                Integer day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Material_Dialog_MinWidth, onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: " + year + "/");
                dueDateTextView.setText(year + "/" + month + "/" + dayOfMonth );


            }

        };

        myDialog.show();
    }

    private void showDatePickerPopup(View v){

    }

}