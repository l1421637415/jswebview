package com.jyh.webview.jsbridge;

import android.content.Context;

/**
 * Created by jiangyaohui on 2017/7/4.
 */

public interface JsBridge {

    void setContext(Context context);

    <T> void handleResponse (T obj,String callBackId);
}
