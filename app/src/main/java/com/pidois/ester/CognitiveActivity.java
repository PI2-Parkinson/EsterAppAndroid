package com.pidois.ester;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class CognitiveActivity extends ExerciseAbstractClass implements View.OnClickListener {

    private TextView mText = null;
    private int time = 0;
    private int answers = 0;
    private int correctAnswers = 0;


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
            "#FFFFFF", "#808080"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognitive);
        Chronometer simpleChronometer = findViewById(R.id.simpleChronometer);
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            public void onChronometerTick(Chronometer chronometer) {

                int timeElapsed = (int) (SystemClock.elapsedRealtime() - chronometer.getBase());

                int hours = timeElapsed / 3600000;
                int minutes = (timeElapsed - hours * 3600000) / 60000;

                Log.i("CognitiveActivity",minutes + " minutos " + time + " segundos");

                if (time == 10) {
                    time = 0;
                    LinearLayout lView = findViewById(R.id.linearlayout);
                    lView.removeAllViewsInLayout();
                    showLayout();
                    countAnswers();

                } else if(minutes == 1){
                    endGame();
                    chronometer.stop();
                }
                time++;
            }
        });
        simpleChronometer.start();

        int[] color = generateRandomSequence();

        showSequence(color);
        generateButtons(color);


    }

    public int[] generateRandomSequence(){
        int[] color = {0,0};
        int randomColorName = random.nextInt(10);
        int randomColorHex = random.nextInt(10);
        while(randomColorName == randomColorHex){
            randomColorHex = random.nextInt(10);
        }

        color[0] = randomColorName;
        color[1] = randomColorHex;

        return color;
    }

    public void endGame(){
        LinearLayout lView = findViewById(R.id.linearlayout);
        mText = new TextView(this);
        lView.removeAllViewsInLayout();
        mText.setText("Fim do exercício");
        mText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
        lView.addView(mText);
    }

    public void showSequence(int[] color){


        LinearLayout lView = findViewById(R.id.linearlayout);


        mText = new TextView(this);
        mText.setText(colorName[color[0]]);
        mText.setTextColor(Color.parseColor(colorHex[color[1]]));
        mText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
        mText.setBackgroundColor(Color.parseColor("#000000"));

        lView.addView(mText);

    }

    public void generateButtons(int[] color){
        int randomColor = random.nextInt(10);

        Button buttonLeft = findViewById(R.id.leftButton);
        buttonLeft.setBackgroundColor(Color.parseColor(colorHex[color[0]]));
        buttonLeft.setOnClickListener(this);

        Button buttonCenter = findViewById(R.id.centerButton);
        buttonCenter.setBackgroundColor(Color.parseColor(colorHex[color[1]]));
        buttonCenter.setOnClickListener(this);

        Button buttonRight = findViewById(R.id.rightButton);

        if(colorHex[randomColor] != colorHex[color[0]] &&
                colorHex[randomColor] != colorHex[color[0]]){

            buttonRight.setBackgroundColor(Color.parseColor(colorHex[randomColor]));

        } else {

            while(colorHex[randomColor] == colorHex[color[0]] ||
                    colorHex[randomColor] == colorHex[color[0]]) {
                randomColor = random.nextInt(10);
            }


            buttonRight.setBackgroundColor(Color.parseColor(colorHex[randomColor]));
        }

        buttonRight.setOnClickListener(this);
    }

    public void showLayout(){
        int[] color = generateRandomSequence();

        showSequence(color);
        generateButtons(color);
    }

    @Override
    public void onClick(View view) {
        LinearLayout lView = findViewById(R.id.linearlayout);
        mText = new TextView(this);
        int i = view.getId();

        if (i == R.id.leftButton){
            time = 0;
            lView.removeAllViewsInLayout();
            mText.setText("Acertou!");
            lView.addView(mText);
            showLayout();
            countAnswers();
            countRightAnswers();
        } else if (i == R.id.centerButton){
            time = 0;
            lView.removeAllViewsInLayout();
            mText.setText("Errou!");
            lView.addView(mText);
            showLayout();
            countAnswers();
        } else if (i == R.id.rightButton){
            time = 0;
            lView.removeAllViewsInLayout();
            mText.setText("Errou!");
            lView.addView(mText);
            showLayout();
            countAnswers();
        }
    }

    public int countAnswers(){
        answers += 1;
        Log.i("Total of answers", answers + " total answers");
        return answers;
    }

    public int countRightAnswers(){
        correctAnswers += 1;
        Log.i("Right answers", answers + " right answers");

        return correctAnswers;
    }

    public int showRightAnswers(int answers){
        return answers;
    }
}
