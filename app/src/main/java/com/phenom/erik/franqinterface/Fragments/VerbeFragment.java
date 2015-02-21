package com.phenom.erik.franqinterface.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
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
 * Created by Raul on 2/21/2015.
 */
public class VerbeFragment extends PreferenceFragment implements Constants {

    private Context mContext;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mContext = getActivity();

        this.setPreferenceScreen(createPreferenceHierarchy());
    }

    public PreferenceScreen createPreferenceHierarchy(){

        String name1;

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(mContext);

        PreferenceCategory cat1 = new PreferenceCategory(mContext);
        cat1.setTitle(name);
        root.addPreference(cat1);

        File dir = new File(Environment.getExternalStorageDirectory()+"/FranqInterface/" + name);

        for (File f1 : dir.listFiles()) {
            if (f1.isFile()) {
                name1 = f1.getName();

                Preference pref = new Preference(mContext);
                pref.setTitle(name1); //verb
                pref.setKey(name); //timp
                pref.setIcon(R.drawable.ic_action_view_as_list);
                cat1.addPreference(pref);
            }
        }

        return root;
    }//end method

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        if(preference != null) {
            String title = (String) preference.getTitle();
            String key = (String) preference.getKey();
            showDialog(title,key);
        }
        return true;
    }

    private void showDialog(String title, String key) {

        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.table_dialog);

        String mp3Path = Environment.getExternalStorageDirectory()+"/FranqInterface/audio/" + title + ".mp3";

        if(new File(mp3Path).exists()) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(mp3Path);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // open the file for reading
            InputStream instream = new FileInputStream(Environment.getExternalStorageDirectory()+"/FranqInterface/" + key + "/" + title);

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
                    //Log.d(TAG, line);

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
}
