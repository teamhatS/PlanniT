package com.hats.plannit.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;
import com.hats.plannit.ui.calendar.CalendarViewModel;

import java.util.ArrayList;

public class CoursesFragment extends Fragment {

    private CoursesViewModel coursesViewModel;
    private ArrayList<Course> coursesList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coursesViewModel =
                ViewModelProviders.of(this).get(CoursesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_courses, container, false);
        RecyclerView customersRecyclerView =  root.findViewById(R.id.rv_courses);
        addUITestData();
        final CoursesAdapter cAdapter = new CoursesAdapter(coursesList, this.getContext());
        customersRecyclerView.setAdapter(cAdapter);
        customersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        coursesViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                cAdapter.notifyDataSetChanged();
//            }
//        });


        return root;
    }

    private void addUITestData(){
        Course course1 = new Course
                ("CECS 445", "M/W 3:30PM- 4:45PM", "ECS  Room 308");
        Course course2 = new Course
                ("CECS 327", "T/F 12:30PM- 1:45PM", "VEC  Room 408");
        Course course3 = new Course
                ("CECS 378", "M/W 1:20PM- 2:45PM", "ECS  Room 228");
        coursesList.add(course1);
        coursesList.add(course2);
        coursesList.add(course3);

    }
}