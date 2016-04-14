package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BostoenApp.DB.AntwoordOptie;
import com.example.BostoenApp.DB.VragenDossier;
import com.example.BostoenApp.R;

import java.util.ArrayList;
import java.util.List;

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
        Button wijziging = (Button) view.findViewById(R.id.btnWijzigen);
        Button verzending = (Button) view.findViewById(R.id.btnVerzenden);
        Button kiesReeks = (Button) view.findViewById(R.id.btnKiesReeks);
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

        wijziging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToInstellingen();
            }
        });

        verzending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        kiesReeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.setLastDossier(null);
                methods.setLastPlaats(null);
                methods.setLastReeks(null);
                mListener.goToKeuzeFragment();
            }
        });
        return view;
    }

    protected void sendEmail() {

        Log.i("Send email", "");

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.setType("text/plain");



        //get a list of apps that meet your criteria above

        List<ResolveInfo> pkgAppsList = getActivity().getPackageManager().queryIntentActivities(emailIntent, PackageManager.MATCH_DEFAULT_ONLY | PackageManager.GET_RESOLVED_FILTER);

        //Cycle through list of apps in list and select the one that matches GMail's package name

        for (ResolveInfo resolveInfo : pkgAppsList) {

            String packageName = resolveInfo.activityInfo.packageName;

            String className = "";

            if(packageName.equals("com.google.android.gm")) {

                className = resolveInfo.activityInfo.name;

                emailIntent.setClassName(packageName, className);

            }

        }





        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"MAIL ONTVANGER"});

        //emailIntent.putExtra(Intent.EXTRA_CC, "EMAIL ONTVANGER");

        //emailIntent.putExtra(Intent.EXTRA_BCC, "EMAIL ONTVANGER");

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ONDERWERP");

        emailIntent.putExtra(Intent.EXTRA_TEXT, "DATA");


        try {

            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            startActivity(emailIntent);

            getActivity().finish();

            // Log.i("Finished sending email...", "");

        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(getActivity(),

                    "There is no email client installed.", Toast.LENGTH_SHORT).show();

        }

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
        void setLastPlaats(Integer lastPlaats);
        void setLastReeks(Integer lastReeks);
        ArrayList<AntwoordOptie> getAntwoorden(int vraagid);

    }
}
