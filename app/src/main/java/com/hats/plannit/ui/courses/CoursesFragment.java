package com.hats.plannit.ui.courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;

import java.util.List;

/*
 * author: Howard chen
 */

public class CoursesFragment extends Fragment implements OnItemClicked
{
    private CoursesAdapter coursesAdapter;

    private Button addCoursesButton;
    private RecyclerView customersRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_courses, container, false);
        customersRecyclerView =  root.findViewById(R.id.rv_courses);
        addCoursesButton = root.findViewById(R.id.add_courses_button);

        CourseAsset.courseInit(this);

        CourseAsset.courseViewModel.getmRegisteredCourseList().observe(getViewLifecycleOwner(), new Observer<List<Course>>()
        {
            @Override
            public void onChanged(List<Course> courseList)
            {
                coursesAdapter.notifyDataSetChanged();
            }
        });

        addCoursesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), AddCoursesView.class);
                startActivityForResult(intent, 1);
            }
        });
        initRecyclerViews();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        initRecyclerViews();
    }

    public void initRecyclerViews()
    {
        coursesAdapter = new CoursesAdapter(getActivity(), CourseAsset.registeredCourseList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        customersRecyclerView.setLayoutManager(layoutManager);
        customersRecyclerView.setAdapter(coursesAdapter);
    }

    @Override
    public void onItemDelete(int position)
    {
        CourseAsset.courseViewModel.remove(position);
        coursesAdapter.notifyItemRemoved(position);
    }
}