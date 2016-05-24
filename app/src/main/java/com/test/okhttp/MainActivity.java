package com.test.okhttp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okio.BufferedSink;

public class MainActivity extends FragmentActivity {

    OkHttpClient client = new OkHttpClient();
    Timer timer = new Timer();
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void request() {

        //getSync();

        //getAnsync();

        //header();

        //postStr();

        //postStream();

        //postFile();

        //postForm();

        //postMultiPart();

        //parseResponse();

        //useCache();

        //cancelRequest();

        //timeout();

        //configCall();

        //verfiyconnect();

    }

    private void verfiyconnect() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            System.out.println("Authenticating for response: " + response);
                            System.out.println("Challenges: " + response.challenges());
                            String credential = Credentials.basic("jesse", "password1");
                            return response.request().newBuilder()
                                    .header("Authorization", credential)
                                    .build();
                        }
                    }).build();
                    Request request = new Request.Builder()
                            .url("http://publicobject.com/secrets/hellosecret.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    System.out.println(response.body().string());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void configCall() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Request request = new Request.Builder()
//                            .url("http://httpbin.org/delay/1") // This URL is served with a 1 second delay.
//                            .build();
//
//                    try {
//                        Response response = client.clone() // Clone to make a customized OkHttp for this request.
//                                .setReadTimeout(500, TimeUnit.MILLISECONDS)
//                                .newCall(request)
//                                .execute();
//                        System.out.println("Response 1 succeeded: " + response);
//                    } catch (IOException e) {
//                        System.out.println("Response 1 failed: " + e);
//                    }
//
//                    try {
//                        Response response = client.clone() // Clone to make a customized OkHttp for this request.
//                                .setReadTimeout(3000, TimeUnit.MILLISECONDS)
//                                .newCall(request)
//                                .execute();
//                        System.out.println("Response 2 succeeded: " + response);
//                    } catch (IOException e) {
//                        System.out.println("Response 2 failed: " + e);
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void timeout() {
        client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                            .build();
                    Response response = client.newCall(request).execute();
                    System.out.println("Response completed: " + response);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void cancelRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                            .build();
                    final long startNanos = System.nanoTime();
                    final Call call = client.newCall(request);
                    // Schedule a job to cancel the call in 1 second.
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.printf("%.2f Canceling call.%n", (System.nanoTime() - startNanos) / 1e9f);
                            call.cancel();
                            System.out.printf("%.2f Canceled call.%n", (System.nanoTime() - startNanos) / 1e9f);
                        }
                    }, 1000,1000);
                    try {
                        System.out.printf("%.2f Executing call.%n", (System.nanoTime() - startNanos) / 1e9f);
                        Response response = call.execute();
                        System.out.printf("%.2f Call was expected to fail, but completed: %s%n",(System.nanoTime() - startNanos) / 1e9f, response);
                    } catch (IOException e) {
                        System.out.printf("%.2f Call failed as expected: %s%n",(System.nanoTime() - startNanos) / 1e9f, e);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

//    Force a Network Response
//    In some situations, such as after a user clicks a 'refresh' button, it may be necessary to skip the cache, and fetch data directly from the server. To force a full refresh, add the no-cache directive:
//            connection.addRequestProperty("Cache-Control", "no-cache");
//    If it is only necessary to force a cached response to be validated by the server, use the more efficient max-age=0 instead:
//            connection.addRequestProperty("Cache-Control", "max-age=0");
//    Force a Cache Response
//    Sometimes you'll want to show resources if they are available immediately, but not otherwise. This can be used so your application can show something while waiting for the latest data to be downloaded. To restrict a request to locally-cached resources, add the only-if-cached directive:
//            try {
//        connection.addRequestProperty("Cache-Control", "only-if-cached");
//        InputStream cached = connection.getInputStream();
//        // the resource was cached! show it
//        catch (FileNotFoundException e) {
//            // the resource was not cached
//        }
//    }
//    This technique works even better in situations where a stale response is better than no response. To permit stale cached responses, use the max-stale directive with the maximum staleness in seconds:
//    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//    connection.addRequestProperty("Cache-Control", "max-stale=" + maxStale);


    private void useCache() {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File("/sdcard/Download");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        client = new OkHttpClient.Builder().cache(cache).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://publicobject.com/helloworld.txt")
                            .build();
                    Response response1 = client.newCall(request).execute();
                    if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

                    String response1Body = response1.body().string();
                    System.out.println("Response 1 response:          " + response1);
                    System.out.println("Response 1 cache response:    " + response1.cacheResponse());
                    System.out.println("Response 1 network response:  " + response1.networkResponse());

                    Response response2 = client.newCall(request).execute();
                    if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);

                    String response2Body = response2.body().string();
                    System.out.println("Response 2 response:          " + response2);
                    System.out.println("Response 2 cache response:    " + response2.cacheResponse());
                    System.out.println("Response 2 network response:  " + response2.networkResponse());

                    System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void parseResponse() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("https://api.github.com/gists/c2a7c39532239ff261be")
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    Gist gist = new Gson().fromJson(response.body().charStream(), Gist.class);
                    for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
                        System.out.println(entry.getKey());
                        System.out.println(entry.getValue().content);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    static class Gist {
        Map<String, GistFile> files;
    }

    static class GistFile {
        String content;
    }


    private void postMultiPart() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String IMGUR_CLIENT_ID = "...";
                    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                    //表单上传
//                    RequestBody multiBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                            .addFormDataPart("hello", "android")
//                            .addFormDataPart("photo", "photo_name", RequestBody.create(null, new File("")))
//                            .addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), new File(""))).build();
                    //表单上传
                    RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("hello", "jack")
                            .addFormDataPart("photo", "photo_name", RequestBody.create(null, new File("/sdcard/hello.png")))
                            .addPart(Headers.of("Content-Disposition", "form-data; name=\"title\""),
                                    RequestBody.create(null, "Square Logo"))
                            .addPart(Headers.of("Content-Disposition", "form-data; name=\"image\""),
                                    RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/hello.png")))
                            .build();

                    Request request = new Request.Builder()
                            .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                            .url("https://api.imgur.com/3/image")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    System.out.println(response.body().string());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void postForm() {
        //提交表单
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    RequestBody formBody = new FormEncodingBuilder()
//                            .add("search", "Jurassic Park")
//                            .build();
//                    Request request = new Request.Builder()
//                            .url("https://en.wikipedia.org/w/index.php")
//                            .post(formBody)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//                    System.out.println(response.body().string());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void postFile() {
        //提交文件
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
                    File file = new File("/sdcard/test.rar");
                    Request request = new Request.Builder()
                            .url("https://api.github.com/markdown/raw")
                            .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    System.out.println(response.body().string());

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void postStream() {
        //post 数据大于1M,应采用流的方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
                    RequestBody requestBody = new RequestBody() {
                        @Override
                        public MediaType contentType() {
                            return MEDIA_TYPE_MARKDOWN;
                        }
                        @Override
                        public void writeTo(BufferedSink sink) throws IOException {
                            sink.writeUtf8("Numbers\n");
                            sink.writeUtf8("-------\n");
                            for (int i = 2; i <= 997; i++) {
                                sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                            }
                        }
                        private String factor(int n) {
                            for (int i = 2; i < n; i++) {
                                int x = n / i;
                                if (x * i == n) return factor(x) + " × " + i;
                            }
                            return Integer.toString(n);
                        }
                    };

                    Request request = new Request.Builder()
                            .url("https://api.github.com/markdown/raw")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    System.out.println(response.body().string());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void postStr() {
        //post json数据,json数据不大于1M
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MediaType MEDIA_TYPE_MARKDOWN= MediaType.parse("text/x-markdown; charset=utf-8");
                    String postBody = ""
                            + "Releases\n"
                            + "--------\n"
                            + "\n"
                            + " * _1.0_ May 6, 2013\n"
                            + " * _1.1_ June 15, 2013\n"
                            + " * _1.2_ August 11, 2013\n";

                    Request request = new Request.Builder()
                            .url("https://api.github.com/markdown/raw")
                            .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    System.out.println(response.body().string());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void header() {
        //请求头信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Request request = new Request.Builder()
                            .url("https://api.github.com/repos/square/okhttp/issues")
                            .header("User-Agent", "OkHttp Headers.java")
                            .addHeader("Accept", "application/json; q=0.5")
                            .addHeader("Accept", "application/vnd.github.v3+json")
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    System.out.println("Server: " + response.header("Server"));
                    System.out.println("Date: " + response.header("Date"));
                    System.out.println("Vary: " + response.headers("Vary"));

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();

    }
    /**
     * 响应体的 string() 方法对于小文档来说十分方便、高效。
     * 但是如果响应体太大（超过1MB），应避免适应 string() 方法
     * 因为他会将把整个文档加载到内存中。
     */
    private void getAnsync() {
        //异步get请求
        try {

            MediaType JSON= MediaType.parse("application/json; charset=utf-8");
            //json请求
            String json = "{}";
            RequestBody body = RequestBody.create(JSON, json);

//            Request request = new Request.Builder().url("http://www.baidu.com").post(body).build();

            Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("test", "error===" + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e("test","success=="+response.body().string());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 响应体的 string() 方法对于小文档来说十分方便、高效。
     * 但是如果响应体太大（超过1MB），应避免适应 string() 方法
     * 因为他会将把整个文档加载到内存中。
     */
    private void getSync() {
        //同步get请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();
                    Response response = client.newCall(request).execute();
                    client.newCall(request).cancel();
                    String json = response.body().string();
                    Log.e("test", "response==" + json);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
