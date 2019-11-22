package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import com.pidois.ester.R;
import java.util.ArrayList;

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
                playSequence();

            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStart.setVisibility(View.INVISIBLE);
                buttonStop.setVisibility(View.VISIBLE);
                exec_chronometer.setBase(SystemClock.elapsedRealtime());
                exec_chronometer.start();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStart.setVisibility(View.VISIBLE);
                buttonStop.setVisibility(View.INVISIBLE);
                alertDialog();
                exec_chronometer.stop();
            }
        });

    }

    private void playSequence() {
        // create an empty array list with an initial capacity
        ArrayList<Integer> arrlist = new ArrayList<Integer>(30);

        // use add() method to add elements in the list
        arrlist.add(3);
        arrlist.add(3);
        arrlist.add(4);
        arrlist.add(5);
        arrlist.add(5);
        arrlist.add(4);
        arrlist.add(3);
        arrlist.add(2);
        arrlist.add(1);
        arrlist.add(1);
        arrlist.add(2);
        arrlist.add(3);
        arrlist.add(3);
        arrlist.add(2);
        arrlist.add(2);

        final Handler handler = new Handler();

        // let us print all the elements available in list
        for (Integer number : arrlist) {
            //System.out.println("Number = " + number);
            switch (number) {
                case 1:
                    playSound(mediaPlayer, R.raw.nota_do);

                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }

                    break;
                case 2:
                    playSound(mediaPlayer, R.raw.nota_re);

                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }

                    break;
                case 3:
                    playSound(mediaPlayer, R.raw.nota_mi);

                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }

                    break;
                case 4:
                    playSound(mediaPlayer, R.raw.nota_fa);

                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }
                    break;
                case 5:
                    playSound(mediaPlayer, R.raw.nota_sol);

                    try {
                        Thread.sleep(333);
                    } catch (Exception e) {
                        Log.e("Erro sleep", "Erro! " + e);
                    }
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
