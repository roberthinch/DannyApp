package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_TOTAL_QUESTIONS = "com.example.myfirstapp.CURRENT_TOTAL_QUESTIONS";
    public static final String CURRENT_TOTAL_CORRECT   = "com.example.myfirstapp.CURRENT_TOTAL_CORRECT";
    public static final String CURRENT_QUESTION_NUMBER = "com.example.myfirstapp.CURRENT_QUESTION_NUMBER";
    public static final String CURRENT_LANGUAGE = "com.example.myfirstapp.CURRENT_LANGUAGE";
    public static WordStore WORD_STORE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load up session singleton noun store
        WORD_STORE = new WordStore( this.getAssets(), "words.csv");
    }

    /** Called when the user taps the Send button */
    public void launchWhatIsInEnglishView( View view) {
        Intent intent = new Intent(this, MultipleChoice.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 20 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        intent.putExtra( CURRENT_LANGUAGE, 0 );
        startActivity(intent);
    }

    public void launchWhatIsInGermanView( View view) {
        Intent intent = new Intent(this, MultipleChoice.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 20 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        intent.putExtra( CURRENT_LANGUAGE, 1 );
        startActivity(intent);
    }

    public void launchMathsGameView( View view) {
        Intent intent = new Intent(this, MathsGame.class);
        intent.putExtra( CURRENT_TOTAL_QUESTIONS, 20 );
        intent.putExtra( CURRENT_QUESTION_NUMBER, 0 );
        intent.putExtra( CURRENT_TOTAL_CORRECT, 0 );
        startActivity(intent);
    }

    /** Called when the user taps the Send button */
    public void launchAddWords( View view) {
        Intent intent = new Intent(this, addWords.class);
        startActivity(intent);
    }
}
