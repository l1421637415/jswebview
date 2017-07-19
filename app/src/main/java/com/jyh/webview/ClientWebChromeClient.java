package com.jyh.webview;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.jyh.webview.jsbridge.JsBridgeEngine;

/**
 * Created by jiangyaohui on 2017/7/3.
 */

public class ClientWebChromeClient extends WebChromeClient{

    @Override
    public boolean onJsPrompt(WebView webView, String url, String mesg, String defVal, JsPromptResult jsPromptResult){
        jsPromptResult.confirm("");
        JsBridgeEngine.INSTANCE.handRequest(mesg);
        return true;
    }
}
