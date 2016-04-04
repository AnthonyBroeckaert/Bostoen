package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.BostoenApp.DB.AntwoordOptie;
import com.example.BostoenApp.DB.Vraag;
import com.example.BostoenApp.DB.VragenDossier;
import com.example.BostoenApp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marnix on 20/03/2016.
 */
public class EnqueteActivity extends Activity  implements FragmentsInterface, VragenFragment.OnFragmentInteractionListener{


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<ObjectDrawerItem> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enquete_layout);

        Intent intent = getIntent();
        int id = intent.getIntExtra("eersteVraag",0);
        goToVragenFragment(id);

        dataList = new ArrayList<ObjectDrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.icon,
                GravityCompat.START);

        dataList.add(new ObjectDrawerItem("Message"));
        dataList.add(new ObjectDrawerItem("Likes"));
        dataList.add(new ObjectDrawerItem("Games"));
        dataList.add(new ObjectDrawerItem("Lables"));

        adapter = new CustomDrawerAdapter(this, R.layout.listview_item,
                dataList);

        mDrawerList.setAdapter(adapter);

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
        getFragmentManager().beginTransaction().replace(R.id.container, vraag, "VragenFragment")
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
    public void goEnqueteActivity(int eersteVraag) {

    }


    protected void goLoginActivity(){
        Intent intent = new Intent(getApplicationContext(), EnqueteActivity.class);
        startActivity(intent);
     }

    @Override
    public Vraag getVraag(int id) {
        return null;
    }

    @Override
    public Integer getLastDossier() {
        return null;
    }

    @Override
    public ArrayList<VragenDossier> getVragenDossiers(int vraagid) {
        return null;
    }

    @Override
    public ArrayList<AntwoordOptie> getAntwoorden(int vraagid) {
        return null;
    }

    @Override
    public void addVragenDossier(VragenDossier vragenDossier) {

    }

    @Override
    public void updateVragenDossier(int dossiernr, String vraagtekst, VragenDossier vragenDossier) {

    }

    @Override
    public void addOplossing(String oplossing) {

    }
}