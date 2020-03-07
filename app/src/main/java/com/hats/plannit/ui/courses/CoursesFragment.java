package com.hats.plannit.ui.courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;
import com.hats.plannit.ui.calendar.CalendarViewModel;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * author: Howard chen
 */

public class CoursesFragment extends Fragment {

    private CoursesViewModel coursesViewModel;
    protected static ArrayList<Course> coursesList = new ArrayList<>();

    private Button addCoursesButton;
    private RecyclerView customersRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //coursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_courses, container, false);
        customersRecyclerView =  root.findViewById(R.id.rv_courses);
        addCoursesButton = root.findViewById(R.id.add_courses_button);

        addCoursesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), AddCoursesView.class);
                startActivityForResult(intent, 1);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        displayUserCourses();
    }

    public void displayUserCourses()
    {
        final CoursesAdapter cAdapter = new CoursesAdapter(coursesList, this.getContext());
        customersRecyclerView.setAdapter(cAdapter);
        customersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

//    private void addUITestData(){
//        Course course1 = new Course
//                ("CECS 445", "Software Design and Architecture",
//                        "M/W 3:30PM- 4:45PM", "ECS  Room 308");
//        Course course2 = new Course
//                ("CECS 327","Introduction to Networks and Distributed Computing",
//                        "T/F 12:30PM- 1:45PM", "VEC  Room 408");
//        Course course3 = new Course
//                ("CECS 378","Introduction to Computer Security Principles",
//                        "M/W 1:20PM- 2:45PM", "ECS  Room 228");
//        coursesList.add(course1);
//        coursesList.add(course2);
//        coursesList.add(course3);
//    }
}