package com.pidois.ester.Controller;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.pidois.ester.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class CognitiveActivity extends ExerciseAbstractClass implements View.OnClickListener {

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;


    public static final String RED = "#FF0000";
    public static final String BLUE = "#0000FF";
    public static final String GREEN = "#00FF00";
    public static final String YELLOW = "#FFFF00";
    public static final String ORANGE = "#FF8000";
    public static final String PURPLE = "#9900FF";
    public static final String PINK = "#FF00AA";
    public static final String BROWN = "#B36800";
    public static final String WHITE = "#FFFFFF";
    public static final String GRAY = "#808080";
    public static final String BLACK = "#000000";

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("cognitive_answers/"+currentFirebaseUser.getUid());
    DatabaseReference databaseRef = database.push();

    private TextView mText = null;
    private int time = 0, timeCountDown = 0;
    private int answers = 0;
    private int correctAnswers = 0;
    ArrayList<Integer> randomColorArray = new ArrayList<>();
    ArrayList<Integer> randomButtonArray = new ArrayList<>();
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String data;
    private String levelBd = null;


    Random random = new Random();

    private String[] colorName = {"vermelho", "azul",
            "amarelo", "verde",
            "laranja", "roxo",
            "rosa", "marrom",
            "branco", "cinza"
    };
    private String[] colorHex = {RED, BLUE,
            YELLOW, GREEN,
            ORANGE, PURPLE,
            PINK, BROWN,
            WHITE, GRAY
    };

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("RECEBE DA ESP32","VALOR: " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));


            data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
            Log.i("DA ESP32 PRA VARIAVEL","VALOR VARIAVEL: " + DeviceControlActivity.BLUETOOTH_GLOBAL_RDATA);



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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognitive);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Chronometer simpleChronometer = findViewById(R.id.simpleChronometer);
        simpleChronometer.setCountDown(true);
        simpleChronometer.setBase(SystemClock.elapsedRealtime()+ 60*1000);
        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            public void onChronometerTick(Chronometer chronometer) {

                //int timeElapsed = (int) (SystemClock.elapsedRealtime() - chronometer.getBase());

                //int hours = timeElapsed / 3600000;
                //int minutes = (timeElapsed - hours * 3600000) / 60000;

                //Log.i("CognitiveActivity",minutes + " minutos " + time + " segundos");

                if (timeCountDown == 60) {
                    endGame();
                    chronometer.stop();
                    alertDialog();
                    //postDelay();
                } else if (time == 10) {
                    time = 0;
                    LinearLayout lView = findViewById(R.id.linearlayout);
                    lView.removeAllViewsInLayout();
                    showLayout();
                    countAnswers();
                }
                time++;
                timeCountDown++;

            }
        });
        simpleChronometer.start();

        int[] color = generateRandomSequence();

        showSequence(color);
        generateButtons(color);


    }

    public int[] generateRandomSequence(){
        int[] color = {0,0};
        int randomColorName = random.nextInt(10);
        int randomColorHex = random.nextInt(10);
        while(randomColorName == randomColorHex){
            randomColorHex = random.nextInt(10);
        }

        color[0] = randomColorName;
        color[1] = randomColorHex;

        return color;
    }

    public void endGame(){
        LinearLayout lView = findViewById(R.id.linearlayout);
        TableRow tableRow = findViewById(R.id.tableRow);
        TableRow chronometerTableRow = findViewById(R.id.chronometerTableRow);
        TextView title = findViewById(R.id.cognitive_title);
        TextView title2 = findViewById(R.id.cognitive_title2);
        title.setVisibility(View.INVISIBLE);
        title2.setVisibility(View.INVISIBLE);
        mText = new TextView(this);
        lView.removeAllViews();
        tableRow.removeAllViews();
        tableRow.removeAllViewsInLayout();
        chronometerTableRow.removeAllViews();
        mText.setText("Fim do exercício");
        mText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        lView.setBackgroundColor(Color.parseColor(WHITE));
        lView.addView(mText);
        showRightAnswers();
        DeviceControlActivity.BLUETOOTH_GLOBAL_SDATA = "F1";
        BluetoothLeService.enviarDescriptor();
    }

    public void showSequence(int[] color){


        LinearLayout lView = findViewById(R.id.linearlayout);


        mText = new TextView(this);
        mText.setText(colorName[color[0]]);
        mText.setTextColor(Color.parseColor(colorHex[color[1]]));
        mText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
        mText.setBackgroundColor(Color.parseColor(BLACK));

        lView.addView(mText);

    }

    public void generateButtons(int[] color){
        randomButtonArray = new ArrayList<>();
        int randomColor = random.nextInt(10);

        randomColorArray.add(R.id.leftButton);
        randomColorArray.add(R.id.centerButton);
        randomColorArray.add(R.id.rightButton);

        int randomArray = random.nextInt((randomColorArray.size()));
        Log.i("generateButtons","randomArraySize " + randomColorArray.size());
        Log.i("generateButtons","randomArray " + randomArray);

        int leftButton = randomColorArray.get(randomArray);
        randomColorArray.remove(new Integer(leftButton));
        randomButtonArray.add(leftButton);
        randomArray = random.nextInt((randomColorArray.size()));
        Log.i("generateButtons","randomArraySize " + randomColorArray.size());
        Log.i("generateButtons","randomArray " + randomArray);
        Log.i("generateButtons","leftButton " + leftButton + " id " + R.id.leftButton);


        int centerButton = randomColorArray.get(randomArray);
        randomColorArray.remove(new Integer(centerButton));
        randomButtonArray.add(centerButton);
        randomArray = 0;
        Log.i("generateButtons","randomArraySize " + randomColorArray.size());
        Log.i("generateButtons","randomArray " + randomArray);
        Log.i("generateButtons","centerButton " + centerButton);

        int rightButton = randomColorArray.get(randomArray);
        randomColorArray.remove(new Integer(rightButton));
        randomButtonArray.add(rightButton);

        Log.i("generateButtons","rightButton " + rightButton);

        Button buttonLeft = findViewById(leftButton);
        buttonLeft.setBackgroundColor(Color.parseColor(colorHex[color[0]]));
        buttonLeft.setOnClickListener(this);

        Button buttonCenter = findViewById(centerButton);
        buttonCenter.setBackgroundColor(Color.parseColor(colorHex[color[1]]));
        buttonCenter.setOnClickListener(this);

        Button buttonRight = findViewById(rightButton);

        if(colorHex[randomColor] != colorHex[color[0]] &&
                colorHex[randomColor] != colorHex[color[1]]){

            buttonRight.setBackgroundColor(Color.parseColor(colorHex[randomColor]));

        } else {

            while(colorHex[randomColor] == colorHex[color[0]] ||
                    colorHex[randomColor] == colorHex[color[1]]) {
                randomColor = random.nextInt(10);
            }


            buttonRight.setBackgroundColor(Color.parseColor(colorHex[randomColor]));
        }

        buttonRight.setOnClickListener(this);
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    public void showLayout(){
        int[] color = generateRandomSequence();

        showSequence(color);
        generateButtons(color);
    }

    @Override
    public void onClick(View view) {
        LinearLayout lView = findViewById(R.id.linearlayout);
        mText = new TextView(this);
        int i = view.getId();

        if (i == randomButtonArray.get(0)){
            time = 0;
            lView.removeAllViewsInLayout();
            lView.addView(mText);
            showLayout();
            countAnswers();
            countRightAnswers();
            Log.i("onClick", "randomButtonArray " + randomButtonArray.size());
            Log.i("onClick", "randomColorArray " + randomColorArray.size());
        } else if (i == randomButtonArray.get(1) || i == randomButtonArray.get(2)){
            time = 0;
            lView.removeAllViewsInLayout();
            lView.addView(mText);
            showLayout();
            countAnswers();
        }
    }

    public int countAnswers(){
        answers += 1;
        //Log.i("Total of answers", answers + " total answers");
        return answers;
    }

    public int countRightAnswers(){
        correctAnswers += 1;
        //Log.i("Right answers", answers + " right answers");

        return correctAnswers;
    }

    public void showRightAnswers(){

        databaseRef.child("totalAnswers").setValue(answers);
        databaseRef.child("rightAnswers").setValue(correctAnswers);
        databaseRef.child("wrongAnswers").setValue(answers - correctAnswers);
        databaseRef.child("date").setValue(getCurrentDate());

        //database.child(currentFirebaseUser.getUid()).child("answers").child("rightAnswers").setValue(correctAnswers);
        //database.child(currentFirebaseUser.getUid()).child("answers").child("totalAnswers").setValue(answers);
        //database.child(currentFirebaseUser.getUid()).child("answers").child("wrongAnswers").setValue(answers - correctAnswers);
        //database.child(currentFirebaseUser.getUid()).child("answers").child("date").setValue(getCurrentDate());
    }

    private void alertDialog() {
        int wrongAnswers1 = answers-correctAnswers;
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Total: " +answers + "\n\nRespostas corretas: "+correctAnswers+"\nRespostas erradas: "+wrongAnswers1);
        dialog.setTitle("Resultado");
        dialog.setCancelable(false);
        dialog.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //finish();
                        postDelay();

                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void postDelay(){
        Handler hander = new Handler();
        hander.postDelayed(new Runnable() {
            @Override
            public void run() {
                //alertDialog();
                finish();
            }
        }, 100);
    }

    private String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(CognitiveActivity.this, cl);
        CognitiveActivity.this.startActivity(intent);
    }
}
