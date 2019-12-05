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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pidois.ester.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExerciseColorActivity extends ExercisesActivity {

    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String data;
    private String levelBd = null;
    private Button buttonStart, buttonStop, buttonDemo, btn_help;
    private FirebaseUser currentFirebaseUser;
    private DatabaseReference databaseReference;
    private Chronometer chronometer;

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("RECEBE DA ESP32","VALOR: " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));


            data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
            Log.i("DA ESP32 PRA VARIAVEL","VALOR VARIAVEL: " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);


            if (data.contains("V2")){
                levelBd = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA;

                levelBd = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA; //V1XX

                char ch1 = levelBd.charAt(2);
                char ch2 = levelBd.charAt(3);

                levelBd = new StringBuilder().append(ch1).append(ch2).toString();

                Log.i("BD", "LEVEL: " +levelBd);

                sendColorAnswer(currentFirebaseUser, databaseReference, levelBd);

                chronometer.stop();

                alertDialogShowLevel(levelBd);

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

        chronometer = findViewById(R.id.exec_color_chronometer);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        final Button btn_help = findViewById(R.id.btn_help);
        
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogInfo();
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

    private void switchScreen (Class cl){
        Intent intent = new Intent(ExerciseColorActivity.this, cl);
        ExerciseColorActivity.this.startActivity(intent);
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
                        DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SN";
                        Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                        BluetoothLeService.enviarDescriptor();
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }

    public void sendColorAnswer(FirebaseUser currentFirebaseUser, DatabaseReference databaseReference, String value) {
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("color_answers/" + currentFirebaseUser.getUid());
        DatabaseReference databaseRef = databaseReference.push();

        databaseRef.child("right_answers").setValue(value);
        databaseRef.child("date").setValue(getCurrentDate());

    }
}
