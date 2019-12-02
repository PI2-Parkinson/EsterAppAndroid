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

import com.pidois.ester.R;
import java.util.ArrayList;

public class ExerciseSoundActivity extends AppCompatActivity {

    Button btnDo, btnRe, btnMi, btnFa, btnSol, btnInfo;
    MediaPlayer mediaPlayer;
    String sequenceValue = null;
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String data;
    private String levelBd = null;

    ArrayList<Integer> arraySeq = new ArrayList<Integer>(30);

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("RECEBE DA ESP32","VALOR: " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));


            data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
            Log.i("DA ESP32 PRA VARIAVEL","VALOR VARIAVEL: " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);

            if (data.contains("SQ")){
                arraySeq.clear();

                sequenceValue = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA; //SQ16565656

                for(int i = 2; i < sequenceValue.length() - 1; i++) {
                    arraySeq.add(Character.getNumericValue(sequenceValue.charAt(i)));
                }

                Log.i("Oi", "ARRAY" + arraySeq.toString());

                playSequence(arraySeq);

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SR";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();
                try {
                    Thread.sleep(1665);
                } catch (Exception e) {
                    Log.e("Erro sleep", "Erro! " + e);
                }
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

            if ( DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("V1")){
                levelBd = DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA;

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SN";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();

            }

            if (data.contains("BP")){
                alertDialog();
                BluetoothLeService.enviarDescriptor();
            }


            if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("N1")){

                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "N101";
                Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                BluetoothLeService.enviarDescriptor();

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
        btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(mediaPlayer, R.raw.nota_do);
            }
        });

        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(mediaPlayer, R.raw.nota_re);
            }
        });

        btnMi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(mediaPlayer, R.raw.nota_mi);
            }
        });

        btnFa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(mediaPlayer, R.raw.nota_fa);
            }
        });

        btnSol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(mediaPlayer, R.raw.nota_sol);
            }
        });

        //playSequence();

        final Chronometer exec_chronometer = (Chronometer)findViewById(R.id.exec_sound_chronometer);

        final Button buttonStart = (Button)findViewById(R.id.exec_sound_btn_start);
        final Button buttonStop = (Button)findViewById(R.id.exec_sound_btn_stop);
        final Button buttonDemo = (Button)findViewById(R.id.btn_demo);

        buttonStop.setVisibility(View.INVISIBLE);
        buttonStart.setVisibility(View.VISIBLE);

        buttonDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStart.setVisibility(View.VISIBLE);
                buttonStop.setVisibility(View.INVISIBLE);
//                playSequence();

            }
        });

        Log.i("OLHA O RDATA MLK DOIDO","TOMAAAAAAAAA : " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);

        if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("N1")){

            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "N101";
            Log.i("AQUI MANDA SDATA","NIVEL JOGO 1 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
            BluetoothLeService.enviarDescriptor();
        }

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "SR";
//
//                BluetoothLeService.enviarDescriptor();
//                Log.i("AQUI MANDA SDATA","SR FOI : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);

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

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Você deseja mesmo encerrar o exercício? Todo o progresso não será salvo.");
        dialog.setTitle("Deseja sair do jogo?");
        dialog.setPositiveButton("sair",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("BP")){
                            DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "P1";
                            BluetoothLeService.enviarDescriptor();
                        }
                        Log.i("AQUI MANDA SDATA","NIVEL JOGO 2 : " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);


                        Log.i("%$%$#$#$$#%$%#$#$#$@#","SF FOI: " + DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA);
                        finish();
                    }
                });
        dialog.setNegativeButton("não",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA.contains("BP")){
                    DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "P0";
                    BluetoothLeService.enviarDescriptor();
                }
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
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
}
