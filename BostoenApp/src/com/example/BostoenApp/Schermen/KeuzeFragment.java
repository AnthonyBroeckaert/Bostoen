package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.BostoenApp.DB.CustomDate;
import com.example.BostoenApp.DB.DataDBAdapter;
import com.example.BostoenApp.DB.Reeks;
import com.example.BostoenApp.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Anthony on 25/03/2016.
 */
public class KeuzeFragment extends Fragment {
    private View view;
    private Reeks huidig;
    private FragmentsInterface mListener;

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
                mListener.goToVragenFragment();
            }
        });
        DataDBAdapter dataDBAdapter = new DataDBAdapter(getActivity().getApplicationContext());
        dataDBAdapter.open();
        dataDBAdapter.clearAll();
        dataDBAdapter.create();

        ListView reekslijst = (ListView)view.findViewById(R.id.reeksLijst);
        try {
            dataDBAdapter.addReeks(new Reeks(null,"1",1,new CustomDate()));
            dataDBAdapter.addReeks(new Reeks(null, "2", 1, new CustomDate()));
            ArrayList<Reeks> reeksen = dataDBAdapter.getReeksenFromCursor(dataDBAdapter.getReeksen());

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


                }
            });
        } catch (ParseException e) {
            Log.d("Customdate parse error ",e.getMessage());
        }


        dataDBAdapter.close();
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