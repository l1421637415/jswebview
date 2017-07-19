package com.jyh.webview.jsbridge.common;

import android.content.Context;
import android.util.Log;

import com.jyh.webview.jsbridge.AbsJsBridge;
import com.jyh.webview.jsbridge.annotation.BridgeMethod;
import com.jyh.webview.jsbridge.annotation.BridgeModule;
import com.jyh.webview.jsbridge.annotation.BridgeParame;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangyaohui on 2017/7/17.
 */

@BridgeModule(module="hard_ware")
public class HardwareInfo extends AbsJsBridge{
    @Override
    public void setContext(Context context) {

    }

    @BridgeMethod(fun="sys_version")
    public void getSystemVersion(@BridgeParame("mesg") String mesg, @BridgeParame("callBackId")String id){
        Log.i(id+"####",mesg);
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("version","1.0");
        handleResponse(resultMap,id);
    }
}
