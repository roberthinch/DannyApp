package com.example.myapplication;

import android.annotation.SuppressLint;
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

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class MathsGame extends AppCompatActivity {

    private String question;
    private int correctAnswer;
    private int nAnswers = 6;
    private int[] buttonIds;
    private boolean correct;
    private boolean digitEntered;
    public int totalQuestions;
    public int questionNumber;
    public int totalCorrect;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_game);

        // set the question number
        Intent intent = getIntent();
        totalQuestions = intent.getIntExtra(MainActivity.CURRENT_TOTAL_QUESTIONS, -1);
        questionNumber = intent.getIntExtra(MainActivity.CURRENT_QUESTION_NUMBER, -1 )+1;
        totalCorrect   = intent.getIntExtra(MainActivity.CURRENT_TOTAL_CORRECT, -1 );

        int maxNumber = 20;

        Random rnd = ThreadLocalRandom.current();
        int number1 = rnd.nextInt(maxNumber-1)+1;
        int number2 = rnd.nextInt(maxNumber-1)+1;

        question = String.valueOf(number1) + " + " + String.valueOf(number2) + " = ";
        correctAnswer = number1 + number2;

        setup_screen();
    }

    private void setup_screen()
    {
        // put the id of the buttons in a private array
        buttonIds = new int[] { R.id.key_0,R.id.key_1,R.id.key_2,R.id.key_3,R.id.key_4,
                                R.id.key_5,R.id.key_6,R.id.key_7,R.id.key_8,R.id.key_9 };

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

        // disable the next button
        Button button;
        button = findViewById( R.id.next );
        button.setEnabled( false );

        digitEntered = false;
        correct = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void disable_key_pad()
    {
       for( int i = 0; i < 10; i++ )
           findViewById(buttonIds[i]).setEnabled(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void set_correct_answer()
    {
        TextView textView = findViewById(R.id.question_text);
        textView.setBackgroundColor(Color.GREEN );
        totalCorrect += 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void set_wrong_answer()
    {
        TextView textView = findViewById(R.id.question_text);
        textView.setText( question + String.valueOf(correctAnswer));
        textView.setBackgroundColor(Color.RED );
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void on_select_answer(View view)
    {
        int view_id =  view.getId();
        Button button;

        int firstDigit    = correctAnswer / 10;
        int secondDigit   = correctAnswer % 10;
        int selectedDigit = ArrayUtils.indexOf( buttonIds, view_id );

        TextView textView = findViewById(R.id.question_text);
        textView.setText( textView.getText() + String.valueOf( selectedDigit ) );

        // deal with case of entering a 0 for a 1 digit answer
        if(  firstDigit == 0 && selectedDigit == 0  && digitEntered == false ) {
            digitEntered = true;
            return;
        }

        // next deal with single digit answers
        if( firstDigit == 0 ) {
            if( selectedDigit == secondDigit ) {
                set_correct_answer();
            } else {
                correct = false;
                set_wrong_answer();
            }
        }
        else {
            // now with 2 digit answers
            if (digitEntered == false) {
                if (selectedDigit == firstDigit) {
                    digitEntered = true;
                    return;
                } else {
                    digitEntered = true;
                    correct = false;
                    return;
                }
            } else {
                if (selectedDigit == secondDigit) {
                    if( correct == true ) {
                        set_correct_answer();
                    } else
                        set_wrong_answer();

                } else {
                    correct = false;
                    set_wrong_answer();
                }
            }
        }

        disable_key_pad();
        button = findViewById( R.id.next );
        button.setTextColor( Color.BLACK );
        button.setEnabled( true );
    }

    public void on_next(View view)
    {
       if( questionNumber < totalQuestions )
       {
           Intent intent = new Intent(this, MathsGame.class);
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