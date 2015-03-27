package com.phenom.erik.franqinterface.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.phenom.erik.franqinterface.R;
import com.phenom.erik.franqinterface.Util.Constants;
import com.phenom.erik.franqinterface.Util.Helpers;

import java.util.Random;

/**
 * Created by Erik on 3/17/2015.
 */
public class Chance extends Fragment implements Constants, Button.OnClickListener {

    private String[] questions;
    private int index = -1;

    private Context mContext;

    private String correct;

    private Button resetButton;
    private Button fiftyButton;
    private View globalContainer;


    private TextView textPoints;
    private int intPoints = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chance_layout, root, false);

        mContext = getActivity();

        resetButton = (Button) view.findViewById(R.id.resetChance);
        resetButton.setOnClickListener(this);

        fiftyButton = (Button) view.findViewById(R.id.fiftyChance);
        fiftyButton.setOnClickListener(this);

        questions = getResources().getStringArray(R.array.questions);
        textPoints = (TextView) view.findViewById(R.id.pointsChance);
        textPoints.setTextColor(Color.parseColor(COLOR_BLUE));

        globalContainer = view;
        draw(globalContainer);
        view = globalContainer;

        return view;
    }

    private void draw(View mContainer) {

        if(index + 1 >= questions.length) {
            reset();
        } else {
            index++;
        }

        textPoints.setText(Integer.toString(intPoints));

        TextView textView = (TextView) mContainer.findViewById(R.id.question);

        textView.setText(questions[index]);

        int resID = mContext.getResources().getIdentifier("question" + Integer.toString(index),"array",mContext.getPackageName());
        String[] options = getResources().getStringArray(resID);
        correct = options[0];

        setButtonsText(options,mContainer);
    }

    private void setButtonsText(String[] options, View mContainer) {

        int[] solutions = { 1, 2, 3, 4 };
        Helpers.shuffleArray(solutions);

        Button button = null;

        for(int i = 0; i<4; i++) {
            switch (i) {
                case 0:
                    button = (Button) mContainer.findViewById(R.id.option1);
                    break;
                case 1:
                    button = (Button) mContainer.findViewById(R.id.option2);
                    break;
                case 2:
                    button = (Button) mContainer.findViewById(R.id.option3);
                    break;
                case 3:
                    button = (Button) mContainer.findViewById(R.id.option4);
                    break;
            }

            button.setEnabled(true);
            button.setOnClickListener(this);
            button.setText(options[solutions[i] - 1]);

        }
    }

    private void split(View mContainer) {

        Button button = null;
        short fifty = 0;

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    button = (Button) mContainer.findViewById(R.id.option1);
                    break;
                case 1:
                    button = (Button) mContainer.findViewById(R.id.option2);
                    break;
                case 2:
                    button = (Button) mContainer.findViewById(R.id.option3);
                    break;
                case 3:
                    button = (Button) mContainer.findViewById(R.id.option4);
                    break;
            }

            if(!button.getText().toString().equals(correct) && fifty < 2) {
                fifty++;
                button.setEnabled(false);
            }
        }
    }

    private void reset() {
        fiftyButton.setEnabled(true);
        index = 0;
        intPoints = 10;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        if (b == resetButton) {
            reset();
            draw(globalContainer);
            return;
        }

        if (b == fiftyButton) {

            if(intPoints < 5) {
                Toast.makeText(mContext, "Not Enough Points !", Toast.LENGTH_SHORT).show();
                return;
            } else {
                intPoints-=5;
                textPoints.setText(Integer.toString(intPoints));
                split(globalContainer);
                fiftyButton.setEnabled(false);
            }
            return;
        }

        if(b.getText().toString().equals(correct)) {
            fiftyButton.setEnabled(true);
            Toast.makeText(mContext, "CORRECT !", Toast.LENGTH_SHORT).show();
            intPoints+=10;
            if(index + 1 == questions.length) {
                changeFragment();
                reset();
                return;
            }
            draw(globalContainer);
        } else {
            intPoints-=5;
            textPoints.setText(Integer.toString(intPoints));
            Toast.makeText(mContext, "WRONG !", Toast.LENGTH_SHORT).show();

            if (intPoints < 0) {
                changeFragment();
                reset();
                return;
            }
        }
    }

    private void changeFragment() {

        android.app.Fragment firstFragment = null;
        ChanceOver chanceOver = new ChanceOver();
        chanceOver.setPoints(intPoints);
        firstFragment = chanceOver;
        chanceOver = null;

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
    }
}
