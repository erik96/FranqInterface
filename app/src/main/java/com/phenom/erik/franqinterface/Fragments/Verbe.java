package com.phenom.erik.franqinterface.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.phenom.erik.franqinterface.R;
import com.phenom.erik.franqinterface.Util.Constants;

import java.io.File;

/**
 * Created by Erik on 2/15/2015.
 */
public class Verbe extends PreferenceFragment implements Constants {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mContext = getActivity();

        this.setPreferenceScreen(createPreferenceHierarchy());
    }

    public PreferenceScreen createPreferenceHierarchy(){

        String name;

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(mContext);

        File folderVerbe = new File(Environment.getExternalStorageDirectory()+"/FranqInterface");

        for(File f : folderVerbe.listFiles()) {
            name = f.getName();
            if (name.contains("audio")) continue;

            PreferenceCategory cat = new PreferenceCategory(mContext);
            cat.setTitle(name);
            root.addPreference(cat);

            Preference pref = new Preference(mContext);
            pref.setTitle(name);
            pref.setKey(name);
            //pref.setIcon(R.drawable.ic_action_view_as_list);
            cat.addPreference(pref);
        }

        return root;
    }//end method


    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        String title = (String) preference.getTitle().toString();

        android.app.Fragment firstFragment = null;
        VerbeFragment verbeFragment = new VerbeFragment();
        verbeFragment.setName(title);
        firstFragment = verbeFragment;
        verbeFragment = null;

        final android.app.Fragment finalFragment = firstFragment;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right,R.anim.slide_in_right,R.anim.slide_out_left)
                        .replace(R.id.container, finalFragment)
                        .addToBackStack("tag")
                        .commit();

            }
        }, 280);

        return true;
    }

}
