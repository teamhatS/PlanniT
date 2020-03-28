package com.hats.plannit.ui.calendar;

import android.graphics.RectF;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;
import com.hats.plannit.ui.assignments.AssignmentsAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeekDayCalendarFragment extends Fragment implements WeekView.EventClickListener, MonthLoader.MonthChangeListener
{
    private boolean run = false;
    private static final int DAY_VIEW = 1;
    private static final int WEEK_VIEW = 2;
    private int viewType;

    private WeekView weekView;

    private List<Assignment> assignmentList;

    public WeekDayCalendarFragment(int viewType)
    {
        this.viewType = viewType;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_calendar_week_day, container, false);

        assignmentList = AssignmentsAdapter.getAssignmentsArrayList();

        weekView = root.findViewById(R.id.week_day_view);
        weekView.setOnEventClickListener(this);
        weekView.setMonthChangeListener(this);

        setHasOptionsMenu(true);
        setupDateTimeInterpreter(false);

        if(viewType == DAY_VIEW)
        {
            setDayView();
        }
        else if(viewType == WEEK_VIEW)
        {
            setWeekView();
        }

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.calendar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch(id)
        {
            case R.id.day_view:
            {
                if(viewType != DAY_VIEW)
                {
                    item.setChecked(!item.isChecked());
                    viewType = DAY_VIEW;
                    setDayView();
                }
                return true;
            }
            case R.id.week_view:
            {
                if(viewType != WEEK_VIEW)
                {
                    item.setChecked(!item.isChecked());
                    viewType = WEEK_VIEW;
                    setWeekView();
                }
                return true;
            }
            case R.id.month_view:
            {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment, new CalendarFragment());
                fr.commit();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDateTimeInterpreter(final boolean shortDate)
    {
        weekView.setDateTimeInterpreter(new DateTimeInterpreter()
        {
            @Override
            public String interpretDate(Calendar date)
            {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                if (shortDate)
                {
                    weekday = String.valueOf(weekday.charAt(0));
                }
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour)
            {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect)
    {
        Toast.makeText(getContext(), event.getName(), Toast.LENGTH_LONG).show();
    }

    private void setDayView()
    {
        weekView.setNumberOfVisibleDays(1);
        weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
    }

    private void setWeekView()
    {
        weekView.setNumberOfVisibleDays(7);
        weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth)
    {
        List<WeekViewEvent> events = new ArrayList<>();

        if(!run)
        {
            for(Assignment assignment: assignmentList)
            {
                String[] date = assignment.getDate().split("/");
                String[] time = assignment.getTime().split(":");

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, (Integer.parseInt(time[0]) - 1) % 24);
                startTime.set(Calendar.MINUTE, Integer.parseInt(time[1]));
                startTime.set(Calendar.YEAR, Integer.parseInt(date[0]));
                startTime.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
                startTime.set(Calendar.DATE, Integer.parseInt(date[2]));
                Calendar endTime = (Calendar)startTime.clone();
                endTime.add(Calendar.HOUR_OF_DAY, 1);

                WeekViewEvent event = new WeekViewEvent(assignment.getDocumentId().hashCode(), getEventTitle(assignment), startTime, endTime);
                event.setColor(getResources().getColor(R.color.lawn_green));
                events.add(event);
            }
            run = true;
        }
        return events;
    }

    private String getEventTitle(Assignment assignment)
    {
        return assignment.getAssignmentName() + "\nDue at: " + assignment.getTime();
    }
}
