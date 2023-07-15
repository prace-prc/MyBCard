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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addInfo extends Activity {

    TextView txtText;
    EditText cardName;
    EditText addiInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_info);

        //UI 객체생성


        //데이터 가져오기

    }

    //확인 버튼 클릭
    public void mOnClose(View v){

        cardName = (EditText)findViewById(R.id.cardName);
        addiInfo = (EditText)findViewById(R.id.addiInfo);
        String cname = cardName.getText().toString();
        String ainfo = addiInfo.getText().toString();
        //이름 값 입력되었는지 체크
        if(cname.isEmpty() == false){ // 입력되었으면 값 전송
            SharedPreferences preferences = getSharedPreferences("info",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("cname",cname);
            editor.putString("ainfo",ainfo);
            editor.commit();
            Toast.makeText(this, "입력 완료",Toast.LENGTH_LONG).show();
        }
        else{ // 입력 되지 않았으면 toast로 처리
            Toast.makeText(this, "이름이 입력되지 않았습니다.\n다시 입력해주세요",Toast.LENGTH_LONG).show();
        }
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
