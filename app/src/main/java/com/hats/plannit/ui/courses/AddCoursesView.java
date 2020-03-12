package com.hats.plannit.ui.courses;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;

import java.util.ArrayList;

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

//        ArrayList<String> coursesToString = objectToString();
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.courses_view, R.id.new_course, coursesToString);
//        addCoursesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        addCoursesListView.setAdapter(arrayAdapter);
//
//        final ArrayList<Integer> selectedItems = new ArrayList<>();
//        final AdapterView<?>[] adapter = {null};
//        addCoursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                if(adapter[0] == null)
//                {
//                    adapter[0] = adapterView;
//                }
//
//                if(selectedItems.contains(position))
//                {
//                    adapterView.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.silver));
//                    selectedItems.remove(new Integer(position));
//                }
//                else
//                {
//                    selectedItems.add(position);
//                }
//            }
//        });
//
        addCoursesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!CourseAsset.courseListToBeAdded.isEmpty())
                {
                    //ArrayList<Integer> duplicateCourseList = CoursesFragment.courseViewModel.addCourses((ArrayList<Course>)CoursesFragment.availableCourseList, selectedItems);
                    ArrayList<Integer> duplicateCourseList = CourseAsset.courseViewModel.addCourses(CourseAsset.availableCourseList, CourseAsset.courseListToBeAdded);
                    if(!duplicateCourseList.isEmpty())
                    {
                        showDuplicateCourses(adapter, duplicateCourseList);
                        Toast.makeText(getApplication(), "Courses in red are already in your course list!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(getApplication(), "Please choose courses to add!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void showDuplicateCourses(AvailableCourseListViewAdapter adapterView, ArrayList<Integer> duplicateCourseList)
    {
        for(int position: duplicateCourseList)
        {
            adapterView.getChildAt(position).setBackgroundColor(Color.RED);
        }
    }

    private ArrayList<String> objectToString()
    {
        ArrayList<String> coursesToString = new ArrayList<>();
        for(Course course: CourseAsset.availableCourseList)
        {
            coursesToString.add(course.toString());
        }
        return coursesToString;
    }
}
