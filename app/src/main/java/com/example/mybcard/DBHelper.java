package com.example.mybcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper{
    private SQLiteDatabase db = null;
    public DBHelper(Context context){
        super(context, "CardDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //groupTBL이라는 테이블이름으로 gName, gNumber 필드를 생성해주자
        db.execSQL("CREATE TABLE IF NOT EXISTS " + "CardDB" +
                "(" + "no_ INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cname TEXT," +
                "date TEXT," +
                "cache TEXT," +
                "additional TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //이곳에선 테이블이 존재하면 없애고 새로 만들어준다.
        db.execSQL("DROP TABLE IF EXISTS Card_s");
        onCreate(db);
    }

    public long insert(ContentValues addRowValue)
    {
        return db.insert("Card_s", null, addRowValue);
    }
}