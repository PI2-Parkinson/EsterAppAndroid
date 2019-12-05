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

import com.pidois.ester.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button listeners
        findViewById(R.id.btn_conectar).setOnClickListener(this);
        findViewById(R.id.btn_exerc).setOnClickListener(this);
        findViewById(R.id.btn_perfil).setOnClickListener(this);
        findViewById(R.id.btn_dicas).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_conectar){
            switchScreen(DeviceScanActivity.class);
        } else if (i == R.id.btn_exerc){
            Log.i("HOLA", ""+DeviceControlActivity.mConnected);
            if(!DeviceControlActivity.mConnected){
                    alertDialog();
            } else {
                switchScreen(ExercisesActivity.class);
            }

        } else if (i == R.id.btn_perfil){
            switchScreen(ProfileActivity.class);
        } else if (i==R.id.btn_dicas){
            switchScreen(NewsActivity.class);
        }
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(MainActivity.this, cl);
        MainActivity.this.startActivity(intent);
    }


    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("É necessário estar conectado a EsTer para fazer os exercícios.");
        dialog.setTitle("Atenção!");
        dialog.setCancelable(false);
        dialog.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

}
