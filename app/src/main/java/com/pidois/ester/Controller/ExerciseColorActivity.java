package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.pidois.ester.R;

public class ExerciseColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_color);

        final Chronometer exec_chronometer = findViewById(R.id.exec_color_chronometer);

        final Button buttonStart = findViewById(R.id.exec_color_btn_start);
        final Button buttonStop = findViewById(R.id.exec_color_btn_stop);

        buttonStop.setVisibility(View.INVISIBLE);

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
}
