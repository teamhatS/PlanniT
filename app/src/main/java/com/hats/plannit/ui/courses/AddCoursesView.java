package com.hats.plannit.ui.courses;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;

import java.util.ArrayList;
import java.util.List;

public class AddCoursesView extends AppCompatActivity
{
    private Button addCoursesButton;
    private Button backButton;
    private ListView addCoursesListView;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_courses_view);

        this.getWindow().setLayout(1350, 1900);

        addCoursesButton = findViewById(R.id.add_courses_button);
        backButton = findViewById(R.id.back_button);
        addCoursesListView = findViewById(R.id.add_courses_list_view);

        final AvailableCourseListViewAdapter adapter = new AvailableCourseListViewAdapter(AddCoursesView.this, 0, CourseAsset.availableCourseList);
        addCoursesListView.setAdapter(adapter);

        addCoursesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!CourseAsset.courseListToBeAdded.isEmpty())
                {
                    List<Course> duplicateCourseList = CourseAsset.courseViewModel.addCourses(CourseAsset.courseListToBeAdded, CourseAsset.registeredCourseList, getApplicationContext());
                    if(!duplicateCourseList.isEmpty())
                    {
                        showDuplicateCourses(adapter, duplicateCourseList);
                        Toast.makeText(getApplication(), "Courses in red are already in your course list!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        CourseAsset.courseListToBeAdded = new ArrayList<>();
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
                CourseAsset.courseListToBeAdded = new ArrayList<>();
                finish();
            }
        });
    }

    public void showDuplicateCourses(AvailableCourseListViewAdapter adapter, List<Course> duplicateCourseList)
    {
        for(Course course: duplicateCourseList)
        {
            adapter.getChildAt(course).setTextColor(getResources().getColor(R.color.red));
        }
    }
}
