package com.hats.plannit.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hats.plannit.R;
import com.hats.plannit.models.Assignment;
import com.hats.plannit.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private List<Assignment> assignmentList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.init();

        View root = inflater.inflate(R.layout.fragment_search, container, false);


        RecyclerView customersRecyclerView =  root.findViewById(R.id.rv_search);
        assignmentList = searchViewModel.getmAssignmentList().getValue();
        final SearchAdapter searchAdapter = new SearchAdapter(assignmentList, this.getContext());
        customersRecyclerView.setAdapter(searchAdapter);
        customersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final CheckBox cb1 = root.findViewById(R.id.search_cb1);
        final CheckBox cb2 = root.findViewById(R.id.search_cb2);
        final SearchView searchView = root.findViewById(R.id.search);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return root;
    }
}