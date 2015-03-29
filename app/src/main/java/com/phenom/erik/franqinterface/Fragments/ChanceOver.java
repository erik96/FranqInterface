package com.phenom.erik.franqinterface.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phenom.erik.franqinterface.R;
import com.phenom.erik.franqinterface.Util.Constants;

/**
 * Created by Erik on 3/23/2015.
 */
public class ChanceOver extends Fragment implements Constants {

    int points;

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gameover_layout, root, false);

        TextView gameOver = (TextView) view.findViewById(R.id.gameOver);
        TextView score = (TextView) view.findViewById(R.id.chanceScore);

        if (points < 0) {
            gameOver.setText("Reprends le jeu !");
        } else {
            gameOver.setText("Fin !");
            score.setText(Integer.toString(points));
        }

        return view;
    }
}
