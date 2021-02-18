package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_TOTAL_QUESTIONS = "com.example.myfirstapp.CURRENT_TOTAL_QUESTIONS";
    public static final String CURRENT_TOTAL_CORRECT   = "com.example.myfirstapp.CURRENT_TOTAL_CORRECT";
    public static final String CURRENT_QUESTION_NUMBER = "com.example.myfirstapp.CURRENT_QUESTION_NUMBER";
    public static final String CURRENT_LANGUAGE = "com.example.myfirstapp.CURRENT_LANGUAGE";
    public static final String CURRENT_OPERATOR_TYPE = "com.example.myfirstapp.CURRENT_OPERATOR_TYPE";
    public static final String CURRENT_MAX_NUMBER = "com.example.myfirstapp.CURRENT_MAX_NUMBER";
    public static final String CURRENT_SCORE = "com.example.myfirstapp.CURRENT_SCORE";
    public static boolean SOUND_EFFECTS = true;
    public static WordStore WORD_STORE;
    public static WordStore SPELLING_STORE;
    public static int INITIALISED = 0;
    public static int MAX_NUMBER_ADD_GAME = 30;
    public static int MAX_NUMBER_SUBTRACT_GAME = 25;
    public static int MAX_NUMBER_MUTIPLIY_GAME = 9;
    public static int MAX_NUMBER_DIVIDE_GAME = 10;
    public static boolean ADD_GAME_MODE = true;
    public static boolean SUBTRACT_GAME_MODE = true;
    public static boolean MUTIPLY_GAME_MODE = true;
    public static boolean DIVIDE_GAME_MODE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load up session singleton noun store
        if( INITIALISED == 0 )
            WORD_STORE = new WordStore( this.getAssets(), "words.csv");
            SPELLING_STORE = new WordStore( this.getAssets(), "spelling_words.csv");
        INITIALISED = 1;

    }

    /** Called when the user taps the Send button */
    public void launchWhatIsInEnglishView( View view) {
        Intent intent = new Intent(this, MultipleChoice.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 50 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        intent.putExtra( CURRENT_LANGUAGE, 0 );
        intent.putExtra( CURRENT_SCORE, -1 );
        startActivity(intent);
    }

    public void launchWhatIsInGermanView( View view) {
        Intent intent = new Intent(this, MultipleChoice.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 50 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        intent.putExtra( CURRENT_LANGUAGE, 1 );
        intent.putExtra( CURRENT_SCORE, -1 );

        startActivity(intent);
    }

    public void launchAddGameView( View view) {
        Intent intent = new Intent(this, MathsGame.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 20 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        intent.putExtra( CURRENT_OPERATOR_TYPE, 0 );
        intent.putExtra( CURRENT_MAX_NUMBER, MAX_NUMBER_ADD_GAME );
        if( ADD_GAME_MODE ) {
            intent.putExtra(CURRENT_SCORE, 0);
        } else {
            intent.putExtra(CURRENT_SCORE, -1);
        }
        startActivity(intent);
    }

    public void launchSubtractGameView( View view) {
        Intent intent = new Intent(this, MathsGame.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 20 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        intent.putExtra( CURRENT_OPERATOR_TYPE, 1 );
        intent.putExtra( CURRENT_MAX_NUMBER, MAX_NUMBER_SUBTRACT_GAME );
        if( SUBTRACT_GAME_MODE ) {
            intent.putExtra(CURRENT_SCORE, 0);
        } else {
            intent.putExtra(CURRENT_SCORE, -1);
        }
        startActivity(intent);
    }

    public void launchMultiplyGameView( View view) {
        Intent intent = new Intent(this, MathsGame.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 20 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        intent.putExtra( CURRENT_OPERATOR_TYPE, 2 );
        intent.putExtra( CURRENT_MAX_NUMBER, MAX_NUMBER_MUTIPLIY_GAME );
        if( MUTIPLY_GAME_MODE ) {
            intent.putExtra(CURRENT_SCORE, 0);
        } else {
            intent.putExtra(CURRENT_SCORE, -1);
        }
        startActivity(intent);
    }

    public void launchDivisionGameView( View view) {
        Intent intent = new Intent(this, MathsGame.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 20 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        intent.putExtra( CURRENT_OPERATOR_TYPE, 3 );
        intent.putExtra( CURRENT_MAX_NUMBER, MAX_NUMBER_DIVIDE_GAME );
        if( DIVIDE_GAME_MODE ) {
            intent.putExtra(CURRENT_SCORE, 0);
        } else {
            intent.putExtra(CURRENT_SCORE, -1);
        }
        startActivity(intent);
    }

    /** Called when the user taps the Send button */
    public void launchSettings( View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }

    public void launchSpelling( View view) {
        Intent intent = new Intent(this, Spelling.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 5 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        startActivity(intent);
    }
}
