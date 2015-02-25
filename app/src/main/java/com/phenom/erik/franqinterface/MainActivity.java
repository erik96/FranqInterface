package com.phenom.erik.franqinterface;

import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.phenom.erik.franqinterface.Fragments.Expresii;
import com.phenom.erik.franqinterface.Fragments.Verbe;
import com.phenom.erik.franqinterface.Fragments.Welcome;
import com.phenom.erik.franqinterface.Fragments.Words;
import com.phenom.erik.franqinterface.Util.Constants;
import com.phenom.erik.franqinterface.Util.Helpers;

import java.io.File;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, Constants {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        createSDFolder();

        Helpers.copyFolder("audio",this);
        Helpers.copyFolder("1.Indicatif present",this);
        Helpers.copyFolder("2.Indicatif imparfait",this);
        Helpers.copyFolder("3.Indicatif passe compose",this);
        Helpers.copyFolder("4.Indicatif passe simple",this);
        Helpers.copyFolder("5.Indicatif plus-que-parfait",this);
        Helpers.copyFolder("6.Indicatif futur",this);

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

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void createSDFolder() {
        File direct = new File(Environment.getExternalStorageDirectory()+"/FranqInterface");
        if(!direct.exists()) {
            direct.mkdir();
        }

        /*direct = new File(Environment.getExternalStorageDirectory()+"/FranqInterface/verbe");
        if(!direct.exists()) {
            direct.mkdir();
        }

        direct = new File(Environment.getExternalStorageDirectory()+"/FranqInterface/audio");
        if(!direct.exists()) {
            direct.mkdir();
        } */
    }

}
