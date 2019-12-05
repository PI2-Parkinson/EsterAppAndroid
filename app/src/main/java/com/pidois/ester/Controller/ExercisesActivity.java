package com.pidois.ester.Controller;

import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.pidois.ester.R;

import java.util.UUID;

public class ExercisesActivity extends ExerciseAbstractClass implements View.OnClickListener {

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

        int i = view.getId();
        if (i == R.id.btn_ex1){
            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "M1";
            BluetoothLeService.enviarDescriptor();
//            DeviceControlActivity.getCharacteristic();
            Log.i("%$%$#$#$$#%$%#$#$#$@#","EXERCICIO 1 SELECIONADO: " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);

            switchScreen(ExerciseSoundActivity.class);

        } else if (i == R.id.btn_ex2){
            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "M2";
            BluetoothLeService.enviarDescriptor();
            Log.i("%$%$#$#$$#%$%#$#$#$@#","EXERCICIO 2 SELECIONADO: " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);

            switchScreen(ExerciseColorActivity.class);

        } else if (i == R.id.btn_ex3){
            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "M3";
            BluetoothLeService.enviarDescriptor();
            Log.i("%$%$#$#$$#%$%#$#$#$@#","EXERCICIO 3 SELECIONADO: " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);

            switchScreen(CognitiveActivity.class);

        } else if (i == R.id.btn_ex4){
            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "M4";
            BluetoothLeService.enviarDescriptor();
            Log.i("%$%$#$#$$#%$%#$#$#$@#","EXERCICIO 4 SELECIONADO: " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);

            switchScreen(StrapActivity.class);
        }
    }
    private void switchScreen (Class cl){
        Intent intent = new Intent(ExercisesActivity.this, cl);
        ExercisesActivity.this.startActivity(intent);
    }

    public void alertDialogInfo() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Instruções do exercício");
        dialog.setMessage("\nClique em COMEÇAR para dar início ao exercício.\n" +
                "\n Fique atento às cores que acenderão nos LEDs.\n" +
                "\n Clique nos botões correspondentes dos dois controladores. \n" +
                "\n Os cliques não precisam ser simultâneos.");
        dialog.setCancelable(false);
        dialog.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}

