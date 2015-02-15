package com.phenom.erik.franqinterface.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.preference.ListPreference;
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

        this.setPreferenceScreen(createPreferenceHierarchy1());
    }

    public PreferenceScreen createPreferenceHierarchy1(){

        String name;

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(mContext);

        // category 1 created programmatically
        PreferenceCategory cat1 = new PreferenceCategory(mContext);
        cat1.setTitle("Present");
        root.addPreference(cat1);

        File dir = new File(Environment.getExternalStorageDirectory()+"/FranqInterface/verbe");

        for (File f : dir.listFiles()) {
            if (f.isFile()) {
                name = f.getName();

                Preference pref = new Preference(mContext);
                pref.setTitle(name);
                pref.setKey("key");
                cat1.addPreference(pref);
            }
        }

        return root;
    }//end method

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        if(preference != null)
            showDialog((String) preference.getTitle().toString());

        return true;
    }

    private void showDialog(String title) {

        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.table_dialog);

        try {
            // open the file for reading
            InputStream instream = new FileInputStream(Environment.getExternalStorageDirectory()+"/FranqInterface/verbe/" + title);

            // if file the available for reading
            if (instream != null) {
                // prepare the file for reading
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);

                String line;
                int i = 1;
                TextView textView;

                // read every line of the file into the line-variable, on line at the time
                do {
                    line = buffreader.readLine();
                    Log.d(TAG, line);

                    switch (i) {
                        case 1:
                            textView = (TextView) dialog.findViewById(R.id.pers_1);
                            textView.setText(line);
                            break;

                        case 2:
                            textView = (TextView) dialog.findViewById(R.id.pers_2);
                            textView.setText(line);
                            break;

                        case 3:
                            textView = (TextView) dialog.findViewById(R.id.pers_3);
                            textView.setText(line);
                            break;

                        case 4:
                            textView = (TextView) dialog.findViewById(R.id.pers_1_pl);
                            textView.setText(line);
                            break;
                        case 5:
                            textView = (TextView) dialog.findViewById(R.id.pers_2_pl);
                            textView.setText(line);
                            break;

                        case 6:
                            textView = (TextView) dialog.findViewById(R.id.pers_3_pl);
                            textView.setText(line);
                            break;
                        default:
                            break;
                    }
                    i++;
                } while (line != null);

                instream.close();
            }
        } catch (Exception ex) {
            Log.e(TAG, String.valueOf(ex) + "");
        }

        dialog.show();
    }




    public PreferenceScreen createPreferenceHierarchy(){

        mContext = getActivity();

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(mContext);

        // category 1 created programmatically
        PreferenceCategory cat1 = new PreferenceCategory(mContext);
        cat1.setTitle("title");
        root.addPreference(cat1);

        ListPreference list1 = new ListPreference(mContext);
        list1.setTitle("Title");
        list1.setSummary("Summary");
        list1.setDialogTitle("Dialog Title");
        list1.setKey("your_key");

        CharSequence[] entries  = {"entry1","entry2"}; //or anything else that returns the right data
        list1.setEntries(entries);
        int length              = entries.length;
        CharSequence[] values   = new CharSequence[length];
        for (int i=0; i<length; i++){
            CharSequence val = ""+i+1+"";
            values[i] =  val;
        }
        list1.setEntryValues(values);

        cat1.addPreference(list1);

        return root;
    }//end method


}
