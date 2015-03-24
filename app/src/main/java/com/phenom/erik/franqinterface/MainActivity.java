package com.phenom.erik.franqinterface;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.phenom.erik.franqinterface.Fragments.Chance;
import com.phenom.erik.franqinterface.Fragments.Expresii;
import com.phenom.erik.franqinterface.Fragments.Verbe;
import com.phenom.erik.franqinterface.Fragments.Welcome;
import com.phenom.erik.franqinterface.Fragments.Words;
import com.phenom.erik.franqinterface.Util.Constants;
import com.phenom.erik.franqinterface.Util.Helpers;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, Constants {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Helpers.createSDFolder();

        Helpers.copyFolder("audio",this);
        Helpers.copyFolder("1. Indicatif present",this);
        Helpers.copyFolder("2. Indicatif imparfait",this);
        Helpers.copyFolder("3. Indicatif passe compose",this);
        Helpers.copyFolder("4. Conditionnel present",this);
        Helpers.copyFolder("5. Conditionnel passe",this);
        Helpers.copyFolder("6. Indicatif plus-que-parfait",this);
        Helpers.copyFolder("7. Indicatif futur",this);

        Helpers.createNoMedia();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        android.app.Fragment firstFragment = null;

        switch (position) {
            case 0:
                firstFragment = new Welcome();
                break;
            case 1:
                firstFragment = new Verbe();
            default:
                break;
            case 2:
                firstFragment = new Expresii();
                break;
            case 3:
                firstFragment = new Words();
                break;
            case 4:
                firstFragment = new Chance();
                break;
        }

        final android.app.Fragment finalFragment = firstFragment;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, finalFragment)
                        .addToBackStack("tag")
                        .commit();
            }
        }, 280);
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
