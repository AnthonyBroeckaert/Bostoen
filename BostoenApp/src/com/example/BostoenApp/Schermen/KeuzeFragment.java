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
import android.widget.ListView;
import android.widget.RadioButton;


import com.example.BostoenApp.DB.Reeks;
import com.example.BostoenApp.R;
import java.util.ArrayList;


/**
 * Created by Anthony on 25/03/2016.
 */
public class KeuzeFragment extends Fragment {
    private View view;
    private Reeks huidig;
    private FragmentsInterface mListener;
    private OnFragmentInteractionListener methods;
    private TestInterface testInterface;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.keuze_layout, container, false);

        Button volgende = (Button)view.findViewById(R.id.btnFragVragen);
        volgende.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(huidig!=null)
                {
                    mListener.goToVragenFragment(huidig.getEersteVraag());
                }

            }
        });

        ListView reekslijst = (ListView)view.findViewById(R.id.reeksLijst);
        //data die al toegevoegd was verwijderen om conflicten te vermijden
        testInterface.ClearData();
        //test data toevoegen aan database
        testInterface.addSampleData();


        ArrayList<Reeks> reeksen = methods.getReeksen();

        reekslijst.setAdapter(new Reeks.ReeksAdapter(getActivity().getApplicationContext(), reeksen));


        reekslijst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                huidig = (Reeks) parent.getItemAtPosition(position);
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
        if (activity instanceof FragmentsInterface) {
            mListener = (FragmentsInterface) activity;
            if(activity instanceof OnFragmentInteractionListener)
            {
                methods = (OnFragmentInteractionListener)activity;
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

    ArrayList<Reeks> getReeksen();

    }
}