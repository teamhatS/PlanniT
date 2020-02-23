package com.hats.plannit.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private ArrayList<Assignment> assignmentList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        final TextView textView = root.findViewById(R.id.text_search);
        searchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final SearchView searchView = root.findViewById(R.id.search);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);

        Assignment a1 = new Assignment("CECS 491", "Project 1", "2/25/2020", "11:59 PM", "Description of Project !");
        Assignment a2 = new Assignment("CECS 491", "Project 2", "3/25/2020", "11:59 PM", "Description of Project 2");
        Assignment a3 = new Assignment("CECS 491", "Project 3", "4/25/2020", "11:59 PM", "Description of Project 3");
        Assignment a4 = new Assignment("CECS 491", "Project 4", "5/25/2020", "11:59 PM", "Description of Project 4");
        Assignment a5 = new Assignment("CECS 491", "Project 5", "6/25/2020", "11:59 PM", "Description of Project 5");
        assignmentList.add(a1);
        assignmentList.add(a2);
        assignmentList.add(a3);
        assignmentList.add(a4);
        assignmentList.add(a5);

        RecyclerView customersRecyclerView =  root.findViewById(R.id.rv_search);
        final SearchAdapter searchAdapter = new SearchAdapter(assignmentList, this.getContext());
        customersRecyclerView.setAdapter(searchAdapter);
        customersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }
}