package com.example.project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TodayWords extends AppCompatActivity {


    ListView wordsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_words);

        wordsListView = findViewById(R.id.words_list);
        Calendar cal = Calendar.getInstance();
        int today = cal.get((Calendar.DAY_OF_MONTH));
        String check;

        // tb_today에서 유호 데이터 10개 꺼내오기위함
        ArrayList<HashMap<String, String>> wordsData = new ArrayList<>();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor words_cursor, today_cursor;

        //일단 today 테이블에 garbage값 하나를 집어넣어주고 시작. table null검사 하기위해 넣는거나 마찬가지
        db.execSQL("insert into tb_today (string, meaning, day) values (?, ?, ?)", new String[]{"trash", "trash", "trash"});
        today_cursor = db.rawQuery("select day from tb_today order by _id asc limit 1", null);
        today_cursor.moveToNext();
        check = today_cursor.getString(0);

        // 테이블에 저장된 day가 다를 경우에만 테이블을 갱신해줌.
        if (!check.equals(String.valueOf(today))) {
            db.execSQL("delete from tb_today");
            ;
            // 랜덤 시작지점으로부터 10개단어 불러와서 저장
            words_cursor = db.rawQuery("select string, meaning from tb_words order by random() limit 10", null);

            while (words_cursor.moveToNext()) {
                db.execSQL("insert into tb_today (string, meaning, day) values (?, ?, ?)",
                        new String[]{words_cursor.getString(0), words_cursor.getString(1),
                                String.valueOf(today)});
            }
        }
        today_cursor = db.rawQuery("select string, meaning from tb_today order by _id asc limit 10", null);
        while (today_cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("string", today_cursor.getString(0));
            map.put("meaning", today_cursor.getString(1));
            wordsData.add(map);
        }
        db.close();
        SimpleAdapter adapter = new SimpleAdapter(this,
                wordsData,
                android.R.layout.simple_list_item_2,
                new String[]{"string", "meaning"},
                new int[]{android.R.id.text1, android.R.id.text2});
        wordsListView.setAdapter(adapter);

    }


}
