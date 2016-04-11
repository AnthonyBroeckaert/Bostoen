package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


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
 * Created by Marnix on 20/03/2016.
 */
public class EnqueteActivity extends Activity  implements FragmentsInterface,KeuzeFragment.OnFragmentInteractionListener,VragenFragment.OnFragmentInteractionListener,EindFragment.OnFragmentInteractionListener{

    private DataDBAdapter dataDBAdapter;
    private static final String PREFS_NAME = "COM.BOSTOEN.BE";
    private SharedPreferences sharedpreferences;
    private Integer lastPlaats;
    private Integer lastDossier;
    private Integer lastReeks;

    //DRAWERLAYOUT

    private String[] mVragen = {"Test1", "Test2"}; //Namen van de gemaakte vragen in deze array
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enquete_layout);

        if(getIntent().getIntExtra("Lastplaats",-1)!=-1)
        {
            lastPlaats = getIntent().getIntExtra("Lastplaats",-1);
        }

        dataDBAdapter = new DataDBAdapter(getApplicationContext());

        sharedpreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.content_frame, new KeuzeFragment(), "KeuzeFragment")
                    .addToBackStack("KeuzeFragment")
                    .commit();
        }


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.icon, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
               // getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Listview aan drawerlayout linken
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mVragen));
        // Klik listener (verwijst naar DrawerItemClickListener)
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            selectItem(position);
        }
    }

    private void selectItem(int position) {
        //gegevens van vraag op position inladen in fragment
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

    public void goToInstellingen(){
        Intent intent = new Intent(getApplicationContext(), InstellingenActivity.class);
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
        this.lastDossier = lastDossier;

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
    public void setLastReeks(Integer lastReeks) {
        this.lastReeks = lastReeks;
    }

    @Override
    public void addDosier(Dossier dossier) {
        dataDBAdapter.open();
        setLastDossier((int) dataDBAdapter.addDossier(dossier));
        dataDBAdapter.close();
    }

    @Override
    public void updateDossier(int id, Dossier dossier) {
        dataDBAdapter.open();
        dataDBAdapter.updateDossier(id,dossier);
        dataDBAdapter.close();
    }


    @Override
    public Integer getLastPlaats() {
        return lastPlaats;
    }

}