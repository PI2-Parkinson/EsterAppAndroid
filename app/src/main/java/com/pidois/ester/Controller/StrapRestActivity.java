package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

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

    Chronometer strapChronometer;
    private int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap_rest);

        strapChronometer = findViewById(R.id.strapChronometer);
        strapChronometer.setBase(SystemClock.elapsedRealtime());
        strapChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            public void onChronometerTick(Chronometer chronometer) {

                int timeElapsed = (int) (SystemClock.elapsedRealtime() - chronometer.getBase());

                int hours = timeElapsed / 3600000;
                int minutes = (timeElapsed - hours * 3600000) / 60000;

                Log.i("CognitiveActivity",minutes + " minutos " + time + " segundos");

                if (time == 10) {
                    chronometer.stop();
                } else {
                    // nothing to do
                }
                time++;
            }
        });
        strapChronometer.start();
    }


    

}
