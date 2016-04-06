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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.BostoenApp.DB.AntwoordOptie;
import com.example.BostoenApp.DB.Reeks;
import com.example.BostoenApp.DB.Vraag;
import com.example.BostoenApp.DB.VragenDossier;
import com.example.BostoenApp.R;

import java.util.ArrayList;

/**
 * Created by Marnix on 20/03/2016.
 */
public class VragenFragment extends Fragment {
    private View view;
    private FragmentsInterface mListener;
    private OnFragmentInteractionListener methods;
    private int vraagid;
    private boolean answered=false;
    private AntwoordOptie huidig;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.vragenscherm_layout, container, false);
        TextView vraagtekst=(TextView)view.findViewById(R.id.txtVraag);
        TextView tip = (TextView)view.findViewById(R.id.txtTip);
        ListView antwoorden = (ListView)view.findViewById(R.id.AntwoordenList);
        Button ok = (Button) view.findViewById(R.id.btnOK);

        Vraag vraag = methods.getVraag(vraagid);
        if(vraag!=null)
        {
            //kijken of de huidige vraag een afbeelding heeft
            if(vraag.getImage()!=null)
            {
                ImageView image = (ImageView) view.findViewById(R.id.imageView);
                image.setImageBitmap(vraag.getImage());
            }
            vraagtekst.setText(vraag.getTekst());
            if(vraag.getTip()!=null && !vraag.getTip().equals(""))
            {
                tip.setText("Tip : "+vraag.getTip());
            }
            else tip.setText("");

            ArrayList<AntwoordOptie> antwoordOpties=methods.getAntwoorden(vraagid);

            ArrayList<VragenDossier> vragenDossiers=methods.getVragenDossiers(methods.getLastDossier());
            if(vragenDossiers!=null)
            {
                String antwoord="";
                for(int i=0;i<vragenDossiers.size();i++)
                {
                    VragenDossier vragenDossier = vragenDossiers.get(i);
                    if(vragenDossier.getVraagTekst().equals(vraag.getTekst()))
                    {
                        antwoord=vragenDossier.getAntwoordTekst();
                    }
                }
                for (int i =0;i<antwoordOpties.size();i++)
                {
                    if(antwoordOpties.get(i).getAntwoordTekst().equals(antwoord))
                    {
                        huidig=antwoordOpties.get(i);
                        antwoordOpties.get(i).setChecked(true);
                        answered=true;
                        Log.d("Vraag","Match");
                    }
                }
            }

            antwoorden.setAdapter(new AntwoordOptie.AntwoordOptieAdapter(getActivity().getApplicationContext(),antwoordOpties));



            antwoorden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    huidig = (AntwoordOptie) parent.getItemAtPosition(position);
                    for (int i = 0; i < antwoordOpties.size(); i++) {
                        AntwoordOptie antwoordOptie = antwoordOpties.get(i);

                        if (antwoordOptie == huidig) {
                            antwoordOpties.get(i).setChecked(true);
                        } else {
                            antwoordOpties.get(i).setChecked(false);
                        }

                    }

                    antwoorden.setAdapter(new AntwoordOptie.AntwoordOptieAdapter(getActivity().getApplicationContext(), antwoordOpties));


                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (huidig != null) {

                        VragenDossier vragenDossier = new VragenDossier();
                        vragenDossier.setDossierNr(methods.getLastDossier());
                        vragenDossier.setAntwoordTekst(huidig.getAntwoordTekst());
                        vragenDossier.setVraagTekst(vraag.getTekst());
                        vragenDossier.setAntwoordOptie(huidig.getVraagId());

                        if(answered)
                        {
                            Log.d("Vraag","answered");
                            methods.updateVragenDossier(methods.getLastDossier(),vraag.getTekst(),vragenDossier);
                        }
                        else
                        {
                            Log.d("Vraag","not answered");
                            methods.addVragenDossier(vragenDossier);
                        }

                        if (huidig.getVolgendeVraag() != null) {


                            mListener.goToVragenFragment(huidig.getVolgendeVraag());
                            Log.d("volgenge vraag", huidig.getVolgendeVraag().toString());
                            Log.d("volgende vraag","bestaat");
                            Log.d("boolean bestaat", new Boolean(huidig.getVolgendeVraag()!=null).toString());
                        }
                        else {
                            Log.d("Volgende vraag is null", "null");
                            mListener.goToEindScherm();
                        }

                    } else {
                        Log.d("Huidig is null", "null");
                    }
                }
            });
        }

        return view;
        }

        @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof FragmentsInterface) {
            mListener = (FragmentsInterface) activity;
            if(mListener instanceof OnFragmentInteractionListener)
            {
                methods = (OnFragmentInteractionListener)activity;
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

    public void setVraagid(int vraagid)
    {
        this.vraagid=vraagid;
    }
    public int getVraagid()
    {
        return vraagid;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        Vraag getVraag(int id);
        Integer getLastDossier();
        ArrayList<VragenDossier> getVragenDossiers(int vraagid);
        ArrayList<AntwoordOptie> getAntwoorden(int vraagid);
        void addVragenDossier(VragenDossier vragenDossier);
        void updateVragenDossier(int dossiernr,String vraagtekst,VragenDossier vragenDossier);

    }

}