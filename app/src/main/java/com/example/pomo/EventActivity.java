package com.example.pomo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.Locale;


public class EventActivity extends AppCompatActivity {
    private TextView timerTextView;
    private TextView stateTextView;
    private Button pauseButton;
    private Button goBackButton;
    private ProgressBar progressBar;

    private CountDownTimer countDownTimer;

    private long studyTimer;
    private long breakTimer;
    private long timeLeft;
    private boolean studying;
    private boolean paused;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event2);

        timerRunning = false;
        studying = true;
        paused = true;

        studyTimer = getIntent().getIntExtra("studyTimer", 3);
        breakTimer = getIntent().getIntExtra("breakTimer", 0);

        timerTextView = findViewById(R.id.timerTextView);
        stateTextView = findViewById(R.id.stateTextView);
        pauseButton = findViewById(R.id.pauseButton);
        goBackButton = findViewById(R.id.goBackButton);
        progressBar = findViewById(R.id.progressBar);

        pauseButton.setOnClickListener(new ButtonListener());
        goBackButton.setOnClickListener(new ButtonListener());

        timeLeft = studyTimer;
        updateTimer();
    }

    private void startTimer () {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                if (studying == true) {
                    timeLeft = breakTimer;
                    studying = false;
                    stateTextView.setText("BREAK");
                    pauseButton.setText("Start Break");
                } else if (studying == false) {
                    timeLeft = studyTimer;
                    studying = true;
                    stateTextView.setText("STUDY");
                    pauseButton.setText("Start Studying");

                }
                updateTimer();
                timerRunning = false;
            }

        }.start();
    }


    private void updateTimer () {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String str = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(str);

        if (studying){
            progressBar.setProgress((int)(100 - 100*timeLeft/studyTimer));
        }
        else{
            progressBar.setProgress((int)(100 - 100*timeLeft/breakTimer));
        }
    }

    class ButtonListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.pauseButton) {
                if (timerRunning == false){
                    timerRunning = true;
                    paused = false;
                    startTimer();
                }

                else if (paused == false) {
                    paused = true;
                    pauseButton.setText("Resume");
                    countDownTimer.cancel();
                } else if (paused == true) {
                    paused = false;
                    pauseButton.setText("Pause");
                    startTimer();
                }
            }
            if (v.getId() == R.id.goBackButton){
                finish();
            }
        }
    }

}