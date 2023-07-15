package com.example.mybcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class addText extends Activity {

    TextView txtText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_text);

        //UI 객체생성


        //데이터 가져오기
//        Intent intent = getIntent();
//        String data = intent.getStringExtra("data");
//        txtText.setText(data);

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기


        txtText = (TextView)findViewById(R.id.txtText);
        String text = txtText.getText().toString();
        if (text.isEmpty() == false){
        SharedPreferences preferences = getSharedPreferences("text",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("input_text",text);
        editor.commit();
        ((cardCreate)cardCreate.mContext).createText();
        }
        else{
            Toast.makeText(this, "값이 입력되지 않았습니다. 다시 입력해주세요",Toast.LENGTH_LONG).show();
        }

        Log.i("test", "Input text : " + txtText.getText().toString());


        //액티비티(팝업) 닫기
        finish();
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
