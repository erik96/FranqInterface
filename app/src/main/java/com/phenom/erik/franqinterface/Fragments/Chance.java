package com.phenom.erik.franqinterface.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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
    private Context mContext;

    private String correct;

    private Button resetButton;
    private View globalContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chance_layout, root, false);

        mContext = getActivity();

        resetButton = (Button) view.findViewById(R.id.resetChance);
        resetButton.setOnClickListener(this);

        globalContainer = view;
        draw(globalContainer);
        view = globalContainer;

        return view;
    }

    private void draw(View mContainer) {

        questions = getResources().getStringArray(R.array.questions);
        TextView textView = (TextView) mContainer.findViewById(R.id.question);
        final int index = new Random().nextInt(questions.length);
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

            button.setOnClickListener(this);
            button.setText(options[solutions[i] - 1]);

        }

    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        if (b == resetButton) {
            draw(globalContainer);
            return;
        }

        if(b.getText().toString().equals(correct)) {
            Toast.makeText(mContext, "CORRECT !", Toast.LENGTH_SHORT).show();
            draw(globalContainer);
        } else {
            Toast.makeText(mContext, "WRONG !", Toast.LENGTH_SHORT).show();
        }
    }
}
