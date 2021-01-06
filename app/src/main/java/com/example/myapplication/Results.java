package com.example.myapplication;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Results extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // set the total correct
        Intent intent = getIntent();
        int totalQuestions = intent.getIntExtra(MainActivity.CURRENT_TOTAL_QUESTIONS, -1);
        int totalCorrect   = intent.getIntExtra(MainActivity.CURRENT_TOTAL_CORRECT, -1);
        int score          = intent.getIntExtra(MainActivity.CURRENT_SCORE, -1);

        String text;
        if( score < 0 ) {
            text = "You scored " + String.valueOf(totalCorrect) + "/" + String.valueOf(totalQuestions);
        } else {
            text = "You scored " + String.valueOf(score) + "pts"; }

        TextView textView = findViewById(R.id.resultText);
        textView.setText(text);

        if( 1.0 * totalCorrect / totalQuestions > 0.849 && MainActivity.SOUND_EFFECTS ){
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.cheers);
            mp.start();
        }
    }

    public void on_next(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

