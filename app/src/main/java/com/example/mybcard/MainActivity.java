package com.example.mybcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cardCreate = findViewById(R.id.createButton);
        Button cardTrans = findViewById(R.id.transButton);
        Button cardManage = findViewById(R.id.manageButton);

        cardCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), cardCreate.class);
                startActivity(intent);
            }
        });
        cardTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), cardTrans.class);
                startActivity(intent);
            }
        });
        cardManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), cardManage.class);
                startActivity(intent);
            }
        });
    }
}