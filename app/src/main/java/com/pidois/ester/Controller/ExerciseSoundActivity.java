package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.pidois.ester.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ExerciseSoundActivity extends AppCompatActivity {

    Button btnDo, btnRe, btnMi, btnFa, btnSol;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_sound);

        btnDo = findViewById(R.id.e_button_do);
        btnRe = findViewById(R.id.e_button_re);
        btnMi = findViewById(R.id.e_button_mi);
        btnFa = findViewById(R.id.e_button_fa);
        btnSol = findViewById(R.id.e_button_sol);

        //mediaPlayer = MediaPlayer.create(this, R.raw.nota_do);
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
        buttonStart.setVisibility(View.INVISIBLE);

        buttonDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonDemo.setVisibility(View.INVISIBLE);
                buttonStart.setVisibility(View.VISIBLE);
                buttonStop.setVisibility(View.INVISIBLE);
                playSequence();

            }
        });

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

    private void playSequence() {
        // create an empty array list with an initial capacity
        ArrayList<Integer> arrlist = new ArrayList<Integer>(5);

        // use add() method to add elements in the list
        arrlist.add(1);
        arrlist.add(2);
        arrlist.add(3);
        arrlist.add(4);
        arrlist.add(5);

        final Handler handler = new Handler();

        // let us print all the elements available in list
        for (Integer number : arrlist) {
            //System.out.println("Number = " + number);
            switch (number) {
                case 1:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playSound(mediaPlayer, R.raw.nota_do);
                        }
                    }, 1000);
                    break;
                case 2:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playSound(mediaPlayer, R.raw.nota_re);
                        }
                    }, 2000);
                    break;
                case 3:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playSound(mediaPlayer, R.raw.nota_mi);
                        }
                    }, 3000);
                    break;
                case 4:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playSound(mediaPlayer, R.raw.nota_fa);
                        }
                    }, 4000);
                    break;
                case 5:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playSound(mediaPlayer, R.raw.nota_sol);
                        }
                    }, 5000);
                    break;
                default:
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

    private class Sequencia {
        String[] seq = {"15243", "12345"};
    }
}
