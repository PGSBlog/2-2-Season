package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MemorizeMain extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_memorize_main);
        }
        public void Step1Btn(View view){
            Intent intent = new Intent(this,ReadDBActivity.class);
            intent.putExtra("STEP", "1");
            startActivity(intent);
        }
        public void Step2Btn(View view){
            Intent intent = new Intent(this,ReadDBActivity.class);
            intent.putExtra("STEP", "2");
            startActivity(intent);
        }
        public void Step3Btn(View view){
            Intent intent = new Intent(this,ReadDBActivity.class);
            intent.putExtra("STEP", "3");
            startActivity(intent);
        }
        public void Step4Btn(View view){
            Intent intent = new Intent(this,ReadDBActivity.class);
            intent.putExtra("STEP", "4");
            startActivity(intent);
        }
        public void Step5Btn(View view){
            Intent intent = new Intent(this,ReadDBActivity.class);
            intent.putExtra("STEP", "5");
            startActivity(intent);
        }
}