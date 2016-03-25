package com.example.BostoenApp.Schermen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.BostoenApp.R;

/**
 * Created by Anthony on 25/03/2016.
 */
public class LoginKlantFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loginklant_layout, container, false);
    }
}