package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.text.InputType;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Spelling2 extends AppCompatActivity {

    public int totalQuestions;
    public int questionNumber;
    public int totalCorrect;
    private String soundFileShort;
    private String correctAnswer;
    private String questionText;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling2);

        // set the question number
        Intent intent = getIntent();
        totalQuestions = intent.getIntExtra(MainActivity.CURRENT_TOTAL_QUESTIONS, -1);
        questionNumber = intent.getIntExtra(MainActivity.CURRENT_QUESTION_NUMBER, -1 )+1;
        totalCorrect   = intent.getIntExtra(MainActivity.CURRENT_TOTAL_CORRECT, -1 );

        WordRecord record = MainActivity.VERB_STORE.sampleRecords( 1 )[0];
        correctAnswer  = record.language1;
        questionText  = record.language2;

        setup_screen();
        // say_word();
    }

    private void setup_screen() {

        // display the question number and total correct
        String text = String.valueOf(questionNumber) + "/" + String.valueOf(totalQuestions);
        TextView textView = findViewById(R.id.question_number);
        textView.setText(text);

        text = String.valueOf(totalCorrect) + "/" + String.valueOf(questionNumber - 1);
        textView = findViewById(R.id.question_correct);
        textView.setText(text);

        // display the question
        textView = findViewById(R.id.question_text);
        textView.setText(questionText);

        EditText editText = findViewById(R.id.spellingAnswer);
        editText.setText("");
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (hasFocus) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ( keyCode == KeyEvent.KEYCODE_ENTER ) {
                    check_answer();
                    return true;
                }
                return false;
            }
        });
        editText.requestFocus();

        // disable the next button
        Button button;
        button = findViewById( R.id.next );
        button.setEnabled( false );
    }

    private boolean say_word()
    {
        if( ( soundFileShort.length() > 0 ) ) {
            String uriPath = "android.resource://" + getPackageName() + "/raw/" + soundFileShort;
            Uri uri = Uri.parse(uriPath);

            if( uri != null ) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), uri );
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
                return true;
            }
        }
        return false;
    }

    public void on_play_sound(View view)
    {
       // say_word();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void on_next(View view)
    {
        launch_next_question();
    }

    public void on_enter(View view) { check_answer(); }

    public void check_answer()
    {
        // enable the next button
        Button button;
        button = findViewById( R.id.next );
        button.setEnabled( true );

        // disable the enter button
        button = findViewById( R.id.enter );
        button.setEnabled( false );

        // remove the screen
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        EditText editText = findViewById(R.id.spellingAnswer);
        editText.setEnabled( false );
        String text = editText.getText().toString();

        if( text.equals( correctAnswer ) ) {
            editText.setText("Correct!");
            totalCorrect += 1;
        } else
            editText.setText( "Wrong - " + correctAnswer );

    }


    public void launch_next_question()
    {
        if( questionNumber < totalQuestions )
        {
            Intent intent = new Intent(this, Spelling2.class);
            intent.putExtra(MainActivity.CURRENT_TOTAL_QUESTIONS, totalQuestions ) ;
            intent.putExtra(MainActivity.CURRENT_TOTAL_CORRECT, totalCorrect ) ;
            intent.putExtra(MainActivity.CURRENT_QUESTION_NUMBER, questionNumber );
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, Results.class);
            intent.putExtra(MainActivity.CURRENT_TOTAL_QUESTIONS, totalQuestions ) ;
            intent.putExtra(MainActivity.CURRENT_TOTAL_CORRECT, totalCorrect ) ;
            startActivity(intent);
        }
    }

}

