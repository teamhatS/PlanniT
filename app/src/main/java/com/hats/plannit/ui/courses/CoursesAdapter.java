package com.hats.plannit.ui.courses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;

import java.util.ArrayList;

/*
 * author: Howard chen
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder>{
    private static final String TAG = "CoursesAdapter";
    private ArrayList<Course> courseArrayList;
    private Context mContext;

    public CoursesAdapter(ArrayList<Course> courseArrayList, Context mContext) {
        this.courseArrayList = courseArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_courses, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.textViewCourseName.setText(courseArrayList.get(position).getName());
        holder.textViewCourseTitle.setText(courseArrayList.get(position).getTitle());
        holder.textViewCourseTime.setText(courseArrayList.get(position).getTime());
        holder.textViewCourseLocation.setText(courseArrayList.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView textViewCourseName;
        TextView textViewCourseTitle;
        TextView textViewCourseTime;
        TextView textViewCourseLocation;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCourseName = itemView.findViewById(R.id.tv_course_number);
            textViewCourseTitle = itemView.findViewById(R.id.tv_course_title);
            textViewCourseTime = itemView.findViewById(R.id.tv_course_schedule);
            textViewCourseLocation = itemView.findViewById(R.id.tv_course_location);
            parentLayout = itemView.findViewById(R.id.parent_layout_courses);
        }

    }
}
