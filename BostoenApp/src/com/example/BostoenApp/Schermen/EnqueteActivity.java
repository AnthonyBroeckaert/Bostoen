package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


import com.example.BostoenApp.DB.AntwoordOptie;
import com.example.BostoenApp.DB.DataDBAdapter;
import com.example.BostoenApp.DB.Dossier;
import com.example.BostoenApp.DB.Reeks;
import com.example.BostoenApp.DB.Vraag;
import com.example.BostoenApp.DB.VragenDossier;
import com.example.BostoenApp.R;

import java.text.ParseException;
import java.util.ArrayList;


/**
 * for marnix
 * Created by Marnix on 20/03/2016.
 */
public class EnqueteActivity extends Activity  implements FragmentsInterface,KeuzeFragment.OnFragmentInteractionListener,VragenFragment.OnFragmentInteractionListener,EindFragment.OnFragmentInteractionListener{

    private DataDBAdapter dataDBAdapter;
    private static final String PREFS_NAME = "COM.BOSTOEN.BE";
    private SharedPreferences sharedpreferences;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enquete_layout);

        dataDBAdapter = new DataDBAdapter(getApplicationContext());

        sharedpreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.content_frame, new KeuzeFragment(), "KeuzeFragment")
                    .addToBackStack("KeuzeFragment")
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void goToAdviseurFragment() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new LoginAdviseurFragment(), "AdviseurFragment")
                .addToBackStack("AdviseurFragment")
                .commit();
    }

    @Override
    public void goToKlantFragment() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new LoginKlantFragment(), "KlantFragment")
                .addToBackStack("KlantFragment")
                .commit();
    }

    @Override
    public void goToVragenFragment(int id) {
        VragenFragment vraag=new VragenFragment();
        vraag.setVraagid(id);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, vraag, "VragenFragment")
                .addToBackStack("VragenFragment")
                .commit();
    }


    @Override
    public void goToAboutFragment() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new AboutFragment(), "AboutFragment")
                .addToBackStack("AboutFragment")
                .commit();
    }

    @Override
    public void goToKeuzeFragment() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new KeuzeFragment(), "KeuzeFragment")
                .addToBackStack("KeuzeFragment")
                .commit();
    }

    @Override
    public void goToEindScherm() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new EindFragment(), "EindFragment")
                .addToBackStack("EindFragment")
                .commit();
    }


    protected void goLoginActivity(){
        Intent intent = new Intent(getApplicationContext(), EnqueteActivity.class);
        startActivity(intent);
     }

    @Override
    public ArrayList<Reeks> getReeksen() {
        dataDBAdapter.open();
        ArrayList<Reeks> reeksen = null;
        try {
            reeksen = dataDBAdapter.getReeksenFromCursor(dataDBAdapter.getReeksen());
        } catch (ParseException e) {
            Log.d("Activity getReeksen", e.getMessage());
            dataDBAdapter.close();
        }
        dataDBAdapter.close();
        return reeksen;
    }



    @Override
    public Vraag getVraag(int id) {
        dataDBAdapter.open();
        Vraag vraag=new Vraag();
        try {
            vraag =dataDBAdapter.getVraagFromCursor(dataDBAdapter.getVraag(id));
        } catch (ParseException e) {
            Log.d("Verkeerd formaat datum",e.getMessage());
            dataDBAdapter.close();
        }
        dataDBAdapter.close();
        return vraag;
    }

    @Override
    public ArrayList<AntwoordOptie> getAntwoorden(int vraagid) {
        dataDBAdapter.open();
        ArrayList<AntwoordOptie> antwoordOpties = new ArrayList<>();
        try{
            antwoordOpties=dataDBAdapter.getAntwoordOptiesFromCursor(dataDBAdapter.getAntwoordOptiesVraag(vraagid));
        } catch (ParseException e) {
            Log.d("Verkeerd formaat datum", e.getMessage());
            dataDBAdapter.close();
        }
        dataDBAdapter.close();
        return antwoordOpties;
    }

    @Override
    public void addVragenDossier(VragenDossier vragenDossier) {
        dataDBAdapter.open();
        dataDBAdapter.addVragenDossier(vragenDossier);
        dataDBAdapter.close();
    }

    @Override
    public void updateVragenDossier(int dossiernr, String vraagtekst, VragenDossier vragenDossier) {
        dataDBAdapter.open();
        dataDBAdapter.updateVragenDossier(dossiernr, vraagtekst, vragenDossier);
        dataDBAdapter.close();
    }

    public void setLastDossier(Integer lastDossier)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(lastDossier!=null)
        {
            editor.putInt("LastDossier",lastDossier);
        }
        else
        {
            editor.putInt("LastDossier",-1);
        }
        editor.commit();

    }


    @Override
    public Integer getLastDossier() {
        int output = sharedpreferences.getInt("LastDossier",-1);
        if(output!=-1)
        {
            return output;
        }
        else return null;
    }

    @Override
    public ArrayList<VragenDossier> getVragenDossiers(int dossiernr) {
        dataDBAdapter.open();
        ArrayList<VragenDossier> vragenDossiers;
        vragenDossiers=dataDBAdapter.getVragenDossiersFromCursor(dataDBAdapter.getVragenDossiers(dossiernr));

        dataDBAdapter.close();
        return vragenDossiers;
    }

    @Override
    public Dossier getDossier(int id) {
        dataDBAdapter.open();
        Dossier dossier = null;
        try {
            dossier = dataDBAdapter.getDossierFromCursor(dataDBAdapter.getDossier(id));
        } catch (ParseException e) {
            Log.d("Loginactivity parse error",e.getMessage());
        }
        dataDBAdapter.close();
        return dossier;
    }

    @Override
    public Integer getLastReeks() {
        if(sharedpreferences.getInt("LastReeks",-1)!=-1)
        {
            return sharedpreferences.getInt("LastReeks",-1);
        }
        else return null;
    }

    @Override
    public void setLastReeks(Integer id) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(id != null)
        {
            editor.putInt("LastReeks",id);
        }
        else
        {
            editor.putInt("LastReeks",-1);
        }
        editor.commit();
    }

    @Override
    public void addDosier(Dossier dossier) {
        dataDBAdapter.open();
        setLastDossier((int)dataDBAdapter.addDossier(dossier));
        dataDBAdapter.close();
    }

    @Override
    public void updateDossier(int id, Dossier dossier) {
        dataDBAdapter.open();
        dataDBAdapter.updateDossier(id,dossier);
        dataDBAdapter.close();
    }

    @Override
    public void addOplossing(String oplossing) {

    }

    @Override
    public Integer getLastPlaats() {
        int lastplaats = sharedpreferences.getInt("LastPlaats",-1);
        if(lastplaats!=-1)
        {
            return lastplaats;
        }
        else return null;
    }

}