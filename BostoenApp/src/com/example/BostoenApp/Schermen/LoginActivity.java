package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;


import com.example.BostoenApp.DB.AntwoordOptie;
import com.example.BostoenApp.DB.CustomDate;
import com.example.BostoenApp.DB.DataDBAdapter;
import com.example.BostoenApp.DB.Dossier;
import com.example.BostoenApp.DB.Plaats;
import com.example.BostoenApp.DB.Reeks;
import com.example.BostoenApp.DB.Vraag;
import com.example.BostoenApp.DB.VragenDossier;
import com.example.BostoenApp.R;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Marnix on 20/03/2016.
 */
public class LoginActivity extends Activity implements FragmentsInterface,KeuzeFragment.OnFragmentInteractionListener,TestInterface,VragenFragment.OnFragmentInteractionListener,LoginAdviseurFragment.OnFragmentInteractionListener,LoginKlantFragment.OnFragmentInteractionListener{
    private DataDBAdapter dataDBAdapter;
    private static final String PREFS_NAME = "COM.BOSTOEN.BE";
    private SharedPreferences sharedpreferences;
    private Integer lastPlaats;
    private Integer lastDossier;
    private Integer lastReeks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        sharedpreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        if (savedInstanceState == null) {
            if(!sharedpreferences.contains("Voornaam"))
            {
                Log.d("no shared preferences","");
                goToAdviseurFragment();
            }
            else
            {
                goToKlantFragment();
                Log.d("Shared preferences","");
            }

        }
        dataDBAdapter = new DataDBAdapter(getApplicationContext());
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
        getFragmentManager().beginTransaction().replace(R.id.container, new LoginAdviseurFragment(), "AdviseurFragment")
                .addToBackStack("AdviseurFragment")
                .commit();
    }

    @Override
    public void goToKlantFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new LoginKlantFragment(), "KlantFragment")
                .addToBackStack("KlantFragment")
                .commit();
    }

    @Override
    public void goToVragenFragment(int id) {
        VragenFragment vraag=new VragenFragment();
        vraag.setVraagid(id);
        getFragmentManager().beginTransaction().replace(R.id.container, vraag, "VragenFragment")
                .addToBackStack("VragenFragment")
                .commit();
    }

    @Override
    public void goToAboutFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new AboutFragment(), "AboutFragment")
                .addToBackStack("AboutFragment")
                .commit();
    }

    @Override
    public void goToKeuzeFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new KeuzeFragment(), "KeuzeFragment")
                .addToBackStack("KeuzeFragment")
                .commit();
    }

    protected void goEnqueteActivity(){
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
            Log.d("Activity getReeksen",e.getMessage());
            dataDBAdapter.close();
        }
        dataDBAdapter.close();
        return reeksen;
    }

    @Override
    public void addSampleData()  {
        dataDBAdapter.open();

        dataDBAdapter.addReeks(new Reeks(null, "1", 1, new CustomDate()));
        dataDBAdapter.addReeks(new Reeks(null, "2", 1, new CustomDate()));
        dataDBAdapter.addReeks(new Reeks(null, "3", 3, new CustomDate()));

        Bitmap bitone = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.test1);
        Bitmap bittwo = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.test2);

        dataDBAdapter.addVraag(new Vraag(null,"11","",bitone,new CustomDate(),1,true));
        dataDBAdapter.addVraag(new Vraag(null, "22", "", bittwo, new CustomDate(), 2, true));

        dataDBAdapter.addAntwoordOptie(new AntwoordOptie(1, "aa", "aa", 2, "", true, new CustomDate()));
        dataDBAdapter.addAntwoordOptie(new AntwoordOptie(1, "bb", "bb", 2, null, true,new CustomDate()));
        dataDBAdapter.addAntwoordOptie(new AntwoordOptie(2, "cc", "c", 2, "", true, new CustomDate()));

        dataDBAdapter.close();
    }

    @Override
    public void ClearData() {
        dataDBAdapter.open();
        dataDBAdapter.clearAll();
        dataDBAdapter.create();
        dataDBAdapter.close();
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
        dataDBAdapter.updateVragenDossier(dossiernr,vraagtekst,vragenDossier);
        dataDBAdapter.close();
    }

    @Override
    public String getVoornaam() {
        if(sharedpreferences.contains("Voornaam"))
        {
            return sharedpreferences.getString("Voornaam","");
        }
        else return null;
    }

    @Override
    public void setVoornaam(String voornaam) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Voornaam",voornaam);
        editor.commit();
        Log.d("Activity","Voornaam is set");
    }

    @Override
    public String getNaam() {
        if(sharedpreferences.contains("Naam"))
        {
            return sharedpreferences.getString("Naam","");
        }
        else return null;
    }

    @Override
    public void setNaam(String naam) {
        SharedPreferences.Editor editor=sharedpreferences.edit();
        editor.putString("Naam",naam);
        editor.commit();
        Log.d("Activity", "Naam is set");
    }

    @Override
    public String getEmail() {
        if(sharedpreferences.contains("Email"))
        {
            return sharedpreferences.getString("Email","");
        }
        else return null;
    }

    @Override
    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Email",email);
        editor.commit();
        Log.d("Activity", "Email is set");
    }

    @Override
    public void addPlaats(Plaats plaats) {
        dataDBAdapter.open();
        lastPlaats=new Integer((int) dataDBAdapter.addPlaats(plaats));
        StringBuilder plaatsen= new StringBuilder();
        ArrayList<Plaats> plaatsArrayList = dataDBAdapter.getPlaatsenFromCursor(dataDBAdapter.getPlaatsen());
        for(int i=0;i<plaatsArrayList.size();i++)
        {
            plaatsen.append(i+"\n");
        }
        Log.d("plaatsen",plaatsen.toString());
        dataDBAdapter.close();
    }

    @Override
    public Integer getLastPlaats() {
        return lastPlaats;
    }

    @Override
    public Integer getLastDossier() {
        return lastDossier;
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
        return lastReeks;
    }

    @Override
    public void setLastReeks(Integer id) {
        lastReeks=id;
    }

    @Override
    public void addDosier(Dossier dossier) {
        dataDBAdapter.open();
        lastDossier=(int)dataDBAdapter.addDossier(dossier);
        dataDBAdapter.close();
    }

    @Override
    public void updateDossier(int id, Dossier dossier) {

    }

    @Override
    public Plaats getPlaats(int id) {
        dataDBAdapter.open();
        Plaats plaats = dataDBAdapter.getPlaatsFromCursor(dataDBAdapter.getPlaats(id));
        dataDBAdapter.close();
        return plaats;
    }

    @Override
    public void updatePlaats(int id, Plaats plaats) {
        dataDBAdapter.open();
        dataDBAdapter.updatePlaats(id, plaats);
        dataDBAdapter.close();
    }
}

