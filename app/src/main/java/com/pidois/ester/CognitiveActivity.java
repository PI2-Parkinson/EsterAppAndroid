package com.pidois.ester;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class CognitiveActivity extends ExerciseAbstractClass {

    private TextView mText = null;

    Random random = new Random();

    private String[] colorName = {"vermelho", "azul",
            "amarelo", "verde",
            "laranja", "roxo",
            "rosa", "marrom",
            "branco", "cinza"
    };
    private String[] colorHex = {"#FF0000", "#0000FF",
            "#FFFF00", "#008000",
            "#FFA500", "#800080",
            "#FF0081", "#8B4513",
            "#FFFFFF", "#808080"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognitive);

        generateSequence(colorName, colorHex);

    }

    public void generateSequence (String[] colorName, String[] colorHex){
        int randomColorName = random.nextInt(10);
        int randomColorHex = random.nextInt(10);
        while(randomColorName == randomColorHex){
            randomColorHex = random.nextInt(10);
        }

        LinearLayout lView = findViewById(R.id.linearlayout);


        mText = new TextView(this);
        mText.setText(colorName[randomColorName]);
        mText.setTextColor(Color.parseColor(colorHex[randomColorHex]));

        lView.addView(mText);

    }

    public int countAnswers(int answer){
        return answer;
    }

    public int showRightAnswers(int answers){
        return answers;
    }
}
