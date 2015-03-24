package com.phenom.erik.franqinterface.Fragments;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.phenom.erik.franqinterface.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by Erik on 3/24/2015.
 */
public class ExpresiiTemplate extends Fragment implements Button.OnClickListener {

    private ImageView imageView;
    private Button button;
    private int resId;
    private String title;

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

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v == button) {
            String mp3Path = Environment.getExternalStorageDirectory()+"/FranqInterface/audio/" + title + ".mp3";

            if(new File(mp3Path).exists()) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(mp3Path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }
}
