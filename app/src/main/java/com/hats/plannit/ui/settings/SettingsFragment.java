package com.hats.plannit.ui.settings;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hats.plannit.R;

import org.w3c.dom.Text;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private Button buttonAccount;
    private Button buttonSound;
    private Button buttonNotification;

    Dialog myDialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        buttonAccount = root.findViewById(R.id.btn_account_settings);
        buttonSound = root.findViewById(R.id.btn_sound_settings);
        buttonNotification = root.findViewById(R.id.btn_notification);

//        settingsViewModel =
//                new ViewModelProvider(this).get(SettingsViewModel.class);
//
//        settingsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });

        myDialog = new Dialog(getContext());

        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccountPopup(v);
            }
        });

        buttonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSoundPopup(v);
            }
        });

        buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationPopup(v);
            }
        });


        return root;

    }

    public void showAccountPopup(View v){
        myDialog.setContentView(R.layout.fragment_account_settings);
        myDialog.show();
    }

    public void showNotificationPopup(View v){
        TextView exitText;
        myDialog.setContentView(R.layout.fragment_notification_settings);
        exitText = (TextView) myDialog.findViewById(R.id.tv_exit);
        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void showSoundPopup(View v){
        TextView exitText;
        myDialog.setContentView(R.layout.fragment_sound_settings);
        exitText = (TextView) myDialog.findViewById(R.id.tv_exit);
        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
}