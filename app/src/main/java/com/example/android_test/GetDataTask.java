package com.example.android_test;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataTask extends AsyncTask<String,Integer,String> {
    private final static String TAG = "GetDataTask";
    private String url, outStr;

    public GetDataTask(String url){
        this.url=url;
    }

    public GetDataTask(String url,String outStr){
        this.url=url;
        this.outStr=outStr;
    }
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        StringBuilder inStr = new StringBuilder();

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
//            bw.write(outStr);
//            bw.close();

            int responseCode = connection.getResponseCode();
            if(responseCode==200){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String nextLine;
                while((nextLine = br.readLine()) != null){
                    inStr.append(nextLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }

        return inStr.toString();
    }
}
