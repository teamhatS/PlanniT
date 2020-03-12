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

public class CalendarFragment extends Fragment
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
                monthly_calendar_view.addEvent(new Event(Color.GREEN, dateClicked.getTime()));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });
        return root;
    }
}
