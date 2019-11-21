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
    private int time;

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

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "IT";

                Log.i("AQUI MANDA SDATA","IT : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                DeviceControlActivity.enviarDescriptor();

                time = 0;

                chronometer.setBase(SystemClock.elapsedRealtime() + (10*1000));
                chronometer.start();

                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        time++;

                        Log.i("AQUI E O TIME","VALOR DO TEMPO : " + time);

                        if (time == 10){
                            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "GTR";

                            Log.i("AQUI MANDA SDATA","GTR MALUCO : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                            DeviceControlActivity.enviarDescriptor();
                            chronometer.stop();
                        }
                    }
                });
            }
        });

    }


    

}
