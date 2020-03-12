package com.hats.plannit.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.hats.plannit.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment1 extends Fragment
{
    private CompactCalendarView monthly_calendar_view;
    private SimpleDateFormat simpleDateFormat;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_calendar_1, container, false);

        monthly_calendar_view = root.findViewById(R.id.monthly_calendar_view);
        monthly_calendar_view.setUseThreeLetterAbbreviation(true);

        simpleDateFormat = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

        Event ev1 = new Event(Color.GREEN, 1583693577L);
        monthly_calendar_view.addEvent(ev1);

        monthly_calendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                System.out.println(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });
        return root;
    }
}
