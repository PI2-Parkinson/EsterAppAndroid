package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pidois.ester.R;

import java.util.Locale;

public class StrapRestActivity extends AppCompatActivity {

    private int time = 0;
    private Button mButtonStartPause;
    private CountDownTimer mCountDownTimer;
    private static final long START_TIME_IN_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private boolean mTimerRunning;
    private TextView mTextViewCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap_rest);

        mButtonStartPause = findViewById(R.id.button_start_pause);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        updateCountDownText();

        Chronometer strapChronometer = findViewById(R.id.simpleChronometer);
        strapChronometer.setBase(SystemClock.elapsedRealtime());
        strapChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            public void onChronometerTick(Chronometer chronometer) {

                int timeElapsed = (int) (SystemClock.elapsedRealtime() - chronometer.getBase());

                int hours = timeElapsed / 3600000;
                int minutes = (timeElapsed - hours * 3600000) / 60000;

                Log.i("StrapRestActivity",minutes + " minutos " + time + " segundos");

                if (time == 10) {
                    time = 0;
                    LinearLayout lView = findViewById(R.id.linearlayout);
                    lView.removeAllViewsInLayout();
                    chronometer.stop();

                }
            }
        });
        strapChronometer.start();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    

}
