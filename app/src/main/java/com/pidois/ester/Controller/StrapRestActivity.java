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
import com.pidois.ester.R;

public class StrapRestActivity extends StrapUtils {

    private Chronometer chronometer;
    private Button button;
    private int time = 0;
    private String title, message;

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
                strapResult(grade, StrapRestActivity.class);
            }

        }
    };

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

                            //strapResult("1");
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



}
