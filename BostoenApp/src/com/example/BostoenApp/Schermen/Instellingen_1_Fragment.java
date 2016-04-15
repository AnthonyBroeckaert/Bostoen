package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.BostoenApp.R;

/**
 * Created by Marnix on 15/04/2016.
 */
//
public class Instellingen_1_Fragment extends Fragment {

    private static final String TAG = "BOSTOEN";
    private static EditText naam;
    private static EditText voornaam;
    private static EditText email;

    InstelllingenAdviseurListener activityCommander;

    public interface InstelllingenAdviseurListener {
        public void setInstellingenAdviseur(String naam, String voornaam, String email);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.instellingen1_layout, container, false);

        naam = (EditText) view.findViewById(R.id.txtNaamAdviseur);
        voornaam = (EditText) view.findViewById(R.id.txtVoornaamAdviseur);
        email = (EditText) view.findViewById(R.id.txtEmailAdviseur);

        final Button bevestig = (Button) view.findViewById(R.id.btnFragKlant);
        bevestig.setText("Bevestig");

        bevestig.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bevestig(v);
            }
        });

        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityCommander = (InstelllingenAdviseurListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    public void bevestig(View view) {
        activityCommander.setInstellingenAdviseur(naam.getText().toString(), voornaam.getText().toString(), email.getText().toString());
        Log.i(TAG, "Fragment bevistig button clicked");
    }
}