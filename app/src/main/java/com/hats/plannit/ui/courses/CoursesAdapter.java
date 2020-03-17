package com.hats.plannit.ui.courses;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;

import java.util.List;

/*
 * author: Howard chen
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder>{
    private static final String TAG = "CoursesAdapter";
    private List<Course> courseArrayList;
    private Context mContext;
    private OnItemClicked onItemClicked;

    public CoursesAdapter(Context mContext, List<Course> courseArrayList, OnItemClicked onItemClicked)
    {
        this.courseArrayList = courseArrayList;
        this.mContext = mContext;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_courses, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position)
    {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener
    {
        TextView textViewCourseName;
        TextView textViewCourseTitle;
        TextView textViewCourseTime;
        TextView textViewCourseLocation;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewCourseName = itemView.findViewById(R.id.tv_course_number);
            textViewCourseTitle = itemView.findViewById(R.id.tv_course_title);
            textViewCourseTime = itemView.findViewById(R.id.tv_course_schedule);
            textViewCourseLocation = itemView.findViewById(R.id.tv_course_location);
            parentLayout = itemView.findViewById(R.id.parent_layout_courses);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view)
        {
            final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.remove_course_view);

            Button no_button = dialog.findViewById(R.id.no_button);
            Button yes_button = dialog.findViewById(R.id.yes_button);
            TextView remove_course = dialog.findViewById(R.id.remove_course);
            remove_course.setText("Are you sure you wan to remove " + courseArrayList.get(getLayoutPosition()).getName() + "?");

            yes_button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    onItemClicked.onItemDelete(getLayoutPosition());
                    dialog.dismiss();
                }
            });

            no_button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                }
            });

            dialog.show();
            return false;
        }
    }
}
