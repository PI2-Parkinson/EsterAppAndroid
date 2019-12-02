package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.pidois.ester.R;

import java.util.ArrayList;

public class ExerciseColorActivity extends ExercisesActivity {

    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String data;
    private String levelBd = null;
    private Button buttonStart, buttonStop, buttonDemo, btn_help;

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("RECEBE DA ESP32","VALOR: " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));


            data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
            Log.i("DA ESP32 PRA VARIAVEL","VALOR VARIAVEL: " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);

            if (data.contains("SQ")){

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SF";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();

            }
            if (data.contains("ES")){
                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "C0";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();
            }

            if (data.contains("SR")){

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SF";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();

            }

            if (data.contains("V2")){
                levelBd = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA;

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SN";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();

            }

            if (data.contains("BP")){
                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "P0";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();
            }


            if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("N2")){

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "N201";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
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
        setContentView(R.layout.activity_exercise_color);

        final Chronometer exec_chronometer = findViewById(R.id.exec_color_chronometer);


        final Button buttonStart = findViewById(R.id.exec_color_btn_start);
        final Button buttonStop = findViewById(R.id.exec_color_btn_stop);
        final Button btn_help = findViewById(R.id.btn_help);

        buttonStop.setVisibility(View.INVISIBLE);
        buttonStart.setVisibility(View.VISIBLE);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SR";

                Log.i("AQUI MANDA SDATA","CONEXAO ESTABELECIDA : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();

                buttonStart.setVisibility(View.GONE);
                buttonStop.setVisibility(View.VISIBLE);
                exec_chronometer.setBase(SystemClock.elapsedRealtime());
                exec_chronometer.start();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "P1";
            Log.i("AQUI MANDA SDATA","MANDOU P1 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
            BluetoothLeService.enviarDescriptor();
            buttonStart.setVisibility(View.VISIBLE);
            buttonStop.setVisibility(View.GONE);
            alertDialog();
            exec_chronometer.stop();
            }
        });

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogInfo();
            }
        });

    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Você deseja mesmo encerrar o exercício? Todo o progresso não será salvo.");
        dialog.setTitle("Deseja mesmo sair?");
        dialog.setPositiveButton("sair",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SN";
                        Log.i("AQUI MANDA SDATA","CONEXAO ESTABELECIDA : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                        BluetoothLeService.enviarDescriptor();
                        finish();
                    }
                });
        dialog.setNegativeButton("cancelar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
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
        Intent intent = new Intent(ExerciseColorActivity.this, cl);
        ExerciseColorActivity.this.startActivity(intent);
    }
}
