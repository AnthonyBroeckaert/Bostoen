package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.BostoenApp.DB.AntwoordOptie;
import com.example.BostoenApp.DB.VragenDossier;
import com.example.BostoenApp.R;

import java.util.ArrayList;

/**
 * Created by david on 1/04/2016.
 */
public class EindFragment extends Fragment {
    private View view;
    private FragmentsInterface mListener;
    private OnFragmentInteractionListener methods;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.eindscherm_layout, container, false);
        TextView oplossing = (TextView) view.findViewById(R.id.txtResultaat);
        StringBuilder tekst = new StringBuilder();

        ArrayList<VragenDossier> vragenDossiers = methods.getVragenDossiers(methods.getLastDossier());

        for(VragenDossier vragenDossier : vragenDossiers)
        {
            ArrayList<AntwoordOptie>  antwoordOpties= methods.getAntwoorden(vragenDossier.getAntwoordOptie());
            Log.d("Antwoordopties",new Integer(antwoordOpties.size()).toString());

            for(AntwoordOptie antwoordOptie : antwoordOpties)
            {
                Log.d("Antwoordoptie conditie", new Boolean(antwoordOptie.getAntwoordTekst().equals(vragenDossier.getAntwoordTekst())).toString());
                if(antwoordOptie.getAntwoordTekst().equals(vragenDossier.getAntwoordTekst()))
                {
                    if(antwoordOptie.getOplossing()!=null && !antwoordOptie.getOplossing().equals(""))
                    {
                        tekst.append(antwoordOptie.getOplossing()+" \n"+"\n");
                        Log.d("Oplossing",antwoordOptie.getOplossing());
                    }
                }
            }
        }

        Log.d("oplossing",tekst.toString());
        oplossing.setText(tekst.toString());
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
                methods =(OnFragmentInteractionListener) activity;
            }
            else {
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
        ArrayList<VragenDossier> getVragenDossiers(int dossiernr);
        Integer getLastDossier();
        void setLastDossier(Integer lastdossier);
        ArrayList<AntwoordOptie> getAntwoorden(int vraagid);

    }
}
