package com.jyh.webview.jsbridge;

/**
 * Created by jiangyaohui on 2017/7/4.
 */

public interface JsBridge {

    <T> void handleResponse (T obj,String callBackId);
}
