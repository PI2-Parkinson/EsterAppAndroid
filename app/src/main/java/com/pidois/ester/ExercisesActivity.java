package com.pidois.ester;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class ExercisesActivity extends AppCompatActivity implements View.OnClickListener {

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
            //todo
        }
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(ExercisesActivity.this, cl);
        ExercisesActivity.this.startActivity(intent);
    }
}
