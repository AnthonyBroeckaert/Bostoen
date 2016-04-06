package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.BostoenApp.R;

/**
 * Created by Anthony on 25/03/2016.
 */

public class LoginAdviseurFragment extends Fragment {
    private View view;
    private FragmentsInterface mListener;
    private OnFragmentInteractionListener methods;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.loginadviseur_layout, container, false);
        Button volgende=(Button)view.findViewById(R.id.btnFragKlant);
        EditText voornaam= (EditText) view.findViewById(R.id.txtVoornaamAdviseur);
        EditText naam = (EditText) view.findViewById(R.id.txtNaamAdviseur);
        EditText email = (EditText) view.findViewById(R.id.txtEmailAdviseur);

        if(methods.getVoornaam()!=null)
        {
            voornaam.setText(methods.getVoornaam());
        }

        if(methods.getNaam()!=null)
        {
            naam.setText(methods.getNaam());
        }

        if(methods.getEmail()!=null)
        {
            naam.setText(methods.getEmail());
        }

        volgende.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("click","yeaah");
                boolean blnVoornaam=false;
                boolean blnNaam=false;
                boolean blnEmail=false;
                if(!voornaam.getText().toString().equals(""))
                {
                    blnVoornaam = true;
                }
                else voornaam.setError("Dit veld mag niet leeg zijn");

                if(!naam.getText().toString().equals(""))
                {
                    blnNaam = true;
                }
                else naam.setError("Dit veld mag niet leeg zijn");

                if(!email.getText().toString().equals(""))
                {
                    blnEmail=true;
                }
                else email.setError("Dit veld mag niet leeg zijn");

                if(blnVoornaam && blnNaam && blnEmail)
                {
                    methods.setVoornaam(voornaam.getText().toString());
                    methods.setNaam(naam.getText().toString());
                    methods.setEmail(email.getText().toString());
                    mListener.goToKlantFragment();
                }

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
            if(activity instanceof OnFragmentInteractionListener)
            {
                methods = (OnFragmentInteractionListener)activity;
            }
            else
            {
                throw new RuntimeException(activity.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement FragmetnsInterface");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        String getVoornaam();
        void setVoornaam(String voornaam);
        String getNaam();
        void setNaam(String naam);
        String getEmail();
        void setEmail(String email);

    }
}