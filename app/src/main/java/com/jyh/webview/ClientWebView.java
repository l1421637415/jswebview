package com.jyh.webview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jyh.webview.jsbridge.JsBridge;
import com.jyh.webview.jsbridge.JsBridgeEngine;
import com.jyh.webview.jsbridge.common.HardwareInfo;

/**
 * Created by jiangyaohui on 2017/7/3.
 */

public class ClientWebView extends WebView{
    public ClientWebView(Context context) {
        super(context);
        defulInitSettings();
        registDefJsBridges();
    }

    public ClientWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        defulInitSettings();
        registDefJsBridges();
    }

    public ClientWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defulInitSettings();
        registDefJsBridges();
    }

    private void registDefJsBridges(){
        JsBridgeEngine.INSTANCE.bindWebView(this);
        registJsBridge(new HardwareInfo(getContext()));
    }

    private void defulInitSettings(){
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true); //允许执行js
        if (Build.VERSION.SDK_INT>16){
            webSettings.setMediaPlaybackRequiresUserGesture(true); //api17 是否通过手势触发播放媒体，默认是true，需要手势触发
        }

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); //webview组件缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(false); //是否支持缩放 前提下面方法开放
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(true); //隐藏原生的缩放控件

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //不使用缓存
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        webSettings.setAllowFileAccess(false); //设置为不可访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); //禁止通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setSaveFormData(true); // 保存表单数据
        if(Build.VERSION.SDK_INT>15){
             webSettings.setAllowFileAccessFromFileURLs(false); //api16 是否允许js跨域访问
        }
        if(Build.VERSION.SDK_INT>20){
            //当一个安全的来源（origin）试图从一个不安全的来源加载资源时配置WebView的行为
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        }

        setWebChromeClient(new ClientWebChromeClient());
    }

    public void registJsBridge(JsBridge jsBridge){
        JsBridgeEngine.INSTANCE.addJsBridge(jsBridge);
    }
}
