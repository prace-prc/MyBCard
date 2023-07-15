package com.example.mybcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class cardManage extends AppCompatActivity {

    FrameLayout listView;
    SQLiteDatabase sqlDB;
    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage);
        DBManager dbManager = DBManager.getInstance(this);
        listView = findViewById(R.id.listView);
        image = findViewById(R.id.imageView3);
        text = findViewById(R.id.cardName);

        Button checkbtn = findViewById(R.id.checkbtn);

        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] columns = new String[] {"no_", "cname", "date", "cache", "additional"};
                   Cursor cursor = dbManager.query(columns, null,null,null,null,null);

                    int num = 0;
                    String cname = null;
                    String date = null;
                    String cache = null;
                    String additional = null;

                   while(cursor.moveToNext()){
                       num = cursor.getInt(0);
                       cname = cursor.getString(1);
                       date = cursor.getString(2);
                       cache = cursor.getString(3);
                       additional = cursor.getString(4);
                    }
                   Log.i("test int", Integer.toString(num));
                   Log.i("test name", cname);
                   createImage(cache);
                   text.setText(cname);
                   image.setImageBitmap(StringToBitMap(cache));
            }
        });
    }
    public void createImage(String cache){
        Bitmap bit = StringToBitMap(cache);

        ImageView newImage = new ImageView(getApplicationContext());
        newImage.setImageBitmap(bit);
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        param.topMargin = 20;
        param.leftMargin = 20;
        newImage.setLayoutParams(param);
        listView.addView(newImage);
    }
    public Bitmap StringToBitMap(String encoded){
        try{
            byte [] encode = Base64.decode(encoded,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encode, 0, encode.length);

            return bitmap;
        }
        catch (Exception e){
            return null;
        }
    }

}