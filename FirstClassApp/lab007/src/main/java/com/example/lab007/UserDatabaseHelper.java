package com.example.lab007;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDatabaseHelper<PASSWORLD> extends SQLiteOpenHelper {
    //---(Singleton 적용) -------------------------------
    private static UserDatabaseHelper instance;
    public static synchronized UserDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new UserDatabaseHelper(context, "User", null, 1);
        }
        return instance;
    }
    //---------------------------------------------------
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PASSWORD = "meaning";

    public static final String SQL_CREATE_USER =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT" +
                    ");";
    private UserDatabaseHelper(@Nullable Context context, @Nullable String name,
                               @Nullable SQLiteDatabase.CursorFactory factory, int version){
super(context, name, factory, version);
}
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// DB 버전에 따른 변경사항 적용 - 예제에서는 그냥 DB Table삭제후 재생성함
        if (newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME);
        }
    }
}