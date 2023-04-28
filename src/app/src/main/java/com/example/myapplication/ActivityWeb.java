package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class ActivityWeb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        // Loading URLs in the WebView
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        if (url == null || url.equals("")){
            Toast.makeText(getApplicationContext(), "URL is invalid", Toast.LENGTH_LONG).show();
        }else {
            WebView webView = (WebView) findViewById(R.id.WebView);
            webView.loadUrl(url);
        }
    }
}