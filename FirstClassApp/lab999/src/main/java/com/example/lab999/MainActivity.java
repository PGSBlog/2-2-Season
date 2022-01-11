package com.example.lab999;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    ImageButton btnClick;
    Button btn_papago;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        // 초기에 db에 아무값도 없으면 기본으로 15개의 단어를 넣어줌.
        Cursor cursor = db.rawQuery("SELECT * FROM  tb_words ", null);
        if(!cursor.moveToNext()) {
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"horse","말","1"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"great","훌륭한","1"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"happy","기쁜","1"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"parameter","매개변수","2"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"color","색","2"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"star","별","3"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"light","빛","3"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"ride","타다","4"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"rising","떠오르는","4"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"realize","깨닫다","5"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"circle","원","5"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"propose","제안하다","3"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"resume","재개해다","4"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"create","만들다","2"});
            db.execSQL("insert into tb_words (string, meaning, step) values (? , ? , ? )", new String[]{"cat","고양이","1"});
        }
        db.close();

        btnClick = findViewById(R.id.BtnPapago);
        btn_papago = findViewById(R.id.p);
        btnClick.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.btn_sheet,(LinearLayout)findViewById(R.id.bottomSheetContainer));
                bottomSheetView.findViewById(R.id.p).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();

                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://papago.naver.com/"));
                        startActivity(intent);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.y).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();

                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.ybm.co.kr/"));
                        startActivity(intent);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.n).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();

                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.yanadoo.co.kr/"));
                        startActivity(intent);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


    }
    public void Button1stOnClick(View v){
        startActivity(new Intent(this, com.example.lab999.MemorizeMain.class));
    }
    public void Button2ndOnClick(View v){
        startActivity(new Intent(this, com.example.lab999.GamePart.class));
    }
    public void Button3rdOnClick(View v){
        startActivity(new Intent(this, com.example.lab999.AddWords.class));
    }
    public void Button4thOnClick(View v){
        startActivity(new Intent(this, com.example.lab999.TodayWords.class));
    }
}