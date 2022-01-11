package com.example.project;


import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
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
        Intent gameIntent = new Intent();
        ComponentName componentName = new ComponentName (
                "com.example.project",
                "com.example.project.MemorizeMain");
        gameIntent.setComponent(componentName);
        startActivity(gameIntent);
    }
    public void Button2ndOnClick(View v){
        Intent gameIntent = new Intent();
        ComponentName componentName = new ComponentName (
                "com.example.project",
                "com.example.project.GamePart");
        gameIntent.setComponent(componentName);
        startActivity(gameIntent);
    }
    public void Button3rdOnClick(View v){
        Intent gameIntent = new Intent();
        ComponentName componentName = new ComponentName (
                "com.example.project",
                "com.example.project.AddWords");
        gameIntent.setComponent(componentName);
        startActivity(gameIntent);
    }
    public void Button4thOnClick(View v){
        Intent gameIntent = new Intent();
        ComponentName componentName = new ComponentName (
                "com.example.project",
                "com.example.project.TodayWords");
        gameIntent.setComponent(componentName);
        startActivity(gameIntent);
    }
}