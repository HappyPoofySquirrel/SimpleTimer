package com.example.ghopkins.timerapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.BoolRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekbar;
    TextView timerTextView;
    Boolean counterIsActive=false;
    Button controllerButton;
    CountDownTimer countDownTimer;
    MediaPlayer mplayer;

    public void resetTimer(){
        timerTextView.setText("0:30");
        controllerButton.setText("Go");
        seekbar.setProgress(30);
        countDownTimer.cancel();
        seekbar.setEnabled(true);
        counterIsActive=false;

    }
    public void updateTimer(int secondsLeft){
        int minutes= secondsLeft/60;
        int seconds=secondsLeft -minutes*60;
        String secondsS=String.valueOf(seconds);

        if (seconds <=9){
            secondsS = "0" + seconds;
        }

        timerTextView.setText(minutes + ":" + secondsS);
    }


        public void controlTimer(View view) {
            if (counterIsActive == false) {

                counterIsActive = true;
                seekbar.setEnabled(false);
                countDownTimer=new CountDownTimer(seekbar.getProgress() * 1000 + 100, 1000) { //added the +100 to compensate for the dealy in execution
                    @Override
                    public void onTick(long millisUntilFinished) {
                        controllerButton.setText("Stop");
                        updateTimer((int) millisUntilFinished / 1000);
                    }

                    @Override
                    public void onFinish() {
                        resetTimer();
                        mplayer.start();
                    }
                }.start();
            }   else{
               resetTimer();
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controllerButton = (Button) findViewById(R.id.ControllerButton);
        mplayer = MediaPlayer.create(getApplicationContext(), R.raw.knocks2);
        seekbar= (SeekBar)findViewById(R.id.seekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);


        seekbar.setMax(400);
        seekbar.setProgress(30);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }@Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });




    }
}