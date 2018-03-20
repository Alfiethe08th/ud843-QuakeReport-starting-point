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
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyLitleBrowser());
    }
//This is custom browser which enables web browsing rather than the traditional one inside webview
    public class MyLitleBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}









