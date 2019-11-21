package com.pidois.ester.Controller;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.pidois.ester.R;

public class StrapPosturalActivity extends StrapUtils {

    private Chronometer chronometer;
    private Button button;
    private int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap_postural);

        chronometer = findViewById(R.id.strap_postural_chronometer);
        button = findViewById(R.id.strap_postural_btn);

        chronometer.setCountDown(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime() + (10*1000));
                chronometer.start();

                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        time++;
                        if (time == 10){
                            chronometer.stop();
                        }
                    }
                });
            }
        });
    }
}
