package com.hats.plannit.ui.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;

import java.util.List;

public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder>
{
    private final List<Assignment> assignmentList;

    public CalendarRecyclerViewAdapter(List<Assignment> assignmentList)
    {
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Assignment assignment = assignmentList.get(position);
        holder.assignmentTextView.setText(assignment.getCourseName() + " - " + assignment.getAssignmentName() + "\nDue date: " + assignment.getDate() + "\nDue time: " + assignment.getTime());
    }

    @Override
    public int getItemCount()
    {
        if(assignmentList == null)
        {
            return 0;
        }
        return assignmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView assignmentTextView;

        ViewHolder(View view)
        {
            super(view);
            assignmentTextView = view.findViewById(R.id.assignment_text_view);
        }
    }
}
