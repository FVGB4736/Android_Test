package com.example.android_test;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private RecyclerView dataRV;
    private GetDataTask getWeatherDataTask;
    private String url = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-80AD1231-3348-43AF-8687-95E3FA161953&locationName=%E8%87%BA%E5%8C%97%E5%B8%82&elementName=MinT";
    private String dataResult;


    private List<DataVO> weatherData;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //請求網路權限
        askpermission();

        findviewbyid();
        //獲得資料
        getWeatherDataTask = new GetDataTask(url);
        try {
            dataResult = getWeatherDataTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //測試拿資料
//        Log.e(TAG,dataResult);

        //處理資料並存入weatherData中
        handledata(dataResult);


        final SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        sectionAdapter.addSection(new SectionAdapter(this, R.drawable.ic_android_black_24dp, weatherData.get(0)));
        sectionAdapter.addSection(new SectionAdapter(this, R.drawable.ic_android_green_24dp, weatherData.get(1)));
        sectionAdapter.addSection(new SectionAdapter(this, R.drawable.ic_android_red_24dp, weatherData.get(2)));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        dataRV.setLayoutManager(layoutManager);
        dataRV.setAdapter(sectionAdapter);

    }

    public void askpermission(){
        int permissionInt = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if(permissionInt != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},0);
        }
        else{

        }
    }

    public void findviewbyid(){
        dataRV = findViewById(R.id.dataRV);
    }

    public void handledata(String dataResult){
        weatherData = new ArrayList<DataVO>();

        try {
            JSONObject jsonObj = new JSONObject(dataResult);
            JSONArray jsonArray;

            jsonArray = jsonObj.getJSONObject("records").getJSONArray("location").getJSONObject(0).getJSONArray("weatherElement").getJSONObject(0).getJSONArray("time");
            //查看jsonArray
            Log.e(TAG,jsonArray.toString());

            int jsonArrayLength= jsonArray.length();

            for(int i=0;i<jsonArrayLength;i++){
                DataVO dataVO = new DataVO();
                String startTime = jsonArray.getJSONObject(i).getString("startTime");
                String endTime = jsonArray.getJSONObject(i).getString("endTime");
                //溫度 + 攝氏
                String temP =jsonArray.getJSONObject(i).getJSONObject("parameter").getString("parameterName") + jsonArray.getJSONObject(i).getJSONObject("parameter").getString("parameterUnit");

                dataVO.setStartTime(startTime);
                dataVO.setEndTime(endTime);
                dataVO.setTemP(temP);
                //存入weatherData中
                weatherData.add(dataVO);
                //檢查
//                Log.e(TAG,weatherData.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
