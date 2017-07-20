package com.jyh.webview.jsbridge.common;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.jyh.webview.jsbridge.AbsJsBridge;
import com.jyh.webview.jsbridge.annotation.BridgeMethod;
import com.jyh.webview.jsbridge.annotation.BridgeModule;
import com.jyh.webview.jsbridge.annotation.BridgeParame;
import com.jyh.webview.jsbridge.common.util.NetWorkUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangyaohui on 2017/7/17.
 */

@BridgeModule(module="hard_ware")
public class HardwareInfo extends AbsJsBridge{
    private Context context;

    public HardwareInfo(Context context){
        this.context = context;
    }

    @BridgeMethod("sys_version")
    public void getSystemVersion(@BridgeParame("mesg") String mesg, @BridgeParame("callBackId")String id){
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("version","1.0");
        handleResponse(resultMap,id);
    }

    /**
     * 输出Android TelephonyManager对象信息
     * @param id
     */
    @BridgeMethod("telephony_info")
    public void getTelephonyInfo(@BridgeParame("callBackId")String id){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        handleResponse(tm,id);
    }

    /**
     * 获取当前设备屏幕参数
     */
    @BridgeMethod("screen_info")
    public void getScreenInfo(@BridgeParame("callBackId")String id){
        DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
        Map<String,String> map = new HashMap<>();
        map.put("width",mDisplayMetrics.widthPixels+"");
        map.put("height",mDisplayMetrics.heightPixels+"");
        map.put("density",mDisplayMetrics.density+"");
        map.put("densityDpi",mDisplayMetrics.densityDpi+"");
        handleResponse(map,id);
    }

    /**
     * 获取当前设备网络信息 目前有
     * mac 和网络类型
     */
    @BridgeMethod("net_info")
    public void getNetInfo(@BridgeParame("callBackId")String id){
        Map<String,String> map = new HashMap<>();
        map.put("mac", NetWorkUtil.getMacAddress(context));
        map.put("net_type",NetWorkUtil.getNetworkType(context));
        handleResponse(map,id);
    }
}
