package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;


import com.example.BostoenApp.DB.CustomDate;
import com.example.BostoenApp.DB.Dossier;
import com.example.BostoenApp.DB.Reeks;
import com.example.BostoenApp.DB.Vraag;
import com.example.BostoenApp.R;
import java.util.ArrayList;


/**
 * Created by Anthony on 25/03/2016.
 */
public class KeuzeFragment extends Fragment {
    private View view;
    private Reeks huidig;
    private OnFragmentInteractionListener mListener;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.keuze_layout, container, false);

        Button volgende = (Button)view.findViewById(R.id.btnFragVragen);
        EditText dossiernaam = (EditText)view.findViewById(R.id.txtIdentificatieKeuze);

        if(mListener.getLastDossier()!=null)
        {
            Dossier dossier = mListener.getDossier(mListener.getLastPlaats());

            if(dossier!=null)
            {
                dossiernaam.setText(dossier.getNaam());
            }
        }
        volgende.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("volgende","keuzefragment");
                if(huidig!=null)
                {
                    Dossier dossier = new Dossier();
                    dossier.setPlaatsId(mListener.getLastPlaats());



                    if(mListener.getLastDossier()==null)
                    {
                        //voorkomen dat bij een update de aanmaakdatum gewijzigd wordt
                        dossier.setDatum(new CustomDate());
                        mListener.addDosier(dossier);
                    }
                    else {
                       if(dossier!=null){
                           mListener.updateDossier(mListener.getLastDossier(),dossier);
                       }
                    }
                    mListener.goToVragenFragment(huidig.getEersteVraag());
                }

            }
        });

        ListView reekslijst = (ListView)view.findViewById(R.id.reeksLijst);



        ArrayList<Reeks> reeksen = mListener.getReeksen();

        if(reeksen!=null)
        {
            if(mListener.getLastReeks()!=null)
            {
                reeksen.get(mListener.getLastReeks()).setChecked(true);
                huidig = reeksen.get(mListener.getLastReeks());
            }

            reekslijst.setAdapter(new Reeks.ReeksAdapter(getActivity().getApplicationContext(), reeksen));
        }



        reekslijst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                huidig = (Reeks) parent.getItemAtPosition(position);
                mListener.setLastReeks(position);
                for (int i = 0; i < reeksen.size(); i++) {
                    Reeks reeks = reeksen.get(i);

                    if (reeks == huidig) {
                        reeksen.get(i).setChecked(true);
                    } else {
                        reeksen.get(i).setChecked(false);
                    }

                }


                reekslijst.setAdapter(new Reeks.ReeksAdapter(getActivity().getApplicationContext(), reeksen));
                Log.d("Listview","onclick");
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

    ArrayList<Reeks> getReeksen();
    Integer getLastPlaats();
    Integer getLastDossier();
    Dossier getDossier(int id);
    Integer getLastReeks();
    void setLastReeks(Integer id);
    void addDosier(Dossier dossier);
    void updateDossier(int id,Dossier dossier);
        void goToVragenFragment(int id);

    }
}