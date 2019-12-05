package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pidois.ester.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExerciseSoundActivity extends ExercisesActivity {

    MediaPlayer mediaPlayer;
    String sequenceValue = null, sequenceLevel = null;
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String data;
    private String levelBd = null;
    private Button buttonStart, buttonStop, buttonDemo, btn_help;
    private FirebaseUser currentFirebaseUser;
    private DatabaseReference databaseReference;
    private Chronometer chronometer;
    private int i=0;

    ArrayList<Integer> arraySeq = new ArrayList<Integer>(30);

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("RECEBE DA ESP32","VALOR: " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));


            data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
            Log.i("DA ESP32 PRA VARIAVEL","VALOR VARIAVEL: " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);

            if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("SQ")){
                arraySeq.clear();

                sequenceValue = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA; //SQ16565656

                for(int i = 2; i < sequenceValue.length() - 1; i++) {
                    arraySeq.add(Character.getNumericValue(sequenceValue.charAt(i)));
                }

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SF";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();

                playSequence(arraySeq);

                if (i == 0) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }
                i++;
            }

            if ( DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("V1")){
                levelBd = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA; //V1XX

                char ch1 = levelBd.charAt(2);
                char ch2 = levelBd.charAt(3);

                levelBd = new StringBuilder().append(ch1).append(ch2).toString();

                Log.i("BD", "LEVEL: " +levelBd);

                sendSoundAnswer(currentFirebaseUser, databaseReference, levelBd);

                alertDialogShowLevel(levelBd);

                chronometer.stop();

            }

            if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("QM")){

                switchScreen(MainActivity.class);

            }
        }
    };

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

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_sound);

        ImageView image = findViewById(R.id.finger_sound);
        image.setImageResource(R.drawable.default_finger);

        final Intent intent = getIntent();

        mDeviceAddress = intent.getStringExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS);

        chronometer = findViewById(R.id.exec_sound_chronometer);

        final Button buttonStart = findViewById(R.id.exec_sound_btn_start);
        final Button btn_help = findViewById(R.id.btn_help);

        Log.i("OLHA O RDATA MLK DOIDO","TOMAAAAAAAAA : " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogInfo();
            }
        });
    }

    private void playSequence(ArrayList<Integer> arrayList) {
        // create an empty array list with an initial capacit

        // use add() method to add elements in the list

        ImageView image = findViewById(R.id.finger_sound);

        // let us print all the elements available in list
        for (Integer number : arrayList) {
            //System.out.println("Number = " + number);
            switch (number) {
                case 1:
                    playSound(mediaPlayer, R.raw.nota_do);
                    image.setImageResource(R.drawable.do_note);
                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }

                    break;
                case 2:
                    image.setImageResource(R.drawable.re_note);
                    playSound(mediaPlayer, R.raw.nota_re);

                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }

                    break;
                case 3:
                    image.setImageResource(R.drawable.mi_note);
                    playSound(mediaPlayer, R.raw.nota_mi);

                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }

                    break;
                case 4:
                    image.setImageResource(R.drawable.fa_note);
                    playSound(mediaPlayer, R.raw.nota_fa);

                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }
                    break;
                case 5:
                    image.setImageResource(R.drawable.sol_note);
                    playSound(mediaPlayer, R.raw.nota_sol);

                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }
                    break;
                default:
                    image.setImageResource(R.drawable.default_finger);
                    break;
            }
        }



    }

    private void playSound (MediaPlayer mediaPlayer, int sound) {

        mediaPlayer = MediaPlayer.create(ExerciseSoundActivity.this, sound);
        mediaPlayer.start();

    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    private class Sequencia {
        String[] seq = {"15243", "12345"};
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(ExerciseSoundActivity.this, cl);
        ExerciseSoundActivity.this.startActivity(intent);
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

    public void sendSoundAnswer(FirebaseUser currentFirebaseUser, DatabaseReference databaseReference, String value) {
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("sound_answers/" + currentFirebaseUser.getUid());
        DatabaseReference databaseRef = databaseReference.push();

        databaseRef.child("right_answers").setValue(value);
        databaseRef.child("date").setValue(getCurrentDate());

    }
}
