package com.phenom.erik.franqinterface.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;


import com.phenom.erik.franqinterface.R;
import com.phenom.erik.franqinterface.Util.Constants;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Erik on 2/22/2015.
 */
public class Words extends Fragment implements Constants,Button.OnClickListener {

    private Context mContext;

    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;

    private TextView textView,descriptionTextView;
    private Button resetButton,deleteButton;

    private String[] words;
    private String[] descriptions;

    private View mContainer;

    private int currentIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();
        words = getResources().getStringArray(R.array.words);
        descriptions = getResources().getStringArray(R.array.descriptions);


        mContainer = startGame(inflater);

        return mContainer;
    }


    private View startGame(LayoutInflater inflater) {

        View mContainer = inflater.inflate(R.layout.words_layout, null);

        deleteButton = (Button) mContainer.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);

        resetButton = (Button) mContainer.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);

        relativeLayout = (RelativeLayout) mContainer.findViewById(R.id.main);
        textView = new TextView(mContext);
        textView.setId(TextView.generateViewId());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,100);

        textView.setLayoutParams(params);

        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(46);
        textView.setTextColor(Color.parseColor(COLOR_BLACK));


        try{
            relativeLayout.addView(textView);
        }catch(Exception e){
            e.printStackTrace();
        }


        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.BELOW, textView.getId());

        final int index = new Random().nextInt(words.length);
        initLinearLayout(words[index], mContainer);

        descriptionTextView = (TextView) mContainer.findViewById(R.id.description);
        descriptionTextView.setText(descriptions[index]);

        currentIndex = index;

        try {
            if (linearLayout != null) {
                ViewGroup parent = (ViewGroup) linearLayout.getParent();
                if(parent != null) {
                    parent.removeView(linearLayout);
                }
            }
            assert linearLayout != null;
            relativeLayout.addView(linearLayout,relativeParams);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return mContainer;
    }

    private void initLinearLayout(String s, View mContainer) {

        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) set.add(Character.valueOf(c));

        linearLayout = (LinearLayout) mContainer.findViewById(R.id.buttons_layout);

        for (Character c : set) {
            Button button = new Button(mContext);
            button.setText(String.valueOf(c));
            button.setLayoutParams(new TableLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            button.setOnClickListener(this);
            linearLayout.addView(button);
        }

    }

    private void reset() {

        linearLayout.removeAllViews();
        textView.setText("");
        textView.setTextColor(Color.parseColor(COLOR_BLACK));

        final int index = new Random().nextInt(words.length);
        initLinearLayout(words[index], mContainer);

        descriptionTextView = (TextView) mContainer.findViewById(R.id.description);
        descriptionTextView.setText(descriptions[index]);

        currentIndex = index;

    }

    private void updateViews() {

        String textViewContent = textView.getText().toString();
        String word = words[currentIndex];

        for(int i = 0; i<linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);

            if(v instanceof Button) {
                Button b = (Button) v;
                String buttonContent = b.getText().toString();

                int occurrencesTextView = textViewContent.length() - textViewContent.replace(buttonContent, "").length();
                int occurrencesWord = word.length() - word.replace(buttonContent, "").length();

                if(occurrencesTextView == occurrencesWord) {
                    b.setEnabled(false);
                } else {
                    b.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

        Button pressedButton = (Button) v;

        String s = textView.getText().toString();

        if(pressedButton == deleteButton) {

            if (s == null || s.length() == 0) {
                return;
            } else {
                final String ss = s.substring(0, s.length() - 1);
                textView.setText(ss);
                textView.setTextColor(Color.parseColor(COLOR_BLACK));
                updateViews();
            }

        }else if(pressedButton == resetButton) {
            reset();
        } else {
            textView.append(pressedButton.getText());

            s = textView.getText().toString();

            if(s.equals(words[currentIndex])) {
                textView.setTextColor(Color.parseColor(COLOR_GREEN));
            }

            if(s.length() == words[currentIndex].length() && !s.equals(words[currentIndex])) {
                textView.setTextColor(Color.parseColor(COLOR_RED));
            }

            updateViews();
        }


    }
}
