package com.example.alejandro.navigationdrawersample.http;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okio.BufferedSink;

/**
 * Created by Alejandro on 4/11/2015.
 */
public class WebServiceManager {
    private final static String BASE_URL = "http://jsonplaceholder.typicode.com/";
    private final static String USER_URL ="users/";
    private final static String ALBUM_URL ="albums/";
    private static final String POSTS_URL = "posts/";


    private static final String TAG = WebServiceManager.class.getSimpleName();

    public static void requestForUserInfo(int id, Callback callback){
        String strUrl = BASE_URL + USER_URL;
        strUrl+=id;
        generalRequest(strUrl,callback);


    }

    public static void requestAlbumsUser(int id, Callback callback){
        String strUrl = BASE_URL + ALBUM_URL+"?userId="+id;
        generalRequest(strUrl,callback);

    }

    public static void requestPostTitles(int id, Callback callback){
        String strUrl = BASE_URL + POSTS_URL+"?userId="+id;
        generalRequest(strUrl, callback);

    }



    private static void generalRequest(String url ,Callback callback){
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.SECONDS);
        client.setReadTimeout(5,TimeUnit.SECONDS);
        Log.i(TAG, "This is the target URL " + url);
        Request request = new Request.Builder()
                .url(url)

                .build();
        client.newCall(request).enqueue(callback);


    }
    {}

    public static void requestPostComments(int postId, Callback callback) {
        String strUrl = BASE_URL + POSTS_URL+postId+"/comments";
        generalRequest(strUrl, callback);

    }

    public static void requestAlbumPhotos(int albumId, Callback callback) {
        String strUrl = BASE_URL + ALBUM_URL+albumId+"/photos";
        generalRequest(strUrl, callback);


    }
}
