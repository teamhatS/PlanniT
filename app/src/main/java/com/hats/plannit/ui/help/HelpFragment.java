package com.hats.plannit.ui.help;

import android.app.Dialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hats.plannit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

    private CardView cv1;
    private CardView cv2;
    private CardView cv3;
    private CardView cv4;
    private Dialog myDialog;

//    public HelpFragment() {
//        // Required empty public constructor
//    }
//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDialog = new Dialog(getContext());
        View root = inflater.inflate(R.layout.fragment_help, container, false);
        cv1 = root.findViewById(R.id.help_cv1);
        cv2 = root.findViewById(R.id.help_cv2);
        cv3 = root.findViewById(R.id.help_cv3);
        cv4 = root.findViewById(R.id.help_cv4);

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.setContentView(R.layout.help_answer1);
                myDialog.show();
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.setContentView(R.layout.help_answer2);
                myDialog.show();
            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.setContentView(R.layout.help_answer3);
                myDialog.show();
            }
        });

        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.setContentView(R.layout.help_answer4);
                myDialog.show();
            }
        });

        return root;
    }
}
