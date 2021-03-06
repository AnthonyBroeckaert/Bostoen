package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.BostoenApp.R;

/**
 * Created by Marnix on 11/04/2016.
 */
public class HomeFragment extends Fragment {
    private View view;
    private OnFragmentInteractionListener mListener;


    private Button startEnquete;
    private Button settings;
    private Button about;
    private TextView welkom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.home_layout, container, false);

        startEnquete =(Button)view.findViewById(R.id.btnStart);
        settings = (Button) view.findViewById(R.id.btnSettings);
        about = (Button) view.findViewById(R.id.btnAbout);
        welkom = (TextView) view.findViewById(R.id.txtWelkom);

        welkom.setText("Welkom, " + ((LoginActivity)getActivity()).getNaam());

                startEnquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.goToKlantFragment();

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.goToInstellingen();

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.goToAboutFragment();

            }
        });


        return view;
    }

    public void onAttach(Activity activity)
    {
        super.onAttach(activity);


            if(activity instanceof OnFragmentInteractionListener)
            {

                mListener = (OnFragmentInteractionListener)activity;
            }
            else {
                throw new RuntimeException(activity.toString()
                        + " must implement OnFragmentInteractionListener");
            }


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {

        void goToAboutFragment();
        void goToInstellingen();
        void goToKlantFragment();

    }
}