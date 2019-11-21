package com.pidois.ester.Controller;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import com.pidois.ester.R;

public class StrapRestActivity extends StrapUtils {

    private Chronometer chronometer;
    private Button button;
    private int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap_rest);

        chronometer = findViewById(R.id.strap_rest_chronometer);
        button = findViewById(R.id.strap_rest_btn);

        chronometer.setCountDown(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime() + (11*1000));
                chronometer.start();

                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        time++;
                        if (time == 11){
                            chronometer.stop();

                            strapResult(1);
                        }
                    }
                });
            }
        });



    }


    

}
