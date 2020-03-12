package com.hats.plannit.ui.courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.hats.plannit.R;
import com.hats.plannit.models.Course;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Howard chen
 */

public class CoursesFragment extends Fragment
{
    private Button addCoursesButton;
    private RecyclerView customersRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_courses, container, false);
        customersRecyclerView =  root.findViewById(R.id.rv_courses);
        addCoursesButton = root.findViewById(R.id.add_courses_button);

        CourseAsset.courseInit(this);
        displayUserCourses();

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
        if(!CourseAsset.registeredCourseList.isEmpty())
        {
            final CoursesAdapter cAdapter = new CoursesAdapter((ArrayList<Course>)CourseAsset.registeredCourseList, this.getContext());
            customersRecyclerView.setAdapter(cAdapter);
            customersRecyclerView.setItemAnimator(new DefaultItemAnimator());
            customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }
}