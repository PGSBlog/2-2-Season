package com.example.lab999;


import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GamePart extends AppCompatActivity {
    String correct;
    Button[] btn = new Button[4];
    private int currentScore, cAnswerCount, wAnswerCount;
    //틀린 단어 출력하는 기능있으면 좋을거같은데 일단 보류 + landscape일 때 화면구성 다르게
//    ArrayList<HashMap<String, String>> wrongAnswers = new ArrayList<>();
    TextView stringView;
    TextView scoreView;
    static final int BTN_COUNT = 4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_part);

        Intent intent = getIntent();
        Bundle bundleData = intent.getBundleExtra("CHECK_DATA");
        correct = "";

        btn[0] = (Button) findViewById(R.id.btn1);
        btn[1] = (Button) findViewById(R.id.btn2);
        btn[2] = (Button) findViewById(R.id.btn3);
        btn[3] = (Button) findViewById(R.id.btn4);
        stringView = (TextView) findViewById(R.id.quiz);
        scoreView = (TextView) findViewById(R.id.score);



        //첫 실행시에 초기값 세팅
        if (bundleData == null) {
            currentScore = 0;
            cAnswerCount = 0;
            wAnswerCount = 0;
            scoreView.setText("점수 : " + 0);
        } else {
            scoreView.setText("점수 : " + bundleData.getInt("SCORE_DATA"));
            currentScore = bundleData.getInt("SCORE_DATA");
            cAnswerCount = bundleData.getInt("CORRECT_COUNT");
            wAnswerCount = bundleData.getInt("WRONG_COUNT");
        }
        for (int i = 0; i < BTN_COUNT; i++)
            btn[i].setOnClickListener(new BtnListener());


        Random rd = new Random();
        DBHelper helper = new DBHelper(this);
        boolean flag = true; // 중복 뜻 피하기위해
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select _id from tb_words order by _id desc limit 1", null);
        cursor.moveToNext();
        String MaxId = cursor.getString(0);
        int randi = rd.nextInt(Integer.parseInt(MaxId)) + 1;
        String randStr = String.valueOf(randi);
        cursor = db.rawQuery("select string, meaning from tb_words where _id = ?", new String[]{randStr});

        while (cursor.moveToNext()) {
            stringView.setText(cursor.getString(0));
            correct = cursor.getString(1);
        }

        for (int i = 0; i < BTN_COUNT; i++) {
            randi = rd.nextInt(Integer.parseInt(MaxId)) + 1;
            randStr = String.valueOf(randi);
            cursor = db.rawQuery("select meaning from tb_words where _id = ?", new String[]{randStr});
            cursor.moveToNext();
            btn[i].setText(cursor.getString(0));
            for (int j = 0; j < i; j++) {
                while (btn[j].getText().equals(btn[i].getText())) {
                    randi = rd.nextInt(Integer.parseInt(MaxId)) + 1;
                    randStr = String.valueOf(randi);
                    cursor = db.rawQuery("select meaning from tb_words where _id = ?", new String[]{randStr});
                    cursor.moveToNext();
                    btn[i].setText(cursor.getString(0));
                }
            }
        }
        for (int i = 0; i < BTN_COUNT; i++) {
            if (correct.equals(btn[i].getText())) {
                flag = false;
                break;
            }
        }
        if (flag) {
            randi = rd.nextInt(BTN_COUNT);
            btn[randi].setText(correct);
        }
        db.close();
    }

    private class BtnListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            boolean flag = true;
            for (int i = 0; i < BTN_COUNT; i++)
                if (view == btn[i])
                    if (correct.equals(btn[i].getText())) {
                        showToast("정답입니다! ");
                        currentScore += 100;
                        cAnswerCount++;
                        //토탈 스코어가 1000점 이상이면 게임종료.
                        if (currentScore > 999) {
                            Intent resultIntent = new Intent();
                            Bundle resultBundleData = new Bundle();
                            resultBundleData.putInt("SCORE_DATA", currentScore);
                            resultBundleData.putInt("CORRECT_COUNT", cAnswerCount);
                            resultBundleData.putInt("WRONG_COUNT", wAnswerCount);
                            ComponentName componentName = new ComponentName(
                                    "com.example.project",
                                    "com.example.project.GameResult");
                            resultIntent.setComponent(componentName);
                            resultIntent.putExtra("RESULT_DATA", resultBundleData);
                            startActivity(resultIntent);
                        } else {
                            flag = false;
                            Intent intent = new Intent();
                            Bundle bundleData = new Bundle();
                            bundleData.putInt("SCORE_DATA", currentScore);
                            bundleData.putInt("CORRECT_COUNT", cAnswerCount);
                            bundleData.putInt("WRONG_COUNT", wAnswerCount);
                            ComponentName componentName = new ComponentName(
                                    "com.example.project",
                                    "com.example.project.GamePart");
                            intent.setComponent(componentName);
//                          intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("CHECK_DATA", bundleData);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_left_exit, R.anim.slide_right_enter);
                        }
                    }
            if (flag) {
                showToast("오답입니다.");
                currentScore -= 60;
                wAnswerCount++;
                scoreView.setText("점수 : " + currentScore);
            }
        }
    }

    // 일일이 .show()하기 귀찮아서 그냥 메서드를 만들어 놓음
    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}