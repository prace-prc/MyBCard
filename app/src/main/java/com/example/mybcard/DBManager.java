package com.example.mybcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    static final String DB_CARD = "Card.db";   //DB이름
    static final String TABLE_CARD = "Cards"; //Table 이름
    static final int DB_VERSION = 1;			//DB 버전

    Context myContext = null;

    private static DBManager myDBManager = null;
    private SQLiteDatabase mydatabase = null;

    public static DBManager getInstance(Context context)
    {
        if(myDBManager == null)
        {
            myDBManager = new DBManager(context);
        }

        return myDBManager;
    }

    private DBManager(Context context)
    {
        myContext = context;

        //DB Open
        mydatabase = context.openOrCreateDatabase(DB_CARD, context.MODE_PRIVATE,null);

        //Table 생성
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CARD +
                "(" + "no_ INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cname TEXT," +
                "date TEXT," +
                "cache TEXT," +
                "additional TEXT);");
    }
    public long insert(ContentValues addRowValue)
    {
        return mydatabase.insert(TABLE_CARD, null, addRowValue);
    }
//    public String search(){
//
//    }
    public Cursor query(String[] colums,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
                        String orderby)
    {
        return mydatabase.query(TABLE_CARD,
                colums,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderby);
    }
}
