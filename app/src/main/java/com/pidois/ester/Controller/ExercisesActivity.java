package com.pidois.ester.Controller;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.pidois.ester.R;
import com.pidois.ester.StrapActivity;

public class ExercisesActivity extends ExerciseAbstractClass implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // Button listeners
        findViewById(R.id.btn_ex1).setOnClickListener(this);
        findViewById(R.id.btn_ex2).setOnClickListener(this);
        findViewById(R.id.btn_ex3).setOnClickListener(this);
        findViewById(R.id.btn_ex4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_ex1){
            //todo
        } else if (i == R.id.btn_ex2){
            //todo
        } else if (i == R.id.btn_ex3){
            switchScreen(CognitiveActivity.class);
        } else if (i == R.id.btn_ex4){
            switchScreen(StrapActivity.class);
        }
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(ExercisesActivity.this, cl);
        ExercisesActivity.this.startActivity(intent);
    }
}

