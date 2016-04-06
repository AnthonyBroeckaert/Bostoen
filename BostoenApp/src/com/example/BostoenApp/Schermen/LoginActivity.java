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
public class LoginActivity extends Activity implements FragmentsInterface,LoginAdviseurFragment.OnFragmentInteractionListener,LoginKlantFragment.OnFragmentInteractionListener,TestInterface{

    private static final String PREFS_NAME = "COM.BOSTOEN.BE";
    private SharedPreferences sharedpreferences;
    private DataDBAdapter dataDBAdapter;
    private Integer lastPlaats;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        sharedpreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        if (savedInstanceState == null) {
            if(!sharedpreferences.contains("Voornaam"))
            {
                Log.d("no shared preferences","");
                getFragmentManager().beginTransaction().add(R.id.container, new LoginAdviseurFragment(), "AdviseurFragment")
                        .addToBackStack("AdviseurFragment")
                        .commit();
            }
            else
            {
                getFragmentManager().beginTransaction().add(R.id.container, new LoginKlantFragment(), "KlantFragment")
                        .addToBackStack("KlantFragment")
                        .commit();
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
        goEnqueteActivity();
    }

    @Override
    public void goToEindScherm() {
        getFragmentManager().beginTransaction().replace(R.id.container, new EindFragment(), "EindFragment")
                .addToBackStack("EindFragment")
                .commit();
    }

    //fix voor marnix
    //marnix //

    protected void goEnqueteActivity(){
        Intent intent = new Intent(getApplicationContext(), EnqueteActivity.class);
        intent.putExtra("Lastplaats",lastPlaats);
        startActivity(intent);
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
        Log.d("Activity", "Voornaam is set");
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

    @Override
    public void addPlaats(Plaats plaats) {
        dataDBAdapter.open();
        setLastPlaats(new Integer((int) dataDBAdapter.addPlaats(plaats)));
        StringBuilder plaatsen= new StringBuilder();
        ArrayList<Plaats> plaatsArrayList = dataDBAdapter.getPlaatsenFromCursor(dataDBAdapter.getPlaatsen());
        for(int i=0;i<plaatsArrayList.size();i++)
        {
            plaatsen.append(i+"\n");
        }
        Log.d("plaatsen", plaatsen.toString());
        dataDBAdapter.close();
    }

    public void setLastPlaats(Integer lastPlaats)
    {
        this.lastPlaats=lastPlaats;
    }

    @Override
    public Integer getLastPlaats() {
        return lastPlaats;
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

        dataDBAdapter.addAntwoordOptie(new AntwoordOptie(1, "aa", "aa", 2, "hey", true, new CustomDate()));
        dataDBAdapter.addAntwoordOptie(new AntwoordOptie(1, "bb", "bb", 2, "bye", true,new CustomDate()));
        dataDBAdapter.addAntwoordOptie(new AntwoordOptie(2, "cc", "c", null, "halo", true, new CustomDate()));

        dataDBAdapter.close();
    }

    @Override
    public void ClearData() {
        dataDBAdapter.open();
        dataDBAdapter.clearAll();
        dataDBAdapter.create();
        dataDBAdapter.close();
    }
}

