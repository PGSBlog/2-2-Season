package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReadDBActivity extends AppCompatActivity {


    TextView stringView;
    TextView meaningView;
    Cursor cursor;
    ArrayList<Voca> wordArr = null;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_dbactivity);

        Intent intent = getIntent();
        String step_Num = intent.getStringExtra("STEP");

        stringView = findViewById(R.id.read_string);
        meaningView = findViewById(R.id.read_meaning);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        if(step_Num.equals("1")){
            cursor = db.rawQuery("select string, meaning from tb_words where step='1'",null);
        }else if(step_Num.equals("2")){
            cursor = db.rawQuery("select string, meaning from tb_words where step='2'",null);
        }else if(step_Num.equals("3")){
            cursor = db.rawQuery("select string, meaning from tb_words where step='3'",null);
        }else if(step_Num.equals("4")){
            cursor = db.rawQuery("select string, meaning from tb_words where step='4'",null);
        }else{
            cursor = db.rawQuery("select string, meaning from tb_words where step='5'",null);
        }
        wordArr = new ArrayList<>();
        while(cursor.moveToNext()){
            String eng = cursor.getString(0);
            String kor = cursor.getString(1);
            Voca vc = new Voca(eng,kor);
            wordArr.add(vc);
        }
        stringView.setText(wordArr.get(index).eng);
        meaningView.setText(wordArr.get(index).kor);
    }


    public void PreviousBtn(View view){
        index--;
        if(index==-1){
            index=wordArr.size()-1;
        }
        stringView.setText(wordArr.get(index).eng);
        meaningView.setText(wordArr.get(index).kor);
    }
    public void NextBtn(View view){
        index++;
        if(index==wordArr.size()){
            index=0;
        }
        stringView.setText(wordArr.get(index).eng);
        meaningView.setText(wordArr.get(index).kor);
    }
}