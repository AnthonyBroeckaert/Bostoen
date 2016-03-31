package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.BostoenApp.DB.AntwoordOptie;
import com.example.BostoenApp.DB.Vraag;
import com.example.BostoenApp.R;

import java.util.ArrayList;

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
        Button volgende = (Button)view.findViewById(R.id.btnFragKeuze);

        volgende.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.goToKeuzeFragment();
            }
        });
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