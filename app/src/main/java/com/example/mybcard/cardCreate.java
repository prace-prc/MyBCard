package com.example.mybcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cardCreate extends AppCompatActivity implements View.OnTouchListener {
    public static Context mContext;
    TextView textView;
    FrameLayout listView;
    Button okButton;
    SQLiteDatabase sqlDB;
//    DBHelper dbHelper;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_create);
        Button addButton = findViewById(R.id.addText);
        okButton = findViewById(R.id.okButton);
        listView = findViewById(R.id.listView);
        mContext = this;
        DBManager dbManager = DBManager.getInstance(this);
//        dbHelper = new DBHelper(this);
//        sqlDB = dbHelper.getReadableDatabase();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지를 bitmap화 후 string 변환한 캐시
                listView.setDrawingCacheEnabled(true);
                Bitmap bm = listView.getDrawingCache();
                String cache = BitMaptoString(bm);

                //입력된 명함 이름과 추가 정보 가져오기
                SharedPreferences preferences = getSharedPreferences("info",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String cname = preferences.getString("cname", "def");
                String ainfo = preferences.getString("ainfo", "def");


                if(cname != "def") {
                    //정보들 DB로 전송
                    ContentValues addRowValue = new ContentValues();
                    addRowValue.put("cname", cname);
                    Log.i("cname", cname);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date now = new Date();
                    String nowTime = sdf.format(now);
                    addRowValue.put("date", nowTime);
                    Log.i("time", nowTime);
                    addRowValue.put("cache", cache);
                    Log.i("cache", cache);
                    addRowValue.put("additional", ainfo);
                    Log.i("aInfo", ainfo);
//                    dbHelper.insert(addRowValue);
                    dbManager.insert(addRowValue);
                    Toast.makeText(getApplicationContext(),"명함을 저장했습니다.",Toast.LENGTH_LONG).show();
                    editor.putString("cname", "def");
                    editor.commit();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"명함이 완성되지 않았습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void createText(){
        SharedPreferences preferences = getSharedPreferences("text",MODE_PRIVATE);
        String text = preferences.getString("input_text","def");
        Log.i("test", "Return text : " + text);
        int size = 18;

        TextView newText = new TextView(getApplicationContext());
        newText.setText(text);
        newText.setTextSize(size);
        newText.setTypeface(null, Typeface.BOLD);
        newText.setId(0);
//        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        param.topMargin = 20;
        param.leftMargin = 20;
        newText.setLayoutParams(param);
        newText.setBackgroundColor(Color.alpha(0));
        listView.addView(newText);
        newText.bringToFront();
        newText.setOnTouchListener(this);
    }

    public void createImage(){
        SharedPreferences preferences = getSharedPreferences("image",MODE_PRIVATE);
        String image = preferences.getString("imagestring","");
        Bitmap bitmap = StringToBitMap(image);

        Log.i("image : " ,image);

        ImageView newImage = new ImageView(getApplicationContext());
        newImage.setImageBitmap(bitmap);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        param.topMargin = 20;
        param.leftMargin = 20;
        newImage.setLayoutParams(param);
        listView.addView(newImage);
        newImage.setOnTouchListener(this);
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
    public String BitMaptoString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte [] b = baos.toByteArray();
        String temp = Base64.encodeToString(b,Base64.DEFAULT);
        return temp;
    }

    float oldXvalue;
    float oldYvalue;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int width = ((ViewGroup) v.getParent()).getWidth() - v.getWidth();
        int height = ((ViewGroup) v.getParent()).getHeight() - v.getHeight();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldXvalue = event.getX();
            oldYvalue = event.getY();
            //  Log.i("Tag1", "Action Down X" + event.getX() + "," + event.getY());
            Log.i("Tag1", "Action Down rX " + event.getRawX() + "," + event.getRawY());
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            v.setX(event.getRawX() - oldXvalue);
            v.setY(event.getRawY() - (oldYvalue + v.getHeight()));
            //  Log.i("Tag2", "Action Down " + me.getRawX() + "," + me.getRawY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (v.getX() > width && v.getY() > height) {
                v.setX(width);
                v.setY(height);
            } else if (v.getX() < 0 && v.getY() > height) {
                v.setX(0);
                v.setY(height);
            } else if (v.getX() > width && v.getY() < 0) {
                v.setX(width);
                v.setY(0);
            } else if (v.getX() < 0 && v.getY() < 0) {
                v.setX(0);
                v.setY(0);
            } else if (v.getX() < 0 || v.getX() > width) {
                if (v.getX() < 0) {
                    v.setX(0);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight());
                } else {
                    v.setX(width);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight());
                }
            } else if (v.getY() < 0 || v.getY() > height) {
                if (v.getY() < 0) {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(0);
                } else {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(height);
                }
            }


        }
        return true;
    }

    public void textPopup(View v){
        Intent intent = new Intent(this, addText.class);
//        intent.putExtra("data", "Test Popup");
        startActivityForResult(intent, 1);
    }

    public void imagePopup(View v){
        Intent intent = new Intent(this, addImage.class);
//        intent.putExtra("data", "Test Popup");
        startActivityForResult(intent, 1);
    }

    public void infoPopup(View v){
        Intent intent = new Intent(this, addInfo.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}