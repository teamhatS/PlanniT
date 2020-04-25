package com.hats.plannit.ui.courses;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.hats.plannit.R;
import com.hats.plannit.ui.login.LoginView;

import java.util.ArrayList;
import java.util.List;

public class AddCoursesView extends AppCompatActivity
{
    private Button addCoursesButton;
    private Button backButton;
    private ExpandableListView addCoursesListView;

    private ExpandableListAdapter expandableListAdapter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_courses_view);

        this.getWindow().setLayout(1350, 1900);

        addCoursesButton = findViewById(R.id.add_courses_button);
        backButton = findViewById(R.id.back_button);
        addCoursesListView = findViewById(R.id.add_courses_list_view);

        expandableListAdapter = new AvailableCourseListViewAdapter(this, CourseAsset.availableSubjectList);
        addCoursesListView.setAdapter(expandableListAdapter);

        addCoursesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LoginView.mp.start();
                if(!CourseAsset.courses.isEmpty())
                {
                    List<CheckedTextView> duplicateCourseList = CourseAsset.courseViewModel.addCourses(CourseAsset.courseListToBeAdded, CourseAsset.registeredCourseList, CourseAsset.checkedTextViewList, getApplicationContext());
                    if(!duplicateCourseList.isEmpty())
                    {
                        showDuplicateCourses(duplicateCourseList);
                        Toast.makeText(getApplication(), "Courses in red are already in your course list!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        CourseAsset.courseListToBeAdded = new ArrayList<>();
                        CourseAsset.checkedTextViewList = new ArrayList<>();
                        CourseAsset.courses = new ArrayList<>();
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(getApplication(), "Please choose courses to add!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LoginView.mp.start();
                CourseAsset.courseListToBeAdded = new ArrayList<>();
                CourseAsset.checkedTextViewList = new ArrayList<>();
                CourseAsset.courses = new ArrayList<>();
                finish();
            }
        });
    }

    public void showDuplicateCourses(List<CheckedTextView> duplicateCourseList)
    {
        for(int i = 0; i < duplicateCourseList.size(); i++)
        {
            duplicateCourseList.get(i).setTextColor(getResources().getColor(R.color.red));
        }
    }
}
