package com.phenom.erik.franqinterface.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.TextView;

import com.phenom.erik.franqinterface.R;
import com.phenom.erik.franqinterface.Util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        firstFragment = new VerbeFragment(title);

        final android.app.Fragment finalFragment = firstFragment;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, finalFragment)
                        .commit();
            }
        }, 280);

        return true;
    }

}
