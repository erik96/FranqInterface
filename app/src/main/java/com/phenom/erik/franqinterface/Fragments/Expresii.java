package com.phenom.erik.franqinterface.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phenom.erik.franqinterface.R;
import com.phenom.erik.franqinterface.Util.Constants;

/**
 * Created by Raul on 2/21/2015.
 */
public class Expresii extends PreferenceFragment implements Constants {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //this.setPreferenceScreen(createPreferenceHierarchy());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expresii_layout, root, false);

        return view;
    }

}
