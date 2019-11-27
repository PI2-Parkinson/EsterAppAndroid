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

public class StrapPosturalActivity extends StrapUtils {

    private Chronometer chronometer;
    private Button button;
    private int time = 0;

    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String data;
    private String levelBd = null;


    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("RECEBE DA ESP32","VALOR: " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));

            data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
            Log.i("DA ESP32 PRA VARIAVEL","VALOR VARIAVEL: " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);


            if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("GT")){
                String a = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA;
                strapResult(a);
                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "GTR";
                Log.i("AQUI MANDA SDATA","GRAU TREMOR : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();
            }

            if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("QM")){

                switchScreen(MainActivity.class);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap_postural);

        calibrate();

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

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(StrapPosturalActivity.this, cl);
        StrapPosturalActivity.this.startActivity(intent);
    }
}
