package com.example.android.quakereport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;


public class ShowUsTheWebPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_us_the_web_page);

        Intent intent = getIntent();
        String thatCode = intent.getStringExtra(EarthquakeActivity.EXTRA_MESSAGE);
        WebView webview = (WebView) findViewById(R.id.theWeb);
        webview.loadUrl(thatCode);
        webview.setWebViewClient(new MyLitleBrowser());
        webview.getSettings().setJavaScriptEnabled(true);
    }

//This is a custom browser which make the layout acts like an browser itself
// rather than just displaying content inside the layout, its more sensitive and user friendly.

    public class MyLitleBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}









