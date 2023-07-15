package com.example.mybcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class addText_trans extends Activity {

    TextView txtText;
    LinearLayout listViewText;
    LinearLayout listViewCheck;
    CheckBox checkBox;
    int txtnum = 0;
    int boxnum = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_text_trans);

        //UI 객체생성
//        txtText = (TextView)findViewById(R.id.txtText);
        listViewText = findViewById(R.id.listViewText);
        listViewCheck = findViewById(R.id.listViewCheck);


        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        String arr[] = data.split("\n");
        for(int i=0; i<arr.length; i++) {
            createText(arr[i]);
        }
//        txtText.setText(data);

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Log.i("txtnum 체크", Integer.toString(txtnum));

        for(int i=0; i<txtnum; i++) {
            txtText = (TextView)findViewById(i);
            String text = txtText.getText().toString();
            checkBox = (CheckBox)findViewById(i+100);
            Log.i("잘린 문자열 체크 : ", text);
            if(checkBox.isChecked() == true) {
                SharedPreferences preferences = getSharedPreferences("text", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("input_text", text);
                editor.commit();
                ((cardTrans) cardTrans.mContext).createText();
            }
        }

        //액티비티(팝업) 닫기
        finish();
    }

    public void createText(String text){
        int size = 15;

        TextView newText = new TextView(getApplicationContext());
        CheckBox newBox = new CheckBox(getApplicationContext());
        newText.setText(text);
        newText.setTextSize(size);
        newText.setId(txtnum);
        newBox.setId(boxnum);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        param.topMargin = 27;
//        param.leftMargin = 20;
        newText.setLayoutParams(param);
        newText.setBackgroundColor(Color.alpha(0));
        listViewText.addView(newText);
        listViewCheck.addView(newBox);
        newText.bringToFront();
        Log.i("test text" , text);
        txtnum++;
        boxnum++;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
