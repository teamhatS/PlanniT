package com.hats.plannit.ui.assignments;


/*
 * author: Howard chen
 */

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;

import java.util.List;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.ViewHolder>{
    private static final String TAG = "HomeAdapter";
    private static List<Assignment> assignmentsArrayList;
    private Context mContext;
    private AssignmentsViewModel assignmentsViewModel;

    public AssignmentsAdapter(Context mContext, List<Assignment> assignmentsArrayList,
                              AssignmentsViewModel assignmentsViewModel) {
        this.assignmentsArrayList = assignmentsArrayList;
        this.mContext = mContext;
        this.assignmentsViewModel = assignmentsViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_listitem_assignments, parent, false);
         ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        ((ViewHolder)holder).bindView(position);

        ((ViewHolder)holder)
                .parentLayout
                .setOnLongClickListener
                        (new View.OnLongClickListener() {

                            @Override
                            public boolean onLongClick(View v) {
                                final Assignment assignmentToDelete =
                                        assignmentsArrayList.get(position);
                                final Dialog delDialog = new Dialog(mContext);
                                delDialog.setContentView(R.layout.fragment_delete_dialog);
                                Button yesButton = delDialog.findViewById(R.id.btn_deldialog_yes);
                                Button noButton = delDialog.findViewById(R.id.btn_deldialog_no);
                                TextView exitTextview =
                                        delDialog.findViewById(R.id.tv_deldialog_exit);
                                TextView questionTextView =
                                        delDialog.findViewById(R.id.tv_deldialog_question);
                                String question = "Do you want to delete "
                                        + assignmentToDelete.getAssignmentName() + "?";
                                questionTextView.setText(question);
                                yesButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        assignmentsViewModel.delAssignment(
                                                assignmentToDelete, mContext);
                                        assignmentsArrayList.remove(position);
                                        assignmentsViewModel.getmAssignmentList()
                                                .setValue(assignmentsArrayList);
                                        delDialog.dismiss();
                                    }
                                });
                                delDialog.show();

                                noButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        delDialog.dismiss();
                                    }
                                });

                                exitTextview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        delDialog.dismiss();
                                    }
                                });


                                return false;
                            }
                        });
        ((ViewHolder)holder).checkboxAssignmentComplete
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    assignmentsViewModel.completeAssignment(assignmentsArrayList
                            .get(position), mContext, isChecked);

            }

        });
    }

    @Override
    public int getItemCount() {
        return assignmentsArrayList.size();
    }

    public static List<Assignment> getAssignmentsArrayList()
    {
        return assignmentsArrayList;
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
            parentLayout = itemView.findViewById(R.id.parent_layout_search);
            textViewCourseName = itemView.findViewById(R.id.tv_course_name);
            textViewAssignmentName = itemView.findViewById(R.id.tv_assignment_name);
            textViewAssignmentDate = itemView.findViewById(R.id.tv_assignment_date);
            textViewAssignmentDescription = itemView.findViewById(R.id.tv_assignment_description);
            textViewAssignmentTime = itemView.findViewById(R.id.tv_assignment_time);
            checkboxAssignmentComplete = itemView.findViewById(R.id.checkBox_assignment_complete);
            assignmentDescription = itemView.findViewById(R.id.assignment_description);
        }

        public void bindView(int position){
            textViewCourseName .setText(assignmentsArrayList.get(position).getCourseName());
            textViewAssignmentName .setText(assignmentsArrayList.get(position).getAssignmentName());
            textViewAssignmentDate.setText(assignmentsArrayList.get(position).getDate());
            textViewAssignmentTime.setText(assignmentsArrayList.get(position).getTime());
            textViewAssignmentDescription.setText(assignmentsArrayList.get(position)
                    .getDescription());
            checkboxAssignmentComplete.setChecked(assignmentsArrayList.get(position).getComplete());
            assignmentDescription = itemView.findViewById(R.id.assignment_description);
        }
    }
}

