package com.hats.plannit.ui.courses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;

import java.util.List;

public class AvailableCourseListViewAdapter extends ArrayAdapter<Course>
{
    private Activity activity;
    private List<Course> availableCourseList;
    private static LayoutInflater inflater;

    public AvailableCourseListViewAdapter(Activity activity, int resource, List<Course> courses)
    {
        super(activity, resource, courses);
        this.activity = activity;
        availableCourseList = courses;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return availableCourseList.size();
    }

    public Course getCourse(Course position)
    {
        return position;
    }

    public long getCoursePosition(int position)
    {
        return position;
    }

    public static class ViewHolder
    {
        public CheckedTextView newCourse;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        final ViewHolder viewHolder;

        if(view == null)
        {
            view = inflater.inflate(R.layout.courses_view, null);

            viewHolder = new ViewHolder();
            viewHolder.newCourse = view.findViewById(R.id.new_course);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)view.getTag();
        }

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(viewHolder.newCourse.isChecked())
                {
                    viewHolder.newCourse.setChecked(false);
                    viewHolder.newCourse.setBackgroundColor(view.getResources().getColor(R.color.silver));

                    if(CourseAsset.courseListToBeAdded.contains(availableCourseList.get(position)))
                    {
                        CourseAsset.courseListToBeAdded.remove(availableCourseList.get(position));
                    }
                }
                else
                {
                    viewHolder.newCourse.setChecked(true);

                    CourseAsset.courseListToBeAdded.add(availableCourseList.get(position));
                }
                for(Course course: CourseAsset.courseListToBeAdded)
                {
                    System.out.println(course.toString());
                }
            }
        });

        viewHolder.newCourse.setText(availableCourseList.get(position).toString());

        return view;
    }
}
