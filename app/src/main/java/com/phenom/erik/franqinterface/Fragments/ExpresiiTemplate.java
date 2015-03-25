package com.phenom.erik.franqinterface.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.phenom.erik.franqinterface.R;
import com.phenom.erik.franqinterface.Util.Constants;

import java.io.File;
import java.io.IOException;

/**
 * Created by Erik on 3/24/2015.
 */
public class ExpresiiTemplate extends Fragment implements Constants, Button.OnClickListener {

    private ImageView imageView;
    private Button button;
    private int resId;
    private String title;

    private Context context;

    public void init(int resID, String title) {
        this.resId = resID;
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expresii_template_layout, root, false);

        imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(resId);

        button = (Button) view.findViewById(R.id.soundButton);
        button.setOnClickListener(this);

        context = getActivity();

        return view;
    }

    public void playSound() {

        int soundID;
        int maxN = 7;

        for(int i = 1; i<=maxN; i++) {
            String name = title + Integer.toString(i);
            soundID = getResources().getIdentifier(name,"raw",context.getPackageName());

            if(soundID == 0) break;

            MediaPlayer mediaPlayer = MediaPlayer.create(context,soundID);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == button) { playSound(); }
    }
}
