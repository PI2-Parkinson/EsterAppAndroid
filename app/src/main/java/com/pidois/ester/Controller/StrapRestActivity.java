package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pidois.ester.R;

import java.util.Locale;

public class StrapRestActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private Button button;
    private int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap_rest);

        chronometer = findViewById(R.id.strap_rest_chronometer);
        button = findViewById(R.id.strap_rest_btn);

        chronometer.setBase(SystemClock.elapsedRealtime() + (11*1000));
        chronometer.setCountDown(true);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                time++;
                if (time == 10){
                    chronometer.stop();
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.start();
            }
        });

    }


    

}
