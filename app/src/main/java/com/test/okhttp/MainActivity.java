package com.test.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.reqeust).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });

    }

    private void request() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("test","request");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://www.baidu.com").build();
                    Response response = client.newCall(request).execute();
                    client.newCall(request).cancel();
                    String json = response.body().string();
                    Log.e("test", "json==" + json);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        try {

            Log.e("test","request");
            OkHttpClient client = new OkHttpClient();
            MediaType JSON= MediaType.parse("application/json; charset=utf-8");
            //json请求
            String json = "{}";
            RequestBody body = RequestBody.create(JSON,json);

            //表单上传
            RequestBody multiBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("hello", "android")
                    .addFormDataPart("photo", "photo_name", RequestBody.create(null, new File("")))
                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""),RequestBody.create(MediaType.parse("application/octet-stream"),new File(""))).build();
            Request request = new Request.Builder().url("http://www.baidu.com").post(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("test","异步请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e("test","异步请求成功=="+response.body().string());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
