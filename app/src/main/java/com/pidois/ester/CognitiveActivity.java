package com.pidois.ester;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Random;


public class CognitiveActivity extends ExerciseAbstractClass implements View.OnClickListener {

    public static final String RED = "#FF0000";
    public static final String BLUE = "#0000FF";
    public static final String GREEN = "#00FF00";
    public static final String YELLOW = "#FFFF00";
    public static final String ORANGE = "#FF8000";
    public static final String PURPLE = "#9900FF";
    public static final String PINK = "#FF00AA";
    public static final String BROWN = "#B36800";
    public static final String WHITE = "#FFFFFF";
    public static final String GRAY = "#808080";
    public static final String BLACK = "#000000";

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private TextView mText = null;
    private int time = 0;
    private int answers = 0;
    private int correctAnswers = 0;
    ArrayList<Integer> randomColorArray = new ArrayList<>();
    ArrayList<Integer> randomButtonArray = new ArrayList<>();


    Random random = new Random();

    private String[] colorName = {"vermelho", "azul",
            "amarelo", "verde",
            "laranja", "roxo",
            "rosa", "marrom",
            "branco", "cinza"
    };
    private String[] colorHex = {RED, BLUE,
            YELLOW, GREEN,
            ORANGE, PURPLE,
            PINK, BROWN,
            WHITE, GRAY
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognitive);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

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
        TableRow tableRow = findViewById(R.id.tableRow);
        TableRow chronometerTableRow = findViewById(R.id.chronometerTableRow);
        mText = new TextView(this);
        lView.removeAllViews();
        tableRow.removeAllViews();
        tableRow.removeAllViewsInLayout();
        chronometerTableRow.removeAllViews();
        mText.setText("Fim do exercício");
        mText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        lView.setBackgroundColor(Color.parseColor(WHITE));
        lView.addView(mText);
        showRightAnswers();
    }

    public void showSequence(int[] color){


        LinearLayout lView = findViewById(R.id.linearlayout);


        mText = new TextView(this);
        mText.setText(colorName[color[0]]);
        mText.setTextColor(Color.parseColor(colorHex[color[1]]));
        mText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
        mText.setBackgroundColor(Color.parseColor(BLACK));

        lView.addView(mText);

    }

    public void generateButtons(int[] color){
        randomButtonArray = new ArrayList<>();
        int randomColor = random.nextInt(10);

        randomColorArray.add(R.id.leftButton);
        randomColorArray.add(R.id.centerButton);
        randomColorArray.add(R.id.rightButton);

        int randomArray = random.nextInt((randomColorArray.size()));
        Log.i("generateButtons","randomArraySize " + randomColorArray.size());
        Log.i("generateButtons","randomArray " + randomArray);

        int leftButton = randomColorArray.get(randomArray);
        randomColorArray.remove(new Integer(leftButton));
        randomButtonArray.add(leftButton);
        randomArray = random.nextInt((randomColorArray.size()));
        Log.i("generateButtons","randomArraySize " + randomColorArray.size());
        Log.i("generateButtons","randomArray " + randomArray);
        Log.i("generateButtons","leftButton " + leftButton + " id " + R.id.leftButton);


        int centerButton = randomColorArray.get(randomArray);
        randomColorArray.remove(new Integer(centerButton));
        randomButtonArray.add(centerButton);
        randomArray = 0;
        Log.i("generateButtons","randomArraySize " + randomColorArray.size());
        Log.i("generateButtons","randomArray " + randomArray);
        Log.i("generateButtons","centerButton " + centerButton);

        int rightButton = randomColorArray.get(randomArray);
        randomColorArray.remove(new Integer(rightButton));
        randomButtonArray.add(rightButton);

        Log.i("generateButtons","rightButton " + rightButton);

        Button buttonLeft = findViewById(leftButton);
        buttonLeft.setBackgroundColor(Color.parseColor(colorHex[color[0]]));
        buttonLeft.setOnClickListener(this);

        Button buttonCenter = findViewById(centerButton);
        buttonCenter.setBackgroundColor(Color.parseColor(colorHex[color[1]]));
        buttonCenter.setOnClickListener(this);

        Button buttonRight = findViewById(rightButton);

        if(colorHex[randomColor] != colorHex[color[0]] &&
                colorHex[randomColor] != colorHex[color[1]]){

            buttonRight.setBackgroundColor(Color.parseColor(colorHex[randomColor]));

        } else {

            while(colorHex[randomColor] == colorHex[color[0]] ||
                    colorHex[randomColor] == colorHex[color[1]]) {
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

        if (i == randomButtonArray.get(0)){
            time = 0;
            lView.removeAllViewsInLayout();
            lView.addView(mText);
            showLayout();
            countAnswers();
            countRightAnswers();
            Log.i("onClick", "randomButtonArray " + randomButtonArray.size());
            Log.i("onClick", "randomColorArray " + randomColorArray.size());
        } else if (i == randomButtonArray.get(1) || i == randomButtonArray.get(2)){
            time = 0;
            lView.removeAllViewsInLayout();
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

    public void showRightAnswers(){
        database.child("answers").child("rightAnswers").setValue(correctAnswers);
        database.child("answers").child("totalAnswers").setValue(answers);
        database.child("answers").child("wrongAnswers").setValue(answers - correctAnswers);
    }
}
