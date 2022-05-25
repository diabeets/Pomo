package com.example.pomo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.SeekBar;
import java.text.NumberFormat;

import android.view.View.OnClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SeekBar studySeekBar;
    private SeekBar breakSeekBar;
    private Button startButton;
    private TextView studyTimerTextView;
    private TextView breakTimerTextView;
    int studyTimer;
    int breakTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studySeekBar = findViewById(R.id.studySeekBar);
        breakSeekBar = findViewById(R.id.breakSeekBar);
        studyTimerTextView = findViewById(R.id.studyTimerTextView);
        breakTimerTextView = findViewById(R.id.breakTimerTextView);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new ButtonListener());
        studySeekBar.setOnSeekBarChangeListener(new SeekBarListener());
        breakSeekBar.setOnSeekBarChangeListener(new SeekBarListener());
    }


    class SeekBarListener implements OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean user) {
            if (seekBar.getId() == R.id.studySeekBar){
                studyTimer = progress;
                studyTimerTextView.setText(studyTimer + " Minutes");
            }
            else if (seekBar.getId() == R.id.breakSeekBar){
                breakTimer = progress;
                breakTimerTextView.setText(breakTimer + " Minutes");
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // unused
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // unused
        }
    }

    class ButtonListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.startButton){
                changeActivity();

            }
        }
    }

    private void changeActivity(){
            Intent eventActivity = new Intent(this, EventActivity.class);
            eventActivity.putExtra("studyTimer", studyTimer*60000);
            eventActivity.putExtra("breakTimer", breakTimer*60000);
            startActivity(eventActivity);

    }

}