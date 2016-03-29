package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.BostoenApp.R;

/**
 * Created by Marnix on 20/03/2016.
 */
public class EnqueteActivity extends Activity  implements FragmentsInterface{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enquete_layout);
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



    protected void goLoginActivity(){
        Intent intent = new Intent(getApplicationContext(), EnqueteActivity.class);
        startActivity(intent);
     }
}