package com.hats.plannit.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private List<Assignment> assignmentArrayList;
    private Context mContext;

    public SearchAdapter(List<Assignment> assignmentArrayList, Context mContext) {
        this.assignmentArrayList = assignmentArrayList;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textViewCourseName.setText(assignmentArrayList.get(position).getCourseName());
        holder.textViewAssignmentName.setText(assignmentArrayList.get(position).getAssignmentName());
        holder.textViewAssignmentDate.setText(assignmentArrayList.get(position).getDate());
        holder.textViewAssignmentDescription.setText(assignmentArrayList.get(position).getDescription());

//        final Assignment a = assignmentArrayList.get(position);
//        holder.assignmentDescription.setVisibility(View.GONE);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean expanded = a.getExpanded();
//                holder.assignmentDescription.setVisibility(expanded ? View.GONE:View.VISIBLE);
//                a.setExpanded(!expanded);
//            }
//        });
        holder.assignmentDescription.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.assignmentDescription.setVisibility(assignmentArrayList.get(position).getExpanded() ? View.GONE : View.VISIBLE);
                assignmentArrayList.get(position).setExpanded(!assignmentArrayList.get(position).getExpanded());
            }
        });

    }

    @Override
    public int getItemCount() {
        return assignmentArrayList.size();
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
            textViewAssignmentTime = itemView.findViewById(R.id.tv_assignment_time);
            textViewAssignmentDescription = itemView.findViewById(R.id.tv_assignment_description);
            checkboxAssignmentComplete = itemView.findViewById(R.id.checkBox_assignment_complete);
            assignmentDescription = itemView.findViewById(R.id.assignment_description);
            parentLayout = itemView.findViewById(R.id.parent_layout_search);
        }

    }
}
