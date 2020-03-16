package com.hats.plannit.ui.courses;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.CheckedTextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hats.plannit.R;
import com.hats.plannit.models.Course;
import com.hats.plannit.models.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * author: Howard chen
 */

public class CoursesViewModel extends ViewModel
{
    private AvailableCourseRepo availableCourseRepo;
    private MutableLiveData<List<Subject>> mAvailableSubjectList;
    private MutableLiveData<List<Course>> mRegisteredCourseList;

    public void init()
    {
        if(availableCourseRepo != null)
        {
            return;
        }
        availableCourseRepo = AvailableCourseRepo.getInstance();
        mAvailableSubjectList = availableCourseRepo.getAvailableSubjects();
        mRegisteredCourseList = availableCourseRepo.getRegisteredCourses();

        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);
                List<Course> currentCourses = mRegisteredCourseList.getValue();
                mRegisteredCourseList.postValue(currentCourses);
            }

            @Override
            protected Void doInBackground(Void... voids)
            {
                try
                {
                    Thread.sleep(1600);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public MutableLiveData<List<Subject>> getmAvailableSubjectList()
    {
        return mAvailableSubjectList;
    }

    public MutableLiveData<List<Course>> getmRegisteredCourseList()
    {
        return mRegisteredCourseList;
    }

    public List<CheckedTextView> addCourses(List<ArrayList<Map<Course, CheckedTextView>>> courseListToBeAdded, List<Course> registeredCourseList, List<ArrayList<CheckedTextView>> checkedTextViews, Context context)
    {
        List<CheckedTextView> duplicateCourses = new ArrayList<>();
        List<Course> courses = new ArrayList<>();

        boolean duplicate = false;

        for(int groupPosition = 0; groupPosition < courseListToBeAdded.size(); groupPosition++)
        {
            ArrayList<Map<Course, CheckedTextView>> courseArrayList = courseListToBeAdded.get(groupPosition);

            for(int childPosition = 0; childPosition < courseArrayList.size(); childPosition++)
            {
                if(courseArrayList.get(childPosition) != null)
                {
                    for(Course course: courseArrayList.get(childPosition).keySet())
                    {
                        if(registeredCourseList.contains(course))
                        {
                            duplicateCourses.add(courseArrayList.get(childPosition).get(course));
                            checkedTextViews.get(groupPosition).get(childPosition).setTextColor(context.getResources().getColor(R.color.red));
                            duplicate = true;
                        }
                        else
                        {
                            courses.add(course);
                        }
                    }
                }
            }
        }

        if(!duplicate)
        {
            availableCourseRepo.addRegisteredCourse(courses, context);
            registeredCourseList.addAll(courses);
        }

        return duplicateCourses;
    }

    public void addAllAvailableSubjects(Context context)
    {
        List<Subject> subjectList = new ArrayList<>();

        subjectList.add(new Subject("CECS", new ArrayList<Course>()
        {
            {
                add(new Course("CECS 327", "Introduction to Networks and Distributed Computing", "T/F 12:30PM - 1:45PM", "VEC  Room 408", 5558));
                add(new Course("CECS 378", "Introduction to Computer Security Principles", "M/W 1:20PM - 2:45PM", "ECS  Room 228", 7315));
                add(new Course("CECS 445", "Software Design and Architecture", "M/W 3:30PM - 4:45PM", "ECS  Room 308", 6299));
                add(new Course("CECS 475", "Software Framework", "T/Th 8:00AM - 10:15AM", "ECS  Room 302", 1165));
            }
        }));

        subjectList.add(new Subject("POSC", new ArrayList<Course>()
        {
            {
                add(new Course("POSC 199", "Introduction to California Government", "F 9:00AM - 11:45AM", "SPA Room 209", 3746));
                add(new Course("POSC 218", "Global Politics", "T/Th 12:30PM - 1:45PM", "SPA Room 110", 3149));
                add(new Course("POSC 300", "Scope/Meth Political Science", "T/Th 11:00AM - 12:15PM", "SPA Room 210", 3947));
                add(new Course("POSC 367", "Govt & Politics Middle East", "T 6:30PM - 9:15PM", "SPA Room 212", 8815));
            }
        }));

        subjectList.add(new Subject("ART", new ArrayList<Course>()
        {
            {
                add(new Course("ART 101", "Artists in Their Own Words", "M 5:00PM - 6:50PM", "UT Room 108", 6607));
                add(new Course("ART 305", "Art Disciplines Thru New Tech", "T 9:00AM - 11:45AM", "FA2 Room 200", 1043));
                add(new Course("ART 365", "Media Design: Motion Graphics", "F 9:00AM - 3:45PM", "LA5 Room 369", 3904));
                add(new Course("ART 415", "On Site Studies in Art Educ", "Sa 9:00AM - 3:15PM", "FA2 Room 200", 10646));
            }
        }));

        availableCourseRepo.addAvailableSubjects(subjectList, context);
    }
}