package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class settings extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // set up sound effects switch
        Switch sw = (Switch) findViewById(R.id.sound_effects);
        sw.setChecked(MainActivity.SOUND_EFFECTS);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.SOUND_EFFECTS = true;
                } else {
                    MainActivity.SOUND_EFFECTS = false;
                }
            }
        });

        // set up adding slider
        TextView maxAddView = (TextView) findViewById(R.id.maxAddNumberValue);
        maxAddView.setText( String.valueOf( MainActivity.MAX_NUMBER_ADD_GAME ));
        SeekBar addBar = (SeekBar)findViewById(R.id.maxAddNumberBar);
        addBar.setMax(49);
        addBar.setMin(9);
        addBar.setProgress(MainActivity.MAX_NUMBER_ADD_GAME);
        addBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                maxAddView.setText( String.valueOf(arg1) );
                MainActivity.MAX_NUMBER_ADD_GAME = arg1;
            }
        });

        // set up subtract slider
        TextView maxSubView = (TextView) findViewById(R.id.maxSubtractNumberValue);
        maxSubView.setText( String.valueOf( MainActivity.MAX_NUMBER_SUBTRACT_GAME ));
        SeekBar subBar = (SeekBar)findViewById(R.id.maxSubtractNumberBar);
        subBar.setMax(49);
        subBar.setMin(9);
        subBar.setProgress(MainActivity.MAX_NUMBER_SUBTRACT_GAME);
        subBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                maxSubView.setText( String.valueOf(arg1) );
                MainActivity.MAX_NUMBER_SUBTRACT_GAME = arg1;
            }
        });

        // set up mutpliy slider
        TextView maxMultView = (TextView) findViewById(R.id.maxMultiplyNumberValue);
        maxMultView.setText( String.valueOf( MainActivity.MAX_NUMBER_MUTIPLIY_GAME ));
        SeekBar multBar = (SeekBar)findViewById(R.id.maxMultiplyNumberBar);
        multBar.setMax(9);
        multBar.setMin(4);
        multBar.setProgress(MainActivity.MAX_NUMBER_MUTIPLIY_GAME);
        multBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                maxMultView.setText( String.valueOf(arg1) );
                MainActivity.MAX_NUMBER_MUTIPLIY_GAME = arg1;
            }
        });


    }
}
