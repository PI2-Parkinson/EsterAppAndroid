package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.pidois.ester.R;

import java.util.UUID;

public class ExerciseSoundActivity extends AppCompatActivity {

    //BluetoothGattService service;
    //public BluetoothGattCharacteristic mGattCharacteristic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_sound);

        final Chronometer exec_chronometer = (Chronometer)findViewById(R.id.exec_sound_chronometer);

        final Button buttonStart = (Button)findViewById(R.id.exec_sound_btn_start);
        final Button buttonStop = (Button)findViewById(R.id.exec_sound_btn_stop);

        buttonStop.setVisibility(View.INVISIBLE);

        Log.i("OLHA O RDATA MLK DOIDO","TOMAAAAAAAAA : " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);

        if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("N1")){

            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "N101";
            Log.i("AQUI MANDA SDATA","NIVEL JOGO 1 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
            DeviceControlActivity.enviarDescriptor();
        }

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SR";

                DeviceControlActivity.enviarDescriptor();
                DeviceControlActivity merda = new DeviceControlActivity();
                Log.i("AQUI MANDA SDATA","SR FOI : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);

                buttonStart.setVisibility(View.GONE);
                buttonStop.setVisibility(View.VISIBLE);
                exec_chronometer.setBase(SystemClock.elapsedRealtime());
                exec_chronometer.start();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonStart.setVisibility(View.VISIBLE);
                buttonStop.setVisibility(View.GONE);
                alertDialog();
                exec_chronometer.stop();
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

                        DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SF";

                        DeviceControlActivity.enviarDescriptor();
                        DeviceControlActivity merda = new DeviceControlActivity();

                        Log.i("%$%$#$#$$#%$%#$#$#$@#","SF FOI: " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
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
}
