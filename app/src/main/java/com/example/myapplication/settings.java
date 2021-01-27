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

        // upper and lower limits of the math game numbers
        int minAdd  = 9;
        int maxAdd  = 49;
        int minSub  = 9;
        int maxSub  = 49;
        int minMult = 4;
        int maxMult = 9;
        int minDiv  = 4;
        int maxDiv  = 10;

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

        // set up add game mode
        sw = (Switch) findViewById(R.id.add_game_mode);
        sw.setChecked(MainActivity.ADD_GAME_MODE);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.ADD_GAME_MODE = true;
                } else {
                    MainActivity.ADD_GAME_MODE = false;
                }
            }
        });

        // set up add game mode
        sw = (Switch) findViewById(R.id.subtract_game_mode);
        sw.setChecked(MainActivity.SUBTRACT_GAME_MODE);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.SUBTRACT_GAME_MODE = true;
                } else {
                    MainActivity.SUBTRACT_GAME_MODE = false;
                }
            }
        });
        // set up add game mode
        sw = (Switch) findViewById(R.id.multiply_game_mode);
        sw.setChecked(MainActivity.MUTIPLY_GAME_MODE);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.MUTIPLY_GAME_MODE = true;
                } else {
                    MainActivity.MUTIPLY_GAME_MODE = false;
                }
            }
        });
        // set up add game mode
        sw = (Switch) findViewById(R.id.divide_game_mode);
        sw.setChecked(MainActivity.DIVIDE_GAME_MODE);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.DIVIDE_GAME_MODE = true;
                } else {
                    MainActivity.DIVIDE_GAME_MODE = false;
                }
            }
        });

        // set up adding slider
        TextView maxAddView = (TextView) findViewById(R.id.maxAddNumberValue);
        maxAddView.setText( String.valueOf( MainActivity.MAX_NUMBER_ADD_GAME ));
        SeekBar addBar = (SeekBar)findViewById(R.id.maxAddNumberBar);
        addBar.setMax( maxAdd-minAdd );
        addBar.setProgress( MainActivity.MAX_NUMBER_ADD_GAME-minAdd );
        addBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                maxAddView.setText( String.valueOf( arg1+minAdd ) );
                MainActivity.MAX_NUMBER_ADD_GAME = arg1+minAdd;
            }
        });

        // set up subtract slider
        TextView maxSubView = (TextView) findViewById(R.id.maxSubtractNumberValue);
        maxSubView.setText( String.valueOf( MainActivity.MAX_NUMBER_SUBTRACT_GAME ));
        SeekBar subBar = (SeekBar)findViewById(R.id.maxSubtractNumberBar);
        subBar.setMax( maxSub - minSub );
        subBar.setProgress( MainActivity.MAX_NUMBER_SUBTRACT_GAME-minSub );
        subBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                maxSubView.setText( String.valueOf( arg1 + minSub ) );
                MainActivity.MAX_NUMBER_SUBTRACT_GAME = arg1 + minSub;
            }
        });

        // set up mutpliy slider
        TextView maxMultView = (TextView) findViewById(R.id.maxMultiplyNumberValue);
        maxMultView.setText( String.valueOf( MainActivity.MAX_NUMBER_MUTIPLIY_GAME ));
        SeekBar multBar = (SeekBar)findViewById(R.id.maxMultiplyNumberBar);
        multBar.setMax(maxMult-minMult);
        multBar.setProgress(MainActivity.MAX_NUMBER_MUTIPLIY_GAME-minMult);
        multBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                maxMultView.setText( String.valueOf(arg1+minMult) );
                MainActivity.MAX_NUMBER_MUTIPLIY_GAME = arg1+minMult;
            }
        });

        // set up divide slider
        TextView maxDivView = (TextView) findViewById(R.id.maxDivideNumberValue);
        maxDivView.setText( String.valueOf( MainActivity.MAX_NUMBER_DIVIDE_GAME ));
        SeekBar divBar = (SeekBar)findViewById(R.id.maxDivideNumberBar);
        divBar.setMax(maxDiv-minDiv);
        divBar.setProgress(MainActivity.MAX_NUMBER_DIVIDE_GAME-minDiv);
        divBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                maxDivView.setText( String.valueOf(arg1+minDiv ) );
                MainActivity.MAX_NUMBER_DIVIDE_GAME = arg1+minDiv;
            }
        });


    }
}
