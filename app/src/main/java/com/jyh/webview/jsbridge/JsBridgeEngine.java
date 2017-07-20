package com.jyh.webview.jsbridge;

import android.util.Log;
import android.webkit.WebView;

import com.jyh.webview.jsbridge.annotation.BridgeMethod;
import com.jyh.webview.jsbridge.annotation.BridgeModule;
import com.jyh.webview.jsbridge.annotation.BridgeParame;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyaohui on 2017/7/12.
 */
public enum  JsBridgeEngine {
    INSTANCE;
    private Map<String,JsBridge> bridgeModuleMap = new HashMap<>();
    private Map<String,Method> bridgeMethodMap = new HashMap<>();
    private WebView webView;

    public void bindWebView(WebView webView){
        this.webView = webView;
    }

    public void addJsBridge(JsBridge jsBridge){
        Class obj = jsBridge.getClass();
        if(obj.isAnnotationPresent(BridgeModule.class)){
            BridgeModule bridgeModule = (BridgeModule)obj.getAnnotation(BridgeModule.class);
            String module = bridgeModule.module();
            bridgeModuleMap.put(module,jsBridge);
            for(Method method : obj.getMethods()){
                if(method.isAnnotationPresent(BridgeMethod.class)){
                    BridgeMethod bridgeMethod = method.getAnnotation(BridgeMethod.class);
                    String funName = bridgeMethod.value();
                    bridgeMethodMap.put(funName,method);
                }
            }
        }else {
            Log.w("jsbridge","类"+obj.getName()+"没有添加@BridgeModule注解，无法使用jsbrige功能");
        }
    }

    public void handRequest(String bridgeUri){
        Request request = analyticUri(bridgeUri);
        if(request != null){
            JsBridge jsBridge = bridgeModuleMap.get(request.getModule());
            Method method = bridgeMethodMap.get(request.getMethod());
            try {
                List<String> pnamesName = getMethodParamterName(method);
                Object[] pms = new Object[pnamesName.size()];
                for(int i=0; i<pnamesName.size(); i++){
                    pms[i] = request.getParame(pnamesName.get(i));
                }
                method.invoke(jsBridge,pms);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private Request  analyticUri(String bridgeUri){
        if(bridgeUri.startsWith("jsbridge://jyh.jc/")){
            String uri = bridgeUri.replaceAll("jsbridge://jyh.jc/","");
            if(uri.contains("?")){
                String [] uriInfo = uri.split("\\?");
                String space = uriInfo[0];
                String[] childSpaces = space.split("/");
                if(childSpaces.length == 2){ //目前只支持二度空间
                    Request request = new Request();
                    request.setModule(childSpaces[0]);
                    request.setMethod(childSpaces[1]);
                    String[] parames = uriInfo[1].split("&");
                    for(String parame : parames){
                        String[] pv = parame.split("=");
                        if(pv.length == 2){
                            request.addParame(pv[0],pv[1]);
                        }
                    }
                    return request;
                }
            }
        }
        return null;
    }

    private List<String> getMethodParamterName(Method method){
        Annotation[][] annotations= method.getParameterAnnotations();
        List<String> pnames = new ArrayList<>();
        for(Annotation[] ans :annotations){
            if(ans.length>0 && ans[0] != null){
                pnames.add(((BridgeParame)ans[0]).value());
            }
        }
        return pnames;
//        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
//        String[] params = u.getParameterNames(method);
//        if(params==null){
//            return new ArrayList<>();
//        }else{
//
//            return Arrays.asList(params);
//        }
    }

    private class Request{
        private String module;
        private String method;
        private final Map<String,String> parames = new HashMap<>();

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Map<String, String> getParames() {
            return parames;
        }

        public void addParame(String key,String val){
            parames.put(key,val);
        }

        public String getParame(String key){
            return parames.get(key);
        }
    }

    public void handleResponse(String result,String callBackId) {
        webView.loadUrl("javascript:JYH.callBack('"+callBackId+"','"+result+"')");
    }

}
