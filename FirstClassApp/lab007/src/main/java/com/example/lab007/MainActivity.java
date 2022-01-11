package com.example.lab007;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText id_string, id_meaning, id_step;

    String dbName="Data.db"; //database 파일명
    String tableName="tb_words"; // SQL데이터 Table 이름

    SQLiteDatabase db;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    } // 메뉴에 관한 소스코드. 필요없어서 삭제!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_string = findViewById(R.id.string_editText);
        id_meaning = findViewById(R.id.meaning_editText);
        id_step = findViewById(R.id.step_editText);

        //dbName으로 데이터베이스 파일 생성 또는 열기
        db= this.openOrCreateDatabase(dbName, MODE_PRIVATE, null); // db문서를 열거나, 없으면 만드는 작업처리합니다.

        //만들어진 DB파일에 "tb_words"라는 이름으로 테이블 생성 작업 수행
        db.execSQL("CREATE TABLE IF NOT EXISTS "+tableName+"(num integer primary key autoincrement, string text not null, meaning text, step integer);");//괄호 안에 SQL언어 쓰면 됨.
        //IF NOT EXISTS <- 만약 존재하지 않으면 만들어야함.
    }

    public void clickInsert(View view) { // 데이터 입력한 것들 sql에 저장.

        String string = id_string.getText().toString();
        String meaning =id_meaning.getText().toString();
        int step=Integer.parseInt(id_step.getText().toString());

        //데이터베이스에 데이터를 삽입(insert)하는 쿼리문 실행
        db.execSQL("INSERT INTO "+tableName+"(string, meaning, step) VALUES('"+string+"','"+meaning+"','"+step+"');");

        id_string.setText("");
        id_meaning.setText("");
        id_step.setText("");

    }

    public void clickSelectAll(View view) {

        //*은 모든 데이터가 아닌 모든 컬룸을 말하고 두번째 파라미터가 null이기 때문에 모든 데이터를 가져온다.
        Cursor cursor =db.rawQuery("SELECT * FROM "+tableName+"",null); //두 번째 파라미터: 겁색 조건
        //Cursor객체: 결과 table을 참조하는 객체
        //Cursor객체를 통해 결과 table의 값들을
        //읽어오는 것임.
        if(cursor==null) return;

        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext()){
            int num=cursor.getInt(0);
            String string=cursor.getString(1);
            String meaning= cursor.getString(2);
            int step= cursor.getInt(3);
            buffer.append(num+" 단어 : "+string+" 뜻 : "+meaning+" 단계 : "+step+"\n");
        }
        // 단어 : " + id + ", 뜻 : " + meaning

        new AlertDialog.Builder(this, R.style.MyAlertDialogStyle).setMessage(buffer.toString()).setPositiveButton("확인",null).create().show();
    }

    public void clickSelectByName(View view) {
        String stringname = id_string.getText().toString();

        Cursor cursor=db.rawQuery("SELECT stringname, step FROM "+tableName+" WHERE stringname=?",new String[]{stringname});
        if(cursor==null) return;

        //참고: 총 레코드 수(줄,행(row)수)
        int rowNum= cursor.getCount(); //데이터의 행의 수 지웠을때 지워졌는지 확인.

        StringBuffer buffer= new StringBuffer();
        while(cursor.moveToNext()){

            String name=cursor.getString(0);
            int step= cursor.getInt(1);

            buffer.append(name+"  "+step+"\n");
            Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();

        }
    }

//    public void clickUpdate(View view) {
//        String string = String.getText().toString();
//        db.execSQL("UPDATE "+tableName+" SET step=30, meaning='ss@ss.com' WHERE string=?",new String[]{string});
//    } // 수정하는 코드인데 필요없을것 같아서 삭제!

    public void clickDelete(View view) {
        String string =id_string.getText().toString();
        db.execSQL("DELETE FROM "+tableName+" WHERE string=?",new String[]{string});
    }

}


// 버전 2
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//public class MainActivity extends AppCompatActivity {
//    SwipeRefreshLayout swipeRefreshLayout;
//    // ArrayList<Integer> arrayList;
//    private UserDatabaseHelper userDatabaseHelper;
//    public static final String TABLE_NAME = "user";
//    SQLiteDatabase database;
//    Button selectButton;
//    Button insertButton;
//    Button deleteButton;
//    EditText idEdittext;
//    EditText meaningEditText;
//    TextView textView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//       // swipeRefreshLayout = findViewById(R.id.swipe_refresh);
//      //  arrayList = new ArrayList<>();
//      //  swipeRefreshLayout.setRefreshing(false);
//
//        selectButton = findViewById(R.id.select_button);
//        insertButton = findViewById(R.id.insert_button);
//        deleteButton = findViewById(R.id.delete_button);
//        idEdittext = findViewById(R.id.id_editText);
//        meaningEditText = findViewById(R.id.meaning_editText);
//        textView = findViewById(R.id.textView);
//        userDatabaseHelper = UserDatabaseHelper.getInstance(this);
//        database = userDatabaseHelper.getWritableDatabase();
//        selectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectData(TABLE_NAME);
//            }
//        });
//        insertButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String id = idEdittext.getText().toString().trim();
//                String meaning = meaningEditText.getText().toString().trim();
//                insertData(id, meaning);
//            }
//        });
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String id = idEdittext.getText().toString().trim();
//                deleteData(id);
//            }
//        });
//    }
//    private void selectData(String tableName) {
//        if (database != null) {
//            String sql = "SELECT id, meaning FROM " + tableName;
//            Cursor cursor = database.rawQuery(sql, null);
//            println("데이터 갯수 : " + cursor.getCount());
//            for (int i = 0; i <cursor.getCount(); i++) {
//                cursor.moveToNext();
//                String id = cursor.getString(0);
//                String meaning = cursor.getString(1);
////int number = cursor.getInt(3);
//                println("[" + i + "] 단어 : " + id + ", 뜻 : " + meaning);
//            }
//            cursor.close();
//        }
//    }
//    private void insertData(String id, String meaning) {
//        if (database != null) {
//            String sql = "INSERT INTO user(id, meaning) VALUES(?, ?)";Object[] params = {id, meaning};
//            database.execSQL(sql, params);
//        }
//    }
//    private void deleteData(String id) {
//        if (database != null) {
//            String sql = "DELETE FROM user WHERE id=?";
//            Object[] params = {id};
//            database.execSQL(sql, params);
//        }
//    }
//    public void println (String data) {
//        textView.append(data + "\n");
//    }
//    @Override
//    protected void onDestroy() {
//        userDatabaseHelper.close();
//        super.onDestroy();
//    }
//}
