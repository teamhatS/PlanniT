//package com.hats.plannit.ui.calendar;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.CalendarView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.hats.plannit.R;
//
//import org.w3c.dom.Text;
//
//import java.util.ArrayList;
//
//public class CalendarFragment extends Fragment {
//
////    private CalendarViewModel calendarViewModel;
//    private CalendarView monthly_calendar;
//    private TextView text;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
////        calendarViewModel =
////                ViewModelProviders.of(this).get(CalendarViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
//
//        monthly_calendar = root.findViewById(R.id.monthly_calendar);
//        text = root.findViewById(R.id.text);
//
//        monthly_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
//        {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2)
//            {
//                String date = (i1 + 1) + "/" + i2 + "/" + i;
//                text.setText(date);
//            }
//        });
//
////        final TextView textView = root.findViewById(R.id.monthly_calendar);
////        calendarViewModel.getText().observe(this, new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });
//        return root;
//    }
//}

package com.hats.plannit.ui.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;
import com.hats.plannit.ui.assignments.AssignmentsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarFragment extends Fragment
{
    private CompactCalendarView monthlyCalendarView;
    private SimpleDateFormat simpleDateFormat;
    private TextView monthYear;
    private RecyclerView assignmentRecyclerView;

    private Map<Long, ArrayList<Assignment>> assignments;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        setHasOptionsMenu(true);

        simpleDateFormat = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());

        monthYear = root.findViewById(R.id.month_year);
        monthYear.setText(simpleDateFormat.format(new Date()));

        monthlyCalendarView = root.findViewById(R.id.monthly_calendar_view);
        monthlyCalendarView.setUseThreeLetterAbbreviation(true);

        assignmentRecyclerView = root.findViewById(R.id.assignment_recycler_view);

        assignments = new HashMap<>();

        showDueDate();

        monthlyCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener()
        {
            @Override
            public void onDayClick(Date dateClicked)
            {
                CalendarRecyclerViewAdapter calendarRecyclerViewAdapter = new CalendarRecyclerViewAdapter(assignments.get(dateClicked.getTime()));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                assignmentRecyclerView.setLayoutManager(layoutManager);
                assignmentRecyclerView.setAdapter(calendarRecyclerViewAdapter);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth)
            {
                monthYear.setText(simpleDateFormat.format(firstDayOfNewMonth));
            }
        });
        return root;
    }

    private void showDueDate()
    {
        try
        {
            for(final Assignment assignment: AssignmentsAdapter.getAssignmentsArrayList())
            {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/dd");
                Date date = simpleDateFormat.parse(assignment.getDate());
                monthlyCalendarView.addEvent(new Event(Color.GREEN, date.getTime()));

                if(assignments.containsKey(date.getTime()))
                {
                    assignments.get(date.getTime()).add(assignment);
                }
                else
                {
                    assignments.put(date.getTime(), new ArrayList<Assignment>(){{add(assignment);}});
                }
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
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
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment, new WeekDayCalendarFragment(1));
                fr.commit();
                return true;
            }
            case R.id.week_view:
            {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment, new WeekDayCalendarFragment(2));
                fr.commit();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
