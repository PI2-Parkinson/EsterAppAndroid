package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pidois.ester.R;


public class StrapActivity extends AppCompatActivity implements View.OnClickListener {

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
            DeviceControlActivity.enviarDescriptor();

            switchScreen(StrapRestActivity.class);
        } else if (i == R.id.btn_m2){

            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "MT2";

            Log.i("AQUI MANDA SDATA","MT2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
            DeviceControlActivity.enviarDescriptor();
            switchScreen(StrapPosturalActivity.class);
        } else if (i == R.id.btn_m3){

            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "MT3";

            Log.i("AQUI MANDA SDATA","MT3 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
            DeviceControlActivity.enviarDescriptor();
            switchScreen(StrapFingerNoseActivity.class);
        } else if (i == R.id.btn_m4){
            alertDialog();
        }

    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please Select any option");
        dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(StrapActivity.this, cl);
        StrapActivity.this.startActivity(intent);
    }

}
