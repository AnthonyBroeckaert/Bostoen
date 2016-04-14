package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.BostoenApp.DB.AntwoordOptie;
import com.example.BostoenApp.DB.Plaats;
import com.example.BostoenApp.DB.Vraag;
import com.example.BostoenApp.R;

import java.util.ArrayList;

/**
 * Created by Anthony on 25/03/2016.
 */
public class LoginKlantFragment extends Fragment {
    private View view;
    private OnFragmentInteractionListener mListener;

    private TestInterface testInterface;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.loginklant_layout, container, false);
        Button volgende = (Button)view.findViewById(R.id.btnFragKeuze);
        CheckBox isEigenaar = (CheckBox)view.findViewById(R.id.klantIsEigenaar);
        EditText naam = (EditText)view.findViewById(R.id.txtNaamKlant);
        EditText voornaam = (EditText)view.findViewById(R.id.txtVoornaamKlant);
        EditText straat = (EditText)view.findViewById(R.id.txtStraatKlant);
        EditText gemeente = (EditText)view.findViewById(R.id.txtGemeenteKlant);
        EditText nummer = (EditText)view.findViewById(R.id.txtNrKlant);
        EditText postcode = (EditText)view.findViewById(R.id.txtPostcodeKlant);

        //data die al toegevoegd was verwijderen om conflicten te vermijden
        testInterface.ClearData();
        //test data toevoegen aan database
        testInterface.addSampleData();

        //kijken of er tijdens de uitvoering van de app al een plaats werd toegevoegd
        if(mListener.getLastPlaats()!=null)
        {
            Plaats plaats = mListener.getPlaats(mListener.getLastPlaats());
            if(plaats!=null)
            {


                isEigenaar.setChecked(plaats.isEigenaar());
                naam.setText(plaats.getNaam());
                voornaam.setText(plaats.getVoornaam());
                straat.setText(plaats.getStraat());
                gemeente.setText(plaats.getGemeente());
                nummer.setText(plaats.getNummer().toString());
                postcode.setText(plaats.getPostcode().toString());

            }
            else {
                Log.d("Loginklant","plaats is null");
            }

        }
        volgende.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plaats plaats =new Plaats();
                plaats.setIsEigenaar(isEigenaar.isChecked());
                plaats.setNaam(naam.getText().toString());
                plaats.setVoornaam(voornaam.getText().toString());
                plaats.setStraat(straat.getText().toString());
                plaats.setGemeente(gemeente.getText().toString());
                if(nummer.getText().toString().matches("\\d")) {
                    plaats.setNummer(new Integer(nummer.getText().toString()));
                }
                if(postcode.getText().toString().matches("\\d")) {
                    Log.d("postcode is",postcode.getText().toString());
                    plaats.setPostcode(new Integer(postcode.getText().toString()));
                }
                Log.d("Methods null : ",new  Boolean(mListener==null).toString());
                Log.d("Plaats null : ", new Boolean(plaats == null).toString());
                //indien de plaats nog niet bestaat
                if(mListener.getLastPlaats()==null)
                {
                    mListener.addPlaats(plaats);
                }
                //de plaats updaten
                else {
                    mListener.updatePlaats(mListener.getLastPlaats(),plaats);
                }
                mListener.goToKeuzeFragment();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);


            if(activity instanceof OnFragmentInteractionListener)
            {

                mListener = (OnFragmentInteractionListener)activity;
                if(activity instanceof   TestInterface)
                {
                    testInterface = (TestInterface)activity;
                }
                else
                {
                    throw new RuntimeException(activity.toString()
                            + " must implement TestInterface");
                }
            }
            else {
                throw new RuntimeException(activity.toString()
                        + " must implement OnFragmentInteractionListenere");
            }


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void addPlaats(Plaats plaats);
        Integer getLastPlaats();
        Plaats getPlaats(int id);
        void updatePlaats(int id,Plaats plaats);
        void goToKeuzeFragment();
    }
}