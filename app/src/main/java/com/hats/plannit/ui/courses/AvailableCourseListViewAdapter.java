package com.hats.plannit.ui.courses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;
import com.hats.plannit.models.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailableCourseListViewAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private List<Subject> availableSubjectList;
    private List<ArrayList<Boolean>> checkStates;

    public AvailableCourseListViewAdapter(Context context, List<Subject> subjects)
    {
        this.context = context;
        availableSubjectList = subjects;

        checkStates = new ArrayList<>();
        for(int i = 0; i < availableSubjectList.size(); i++)
        {
            ArrayList<Map<Course, CheckedTextView>> courses = new ArrayList<>();
            ArrayList<CheckedTextView> checkedTextViews = new ArrayList<>();
            ArrayList<Boolean> childrenCheckedStates = new ArrayList<>();

            for(int j = 0; j < availableSubjectList.get(i).getCourseList().size(); j++)
            {
                courses.add(null);

                CheckedTextView checkedTextView = new CheckedTextView(context);
                checkedTextView.setTextColor(context.getResources().getColor(R.color.black));
                checkedTextViews.add(checkedTextView);

                childrenCheckedStates.add(false);
            }

            CourseAsset.courseListToBeAdded.add(courses);
            CourseAsset.checkedTextViewList.add(checkedTextViews);
            checkStates.add(childrenCheckedStates);
        }
    }

    @Override
    public int getGroupCount()
    {
        return availableSubjectList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return availableSubjectList.get(groupPosition).getCourseList().size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return availableSubjectList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return availableSubjectList.get(groupPosition).getCourseList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String subject = ((Subject)getGroup(groupPosition)).getSubjectName();

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.subjects_view, null);
        }

        TextView newSubject = convertView.findViewById(R.id.new_subject);
        newSubject.setText(subject);

        return convertView;
    }

    public static class ViewHolder
    {
        public CheckedTextView newCourse;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        final String course = ((Course)getChild(groupPosition, childPosition)).toString();
        View view = convertView;
        final ViewHolder viewHolder;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.courses_view, null);
            viewHolder = new ViewHolder();
            viewHolder.newCourse = view.findViewById(R.id.new_course);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.newCourse.setText(course);
        viewHolder.newCourse.setChecked((checkStates.get(groupPosition).get(childPosition)));
        viewHolder.newCourse.setTextColor(view.getResources().getColor(R.color.black));
        viewHolder.newCourse.setTextColor(CourseAsset.checkedTextViewList.get(groupPosition).get(childPosition).getTextColors());

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                CheckedTextView checkedTextView = (CheckedTextView)view;

                if(viewHolder.newCourse.isChecked())
                {
                    viewHolder.newCourse.setChecked(false);
                    viewHolder.newCourse.setTextColor(view.getResources().getColor(R.color.black));
                    CourseAsset.checkedTextViewList.get(groupPosition).get(childPosition).setTextColor(view.getResources().getColor(R.color.black));
                    CourseAsset.courseListToBeAdded.get(groupPosition).set(childPosition, null);
                    CourseAsset.courses.remove(CourseAsset.availableSubjectList.get(groupPosition).getCourseList().get(childPosition));
                }
                else
                {
                    viewHolder.newCourse.setChecked(true);
                    viewHolder.newCourse.setTextColor(view.getResources().getColor(R.color.black));
                    CourseAsset.courseListToBeAdded.get(groupPosition).set(childPosition, new HashMap<Course, CheckedTextView>()
                    {
                        {
                            put(CourseAsset.availableSubjectList.get(groupPosition).getCourseList().get(childPosition), viewHolder.newCourse);
                        }
                    });
                    CourseAsset.courses.add(CourseAsset.availableSubjectList.get(groupPosition).getCourseList().get(childPosition));
                }
                checkStates.get(groupPosition).set(childPosition, checkedTextView.isChecked());
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
