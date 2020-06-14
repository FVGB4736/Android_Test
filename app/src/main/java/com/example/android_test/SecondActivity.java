package com.example.android_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private TextView tv2_startTime,tv2_endTime,tv2_temP;

    private SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findviewbyid();
        //設定資料
        Intent intent = getIntent();
        tv2_startTime.setText(intent.getStringExtra("startTime"));
        tv2_endTime.setText(intent.getStringExtra("endTime"));
        tv2_temP.setText(intent.getStringExtra("temP"));
    }
    public void findviewbyid(){
        tv2_startTime = findViewById(R.id.tv2_startTime);
        tv2_endTime = findViewById(R.id.tv2_endTime);
        tv2_temP = findViewById(R.id.tv2_temP);

    }

}
