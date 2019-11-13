package com.pidois.ester.Controller;

import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.pidois.ester.R;

import java.util.UUID;

public class ExercisesActivity extends ExerciseAbstractClass implements View.OnClickListener {

    BluetoothLeService mbls;



    public BluetoothGattService service;
    public BluetoothGattCharacteristic mGattCharacteristic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // Button listeners
        findViewById(R.id.btn_ex1).setOnClickListener(this);
        findViewById(R.id.btn_ex2).setOnClickListener(this);
        findViewById(R.id.btn_ex3).setOnClickListener(this);
        findViewById(R.id.btn_ex4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        BluetoothLeService bls = new BluetoothLeService();


        int i = view.getId();
        if (i == R.id.btn_ex1){
            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "M1";
            mbls = new BluetoothLeService();



            service = mbls.mBluetoothGatt.getService(UUID.fromString("c96d9bcc-f3b8-442e-b634-d546e4835f64"));
            mGattCharacteristic = service.getCharacteristic(UUID.fromString("807b8bad-a892-4ff7-b8bc-83a644742f9b"));
            Log.i("%$%$#$#$$#%$%#$#$#$@#","EXERCICIO 1 SELECIONADO: " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);

            Log.i("VALOR DA CARACT","VALORRRRRRRRRR" + mGattCharacteristic);

            BluetoothGattDescriptor descriptor = mGattCharacteristic.getDescriptor(
                    BluetoothLeService.UUID_CLIENT_CHARACTERISTIC_CONFIG);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mbls.mBluetoothGatt.writeDescriptor(descriptor);

            switchScreen(ExerciseSoundActivity.class);
        } else if (i == R.id.btn_ex2){
            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "M2";
            switchScreen(ExerciseColorActivity.class);
        } else if (i == R.id.btn_ex3){
            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "M3";
            switchScreen(CognitiveActivity.class);
        } else if (i == R.id.btn_ex4){
            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "M4";
            switchScreen(StrapActivity.class);
        }
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(ExercisesActivity.this, cl);
        ExercisesActivity.this.startActivity(intent);
    }
}

