package com.pidois.ester.Controller;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import com.pidois.ester.R;

public class StrapRestActivity extends StrapUtils {

    private Chronometer chronometer;
    private Button button;
    private int time = 0;
    private String title, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap_rest);

        calibrate();

        chronometer = findViewById(R.id.strap_rest_chronometer);
        button = findViewById(R.id.strap_rest_btn);

        chronometer.setCountDown(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "IT";

                Log.i("AQUI MANDA SDATA","IT : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();
                chronometer.setBase(SystemClock.elapsedRealtime() + (11*1000));
                chronometer.start();

                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        time++;
                        if (time == 11){
                            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "GTR";

                            Log.i("AQUI MANDA SDATA","GTR MALUCO : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                            BluetoothLeService.enviarDescriptor();
                            chronometer.stop();

                            //strapResult(1);
                        }
                    }
                });
            }
        });



    }


    

}
