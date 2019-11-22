package com.pidois.ester.Controller;


import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.pidois.ester.R;

public class StrapFingerNoseActivity extends StrapUtils {

    private Chronometer chronometer;
    private Button button;
    private int time = 0;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap_finger_nose);

        calibrate();

        chronometer = findViewById(R.id.strap_finger_chronometer);
        button = findViewById(R.id.strap_finger_btn);

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
                            //sendStrapAsnwer(firebaseUser, databaseReference, "finger_nose", scale);
                        }
                    }
                });
            }
        });

    }
}
