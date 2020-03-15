package com.hats.plannit.ui.home;


/*
 * author: Howard chen
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private static final String TAG = "HomeAdapter";
    private List<Assignment> assignmentsArrayList;
    private Context mContext;

    public HomeAdapter(Context mContext, List<Assignment> assignmentsArrayList) {
        this.assignmentsArrayList = assignmentsArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_assignments, parent, false);
         ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        ((ViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return assignmentsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCourseName;
        TextView textViewAssignmentName;
        TextView textViewAssignmentDate;
        TextView textViewAssignmentDescription;
        TextView textViewAssignmentTime;
        CheckBox checkboxAssignmentComplete;
        View assignmentDescription;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCourseName = itemView.findViewById(R.id.tv_course_name);
            textViewAssignmentName = itemView.findViewById(R.id.tv_assignment_name);
            textViewAssignmentDate = itemView.findViewById(R.id.tv_assignment_date);
            textViewAssignmentDescription = itemView.findViewById(R.id.tv_assignment_description);
            textViewAssignmentTime = itemView.findViewById(R.id.tv_assignment_time);
            checkboxAssignmentComplete = itemView.findViewById(R.id.checkBox_assignment_complete);
            assignmentDescription = itemView.findViewById(R.id.assignment_description);
            parentLayout = itemView.findViewById(R.id.relative_layout_home);
        }

        public void bindView(int position){
            textViewCourseName .setText(assignmentsArrayList.get(position).getCourseName());
            textViewAssignmentName .setText(assignmentsArrayList.get(position).getAssignmentName());
            textViewAssignmentDate.setText(assignmentsArrayList.get(position).getDate());
            textViewAssignmentTime.setText(assignmentsArrayList.get(position).getTime());
            textViewAssignmentDescription.setText(assignmentsArrayList.get(position).getDescription());
            checkboxAssignmentComplete.setChecked(assignmentsArrayList.get(position).getComplete());
            assignmentDescription = itemView.findViewById(R.id.assignment_description);
        }

    }
}

