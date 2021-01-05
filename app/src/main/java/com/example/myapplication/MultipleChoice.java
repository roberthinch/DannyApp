package com.example.myapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class MultipleChoice extends AppCompatActivity {

    private String question;
    private String correctAnswer;
    private String[] answers;
    private int nAnswers = 6;
    private int[] buttonIds;
    private int correctId;
    private boolean correct;
    private int language;
    public int totalQuestions;
    public int questionNumber;
    public int totalCorrect;
    private int score;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        // set the question number
        Intent intent = getIntent();
        totalQuestions = intent.getIntExtra(MainActivity.CURRENT_TOTAL_QUESTIONS, -1);
        questionNumber = intent.getIntExtra(MainActivity.CURRENT_QUESTION_NUMBER, -1 )+1;
        totalCorrect   = intent.getIntExtra(MainActivity.CURRENT_TOTAL_CORRECT, -1 );
        language       = intent.getIntExtra(MainActivity.CURRENT_LANGUAGE, -1 );
        score          = intent.getIntExtra(MainActivity.CURRENT_SCORE, -1 );

        WordRecord[] records = MainActivity.WORD_STORE.sampleRecords( nAnswers );

        WordRecord record = records[0];
        if( language == 0 ) {
            question = record.language1;
            correctAnswer = record.language2;
        } else {
            question = record.language2;
            correctAnswer = record.language1;
        }

        answers = new String[nAnswers];
        for( int i = 0; i < nAnswers; i++ ) {
            record = records[i];
            if( language == 0 ) {
                answers[i] = record.language2;
            } else {
                answers[i] = record.language1;
            }
        }

        // mix up the answers and set up the screen
        shuffleArray( answers );
        setup_screen();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void shuffleIntArray(int[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void shuffleArray(String[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private void setup_screen()
    {
        // put the id of the buttons in a private array
        buttonIds = new int[] { R.id.option_a,R.id.option_b,R.id.option_c,
                                 R.id.option_d,R.id.option_e,R.id.option_f };

        //set the question
        TextView textView = findViewById(R.id.question_text);
        textView.setText(question);

        // display the question number and total correct
        String text = String.valueOf(questionNumber) + "/" + String.valueOf(totalQuestions);
        textView = findViewById(R.id.question_number);
        textView.setText(text);
        text = String.valueOf(totalCorrect) + "/" + String.valueOf(questionNumber-1);
        textView = findViewById(R.id.question_correct);
        textView.setText(text);

        // and the answers
        Button button;
        for( int i = 0; i < nAnswers; i++ )
        {
            button = findViewById( buttonIds[i] );
            button.setText( answers[i] );
            if( answers[i] == correctAnswer ) {
                correctId = button.getId();

            }
        }

        // disable the next button
        button = findViewById( R.id.next );
        button.setEnabled( false );
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void set_correct_answer(Button button)
    {
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{ new int[]{-android.R.attr.state_pressed},},
                new int[] {Color.GREEN, }
        );
        button.setEnabled(false);
        button.setBackgroundTintList( myColorStateList );
        MainActivity.WORD_STORE.correctLearnWord(question,language);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void set_wrong_answer(Button button)
    {
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{ new int[]{-android.R.attr.state_pressed},},
                new int[] {Color.RED, }
        );
        button.setEnabled(false);
        button.setBackgroundTintList( myColorStateList );

        MainActivity.WORD_STORE.addLearnWord(question,language);
    }

    private void set_other_answer(Button button)
    {
        button.setEnabled(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void on_select_answer(View view)
    {
        int view_id =  view.getId();
        Button button;

        correct = true;
        totalCorrect += 1;
        for(int i = 0; i < nAnswers; i++ )
        {
            button = findViewById( buttonIds[i] );
            if( buttonIds[i] == correctId ) {
                set_correct_answer( button ); }
            else if( buttonIds[i] == view_id ) {
                correct = false;
                totalCorrect -= 1;
                set_wrong_answer( button ); }
            else {
                set_other_answer( button ); }
        }

        button = findViewById( R.id.next );
        button.setTextColor( Color.BLACK );
        button.setEnabled( true );
    }

    public void on_next(View view)
    {
       if( questionNumber < totalQuestions )
       {
           Intent intent = new Intent(this, MultipleChoice.class);
           intent.putExtra(MainActivity.CURRENT_TOTAL_QUESTIONS, totalQuestions ) ;
           intent.putExtra(MainActivity.CURRENT_TOTAL_CORRECT, totalCorrect ) ;
           intent.putExtra(MainActivity.CURRENT_QUESTION_NUMBER, questionNumber );
           intent.putExtra(MainActivity.CURRENT_LANGUAGE, language );
           intent.putExtra(MainActivity.CURRENT_SCORE, score );
           startActivity(intent);
       }
       else
       {
           Intent intent = new Intent(this, Results.class);
           intent.putExtra(MainActivity.CURRENT_TOTAL_QUESTIONS, totalQuestions ) ;
           intent.putExtra(MainActivity.CURRENT_TOTAL_CORRECT, totalCorrect ) ;
           intent.putExtra(MainActivity.CURRENT_SCORE, score ) ;
           startActivity(intent);
       }
    }

}