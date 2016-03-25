package com.example.BostoenApp.Schermen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.BostoenApp.R;

/**
 * Created by Marnix on 20/03/2016.
 */
public class LoginActivity extends Activity implements FragmentsInterface{
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
    public void goToVragenFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new VragenFragment(), "VragenFragment")
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
}

