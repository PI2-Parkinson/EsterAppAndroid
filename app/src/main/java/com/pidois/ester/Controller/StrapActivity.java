package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pidois.ester.R;


public class StrapActivity extends StrapUtils implements View.OnClickListener {

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

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap);

        // Button listeners
        findViewById(R.id.btn_m1).setOnClickListener(this);
        findViewById(R.id.btn_m2).setOnClickListener(this);
        findViewById(R.id.btn_m3).setOnClickListener(this);
        findViewById(R.id.btn_m4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_m1){

            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "MT1";

            Log.i("AQUI MANDA SDATA","MT1 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
            BluetoothLeService.enviarDescriptor();

            switchScreen(StrapRestActivity.class);
        } else if (i == R.id.btn_m2){

            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "MT2";

            Log.i("AQUI MANDA SDATA","MT2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
            BluetoothLeService.enviarDescriptor();
            switchScreen(StrapPosturalActivity.class);
        } else if (i == R.id.btn_m3){

            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "MT3";

            Log.i("AQUI MANDA SDATA","MT3 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
            BluetoothLeService.enviarDescriptor();
            switchScreen(StrapFingerNoseActivity.class);
        } else if (i == R.id.btn_m4){
            String title = null;
            String message = "A medição do tremor da mão é realizada de acordo com a Escala de Classificação de Doenças de Parkinson Unificada (Unified Parkinson’s Disease Rating Scale - UPDRS).\n" +
                    "\nEssa forma de avaliação é patrocinada pela Sociedade de Distúrbios do Movimento (Movement Disorders Society - MDS), e possui classificação dos tremores de 0 a 4, sendo eles: normal, discreto, ligeiro, moderado e grave. Para iniciar o teste é necessário colocar a pulseira no punho e escolher o modo.";
            alertDialog(title, message);
        }
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
        Intent intent = new Intent(StrapActivity.this, cl);
        StrapActivity.this.startActivity(intent);
    }

}
