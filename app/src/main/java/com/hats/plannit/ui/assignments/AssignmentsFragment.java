package com.hats.plannit.ui.assignments;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;
import com.hats.plannit.models.Course;
import com.hats.plannit.ui.courses.CoursesViewModel;
import com.hats.plannit.ui.login.LoginView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
@author- Howard Chen
 */

public class AssignmentsFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private AssignmentsViewModel assignmentsViewModel;
    private  CoursesViewModel courseViewModel;
    private String courseSelected = "";
    private RecyclerView homeRecyclerView;
    private AssignmentsAdapter assignmentsAdapter;
    private FloatingActionButton addAssignmentFab;
    private List<Course> registeredCourses;
    private Button submitButton;
    private Button backButton;
    private TextView dueDateTextView;
    private TextView dueTimeTextView;
    private TextInputLayout textInputAssignmentName;
    private TextInputLayout textInputAssignmentDesc;
    private ImageView dueDateIconImageView;
    private ImageView dueTimeIconImageView;
    private String date, time;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Spinner spinnerRegisteredCourses;
    Dialog myDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        assignmentsViewModel = new ViewModelProvider(this).get(AssignmentsViewModel.class);
        courseViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);
        courseViewModel.init();
        assignmentsViewModel.init();
        courseViewModel.getmRegisteredCourseList().observe(getViewLifecycleOwner(), new Observer<List<Course>>()
        {
            @Override
            public void onChanged(List<Course> courseList)
            {
                registeredCourses = courseList;
            }
        });
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeRecyclerView = root.findViewById(R.id.rv_assignment);
        final TextView textView = root.findViewById(R.id.text_home);
        addAssignmentFab = root.findViewById(R.id.fab_add_assignment);
        assignmentsViewModel.getmAssignmentList().observe(getViewLifecycleOwner(), new Observer<List<Assignment>>() {
                    @Override
                    public void onChanged(List<Assignment> assignments) {
                        assignmentsAdapter.notifyDataSetChanged();
                    }
        });

        myDialog = new Dialog(getContext());

        addAssignmentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
                showAddAssignmentPopup(v);
            }
        });
        initRecyclerViews();


        return root;
    }

    private void showAddAssignmentPopup(View v) {

        Boolean complete, expanded;

        myDialog.setContentView(R.layout.fragment_add_assignments);
        spinnerRegisteredCourses = myDialog.findViewById(R.id.spinner_courses);
        ArrayList<String> courseStrings = new ArrayList<>();
        for(Course course : registeredCourses ){
            courseStrings.add(course.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, courseStrings);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinnerRegisteredCourses.setAdapter(adapter);
        spinnerRegisteredCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoginView.mp.start();
                courseSelected = registeredCourses.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                courseSelected = "";
            }
        });

        backButton = (Button) myDialog.findViewById(R.id.btn_add_assignment_back);
        textInputAssignmentName = (TextInputLayout) myDialog.findViewById(R.id.et_assignment_name);
        textInputAssignmentDesc = (TextInputLayout) myDialog.findViewById(R.id.et_assignment_description);
        dueDateIconImageView = (ImageView) myDialog.findViewById(R.id.iv_due_date_icon);
        dueDateTextView = (TextView) myDialog.findViewById(R.id.tv_due_date);
        dueTimeIconImageView = (ImageView) myDialog.findViewById(R.id.iv_due_time_icon);
        dueTimeTextView = (TextView) myDialog.findViewById(R.id.tv_due_time);
        submitButton = (Button) myDialog.findViewById(R.id.btn_add_assignment_submit);
        backButton = (Button) myDialog.findViewById(R.id.btn_add_assignment_back);
        dueDateIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
                showDatePickerPopup(v);
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LoginView.mp.start();
                Log.d(TAG, "onDateSet: " + year + "/");
                date = year + "/" +(month + 1) + "/" + dayOfMonth;
                dueDateTextView.setText(date);
            }
        };

        dueTimeIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
                showTimePickerPopup(v);
            }
        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                LoginView.mp.start();
                Log.d(TAG, "onDateSet: " + minute + "/");
                time = hourOfDay + ":" + minute;
                dueTimeTextView.setText(time);
            }
        };


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.mp.start();
                String courseName =  courseSelected;
                String assignmentName = textInputAssignmentName.getEditText().getText().toString();
                String description = textInputAssignmentDesc.getEditText().getText().toString();
                Assignment newAssignment = new Assignment(courseName, assignmentName,
                                                            date, time, description,
                                                            false, false );

                //hardcoded email for now
                assignmentsViewModel.setReminder(getContext(), "Plannit2020@gmail.com", newAssignment);
                boolean added = assignmentsViewModel.addAssignment(newAssignment, getContext());
                if(added){
                    clearFields();
                }
            }
        });

        myDialog.show(); //could move to the top above listeners.

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginView.mp.start();
                myDialog.dismiss();
            }
        });
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

    private void clearFields(){
        dueDateTextView.setText("Due Date");
        dueTimeTextView.setText("Due Time");
        textInputAssignmentName.getEditText().setText("");
        textInputAssignmentDesc.getEditText().setText("");
        spinnerRegisteredCourses.setSelection(0);
    }

    private void initRecyclerViews(){
        //Assignments
        assignmentsAdapter = new AssignmentsAdapter(getActivity(), assignmentsViewModel.getmAssignmentList().getValue(), assignmentsViewModel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerView.setLayoutManager(layoutManager);
        homeRecyclerView.setAdapter(assignmentsAdapter);

    }
}