package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.BostoenApp.DB.AntwoordOptie;
import com.example.BostoenApp.DB.CustomDate;
import com.example.BostoenApp.DB.DataDBAdapter;
import com.example.BostoenApp.DB.Reeks;
import com.example.BostoenApp.DB.Vraag;
import com.example.BostoenApp.R;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Marnix on 20/03/2016.
 */
public class LoginActivity extends Activity implements FragmentsInterface,KeuzeFragment.OnFragmentInteractionListener,TestInterface,VragenFragment.OnFragmentInteractionListener{
    private DataDBAdapter dataDBAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginAdviseurFragment(), "AdviseurFragment")
                    .addToBackStack("AdviseurFragment")
                    .commit();
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

        dataDBAdapter.addAntwoordOptie(new AntwoordOptie(1, "aa", "aa", 2, 0, true, new CustomDate()));
        dataDBAdapter.addAntwoordOptie(new AntwoordOptie(1, "bb", "bb", 2, 0, true,new CustomDate()));
        dataDBAdapter.addAntwoordOptie(new AntwoordOptie(2, "cc", "c", 2, 0, true, new CustomDate()));

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
}

