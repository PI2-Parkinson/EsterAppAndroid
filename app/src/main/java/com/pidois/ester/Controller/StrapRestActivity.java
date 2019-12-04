package com.pidois.ester.Controller;

import android.app.AlertDialog;
import android.bluetooth.BluetoothClass;
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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.pidois.ester.R;

public class StrapRestActivity extends StrapUtils {

    private Chronometer chronometer;
    private Button button, button_result;
    private int time = 0;
    private String title, message;
    private String levelBd;

    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String data;
    private int grade = 0;
    private String GTValue = null;

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("RECEBE DA ESP32","VALOR: " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));


            data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
            Log.i("DA ESP32 PRA VARIAVEL","VALOR VARIAVEL: " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);

            if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("QM")){

                switchScreen(MainActivity.class);

            }

            if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("GT")){
                levelBd = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA; //GTX

                char ch1 = levelBd.charAt(2);

                levelBd = new StringBuilder().append(ch1).toString();

                Log.i("BD", "LEVEL: " +levelBd);

                sendStrapAsnwer(firebaseUser, databaseReference, "rest", levelBd);

                button_result.setVisibility(View.VISIBLE);
                chronometer.stop();
            }


            /*if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("GT")){
                GTValue = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA;
                grade = Character.getNumericValue(GTValue.charAt(2));
                Log.i("StrapFingerNoseActivity","Grau tremor : " + grade);
                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "GTR";
                Log.i("StrapFingerNoseActivity","Grau tremor : " + GTValue);
                BluetoothLeService.enviarDescriptor();
                strapResult(grade, StrapRestActivity.class);
            }*/

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap_rest);

        calibrate();

        chronometer = findViewById(R.id.strap_rest_chronometer);
        button = findViewById(R.id.strap_rest_btn);
        button_result = findViewById(R.id.strap_rest_result);
        button_result.setVisibility(View.GONE);

        chronometer.setCountDown(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "IT";

                Log.i("AQUI MANDA SDATA","IT : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();
                button.setVisibility(View.GONE);
                chronometer.setBase(SystemClock.elapsedRealtime() + (30*1000));
                chronometer.start();

                /*chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        time++;
                        if (time == 20){
                            //DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "GTR";

                            //Log.i("AQUI MANDA SDATA","GTR MALUCO : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                            //BluetoothLeService.enviarDescriptor();
                            chronometer.stop();

                        }
                    }
                });*/
            }
        });

        button_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShowLevel(levelBd);
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

    private void alertDialogShowLevel(String level) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Seu maior número de acertos foi: "+level+" sequência(s)");
        dialog.setTitle("Resultado");
        dialog.setCancelable(false);
        dialog.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "GTR";
                        Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                        BluetoothLeService.enviarDescriptor();
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(StrapRestActivity.this, cl);
        StrapRestActivity.this.startActivity(intent);
    }


}
