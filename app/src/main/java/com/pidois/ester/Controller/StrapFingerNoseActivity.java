package com.pidois.ester.Controller;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String data;
    private int grade = 0;
    private String GTValue = null;

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("RECEBE DA ESP32","VALOR: " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));


            data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
            Log.i("DA ESP32 PRA VARIAVEL","VALOR VARIAVEL: " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);

            if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("GT")){
                GTValue = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA;
                grade = Character.getNumericValue(GTValue.charAt(2));
                Log.i("StrapFingerNoseActivity","Grau tremor : " + grade);
                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "GTR";
                Log.i("StrapFingerNoseActivity","Grau tremor : " + GTValue);
                BluetoothLeService.enviarDescriptor();
                strapResult(grade, StrapFingerNoseActivity.class);
            }
        }
    };

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

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        mBluetoothLeService = DeviceControlActivity.serviceBLE;
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d("BLA", "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(StrapFingerNoseActivity.this, cl);
        StrapFingerNoseActivity.this.startActivity(intent);
    }
}
