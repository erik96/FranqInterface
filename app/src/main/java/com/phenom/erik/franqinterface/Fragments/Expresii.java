package com.phenom.erik.franqinterface.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phenom.erik.franqinterface.R;
import com.phenom.erik.franqinterface.Util.Constants;

import java.io.File;

/**
 * Created by Raul on 2/21/2015.
 */
public class Expresii extends PreferenceFragment implements Constants {

    private Context mContext;
    private String[] expresii;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mContext = getActivity();

        expresii = getResources().getStringArray(R.array.expresii);
        this.setPreferenceScreen(createPreferenceHierarchy());

    }

    public PreferenceScreen createPreferenceHierarchy(){

        short p = 0;

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(mContext);

        for(String e : expresii) {

            p++;

            String title = e.substring(0, 1).toUpperCase() + e.substring(1);

            PreferenceCategory cat = new PreferenceCategory(mContext);
            cat.setTitle(title);
            root.addPreference(cat);

            Preference pref = new Preference(mContext);
            pref.setTitle(title);
            pref.setKey(title);//TODO: ICON
            if(p%2 == 0) {
                pref.setIcon(R.drawable.ic_1);
            } else {
                pref.setIcon(R.drawable.ic_2);
            }
            cat.addPreference(pref);
        }

        return root;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        String title = preference.getTitle().toString().substring(0, 1).toLowerCase() + preference.getTitle().toString().substring(1);
        int id = getResources().getIdentifier("com.phenom.erik.franqinterface:drawable/" + title, null, null);

        android.app.Fragment firstFragment = null;
        ExpresiiTemplate expresiiTemplate = new ExpresiiTemplate();
        expresiiTemplate.init(id,title);
        firstFragment = expresiiTemplate;
        expresiiTemplate = null;

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
