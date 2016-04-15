package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.view.View;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.BostoenApp.R;

/**
 * Created by Marnix on 20/03/2016.
 */
public class AboutFragment extends Fragment {
    private View view;
    private OnFragmentInteractionListener mListener;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.about_layout, container, false);

        TextView info = (TextView)view.findViewById(R.id.txtAbout);
//        info.setText(R.string.about);
        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {


    }
}