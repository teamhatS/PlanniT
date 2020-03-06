package com.hats.plannit.ui.home;


/*
 * author: Howard chen
 */

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
import com.hats.plannit.models.Assignment;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private static final String TAG = "HomeAdapter";
    private ArrayList<Assignment> assignmentsArrayList;
    private Context mContext;

    public HomeAdapter(ArrayList<Assignment> assignmentsArrayList, Context mContext) {
        this.assignmentsArrayList = assignmentsArrayList;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

    }

    @Override
    public int getItemCount() {
        return assignmentsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


//        TextView textViewCourseName;
//        TextView textViewCourseTitle;
//        TextView textViewCourseTime;
//        TextView textViewCourseLocation;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
//            textViewCourseName = itemView.findViewById(R.id.tv_course_number);
//            textViewCourseTitle = itemView.findViewById(R.id.tv_course_title);
//            textViewCourseTime = itemView.findViewById(R.id.tv_course_schedule);
//            textViewCourseLocation = itemView.findViewById(R.id.tv_course_location);
            parentLayout = itemView.findViewById(R.id.relative_layout_home);
        }

    }
}

