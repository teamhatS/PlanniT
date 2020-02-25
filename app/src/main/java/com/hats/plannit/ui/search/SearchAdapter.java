package com.hats.plannit.ui.search;

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

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private ArrayList<Assignment> assignmentArrayList;
    private Context mContext;

    public SearchAdapter(ArrayList<Assignment> assignmentArrayList, Context mContext) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        holder.textViewCourseName.setText(assignmentArrayList.get(position).getCourseName());
        holder.textViewAssignmentName.setText(assignmentArrayList.get(position).getAssignmentName());
        holder.textViewAssignmentDate.setText(assignmentArrayList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return assignmentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.

        TextView textViewCourseName;
        TextView textViewAssignmentName;
        TextView textViewAssignmentDate;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCourseName = itemView.findViewById(R.id.tv_course_name);
            textViewAssignmentName = itemView.findViewById(R.id.tv_assignment_name);
            textViewAssignmentDate = itemView.findViewById(R.id.tv_assignment_date);
            parentLayout = itemView.findViewById(R.id.parent_layout_search);
        }

    }
}
