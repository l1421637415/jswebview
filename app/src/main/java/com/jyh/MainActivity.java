package com.jyh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jyh.webview.ClientWebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientWebView clientWebView = (ClientWebView)findViewById(R.id.client_web_view);
        clientWebView.loadUrl("http://10.156.16.172:8080/kyh/v1/jsbridge_jyh_demo.html");
    }
}
