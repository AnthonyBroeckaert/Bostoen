package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.BostoenApp.R;

/**
 * Created by Anthony on 25/03/2016.
 */
public class LoginKlantFragment extends Fragment {
    private View view;
    private FragmentsInterface mListener;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.loginklant_layout, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof FragmentsInterface) {
            mListener = (FragmentsInterface) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement FragmetnsInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}