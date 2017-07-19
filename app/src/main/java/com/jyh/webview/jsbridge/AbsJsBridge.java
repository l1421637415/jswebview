package com.jyh.webview.jsbridge;


import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

/**
 * Created by jiangyaohui on 2017/7/13.
 */

public abstract class AbsJsBridge implements JsBridge{

    public  <T> void handleResponse (T obj,String callBackId){
        String result = JSON.toJSONString(obj);
        JsBridgeEngine.INSTANCE.handleResponse(result,callBackId);
    }

}
