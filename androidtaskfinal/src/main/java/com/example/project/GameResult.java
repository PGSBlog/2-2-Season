package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GameResult extends AppCompatActivity {

    TextView correctCount;
    TextView wrongCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        correctCount = (TextView) findViewById(R.id.correct_answer);
        wrongCount = (TextView) findViewById(R.id.wrong_answer);


        Intent intent = getIntent();
        Bundle bundleData = intent.getBundleExtra("RESULT_DATA");
        if (bundleData == null) {
            Toast toast = Toast.makeText(this, "단독 실행", Toast.LENGTH_SHORT);
        } else {
            correctCount.setText("맞은 갯수 : " + bundleData.getInt("CORRECT_COUNT"));
            wrongCount.setText("틀린 갯수 : " + bundleData.getInt("WRONG_COUNT"));
        }
    }

}
