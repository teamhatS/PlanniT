package com.hats.plannit.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;
import com.hats.plannit.models.Course;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private List<Assignment> assignmentList = new ArrayList<>();
    List<String> checked = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.init();

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        final RecyclerView customersRecyclerView =  root.findViewById(R.id.rv_search);
        final List<Assignment> o = searchViewModel.getmAssignmentList().getValue();
        assignmentList.addAll(o);
        final SearchAdapter searchAdapter = new SearchAdapter(assignmentList, this.getContext());
        customersRecyclerView.setAdapter(searchAdapter);
        customersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Course> courses = searchViewModel.getmRegisteredCourseList().getValue();
        LinearLayout ll = root.findViewById(R.id.checkboxes2);
        final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        for (Course c : courses) {
            CheckBox cb = new CheckBox(getContext());
            checkBoxes.add(cb);
            cb.setText(c.getName());
            cb.setWidth(200);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        checked.add(buttonView.getText().toString());
                    }
                    else
                        checked.remove(buttonView.getText().toString());

                    assignmentList.clear();
                    for(Assignment a : o)
                        if(checked.contains(a.getCourseName()))
                            assignmentList.add(a);
                    searchAdapter.notifyDataSetChanged();

                }
            });
            ll.addView(cb);
        }
        final CheckBox cb1 = root.findViewById(R.id.search_cb1);
        final CheckBox cb2 = root.findViewById(R.id.search_cb2);
        final SearchView searchView = root.findViewById(R.id.search);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);
        int sb = searchView.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView cb = searchView.findViewById(sb);
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customersRecyclerView.setAdapter(searchAdapter);
                if (isChecked) {
                    Collections.sort(assignmentList, new Comparator<Assignment>() {
                        @Override
                        public int compare(Assignment a, Assignment b) {
                            if(a.getCourseName()==null || b.getCourseName()==null)
                                return 1;
                            return a.getCourseName().compareTo(b.getCourseName());
                        }
                    });
                    searchAdapter.notifyDataSetChanged();
                }

            }
        });        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentList.clear();
                assignmentList.addAll(o);
                searchView.setQuery("", false);
                searchAdapter.notifyDataSetChanged();
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Collections.sort(assignmentList, new Comparator<Assignment>() {
                        @Override
                        public int compare(Assignment a, Assignment b) {
                            if(a.getDate()==null || b.getDate()==null)
                                return 1;
                            return a.getDate().compareTo(b.getDate());
                        }
                    });
                    customersRecyclerView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                }

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                assignmentList.clear();
                for(Assignment a : o)
                    if(a.getCourseName().equals(searchView.getQuery().toString()) || a.getDescription().contains(searchView.getQuery().toString()))
                        assignmentList.add(a);
                customersRecyclerView.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        Button btn = root.findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb1.setChecked(false);
                cb2.setChecked(false);
                for(CheckBox c : checkBoxes)
                    c.setChecked(false);
                assignmentList.clear();
                assignmentList.addAll(o);
                searchAdapter.notifyDataSetChanged();
                customersRecyclerView.setAdapter(searchAdapter);

            }
        });

        return root;
    }
}