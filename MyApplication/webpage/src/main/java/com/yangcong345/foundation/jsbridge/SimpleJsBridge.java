package com.yangcong345.foundation.jsbridge;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xianchaohua
 */

public class SimpleJsBridge implements JsBridge {

    public static final String MSG_CAN_NOT_FIND_THIS_JS_BRIDGE_METHOD = "Can not find this JsBridgeMethod";
    public static final String RESPONSE_CALL_BACK_METHOD_NAME = "responseCallback";
    public static final String DISPATCH_METHOD_NAME = "dispatch";
    public static final String TAG = "JavaScriptBridge";
    private static final String JS_BRIDGE_NATIVE_OBJECT = "YCJSBridge";
    private static final String JS_BRIDGE_H5_OBJECT = "__YCBridge__";
    private static final String CALL_BACK_FORMAT = "%s.%s('%s')";
    private static final String ERROR_JS_BRIDGE_METHOD_HAS_BEEN_ADDED = "JSBridgeMethod has been added";

    private Map<String, JsBridgeMethod> mMethodMap;
    private WebViewProxy mWebViewProxy;
    private String mCallbackName;
    private String mJsInterfaceName;
    private Context mContext;
    private JsBridgeInterceptor mJsBridgeInterceptor;
    private JsBridgeJsCallInterceptor mJsBridgeJsCallInterceptor;
    private Map<String, JSBridgeResponseMethod> mResponseCallBacks;
    private List<JSBridgeResponseMethod> mCallJsCaches;


    SimpleJsBridge() {
        mMethodMap = new HashMap<>();
        mResponseCallBacks = new HashMap<>();
        mCallJsCaches = new ArrayList<>();
    }

    /**
     * 初始化Bridge
     *
     * @param context
     * @param proxy
     */
    @Override
    public void initBridge(Context context, WebViewProxy proxy) {
        mContext = context;
        mWebViewProxy = proxy;
        mWebViewProxy.addJavascriptInterface(new WebAppInterface(this), JS_BRIDGE_NATIVE_OBJECT);
    }

    /**
     * H5调用native方法
     *
     * @param protocol
     */
    public void evaluateJava(String protocol) {
        if (mJsBridgeInterceptor != null) {
            protocol = mJsBridgeInterceptor.input(protocol);
        }
        try {
            JSONObject jsonObject = new JSONObject(protocol);
            String methodName = jsonObject.optString(JsBridgeConstant.KEY_REGISTER_CALL_METHOD_NAME);
            String id = jsonObject.optString(JsBridgeConstant.KEY_REGISTER_CALL_ID);
            JSONObject params = jsonObject.optJSONObject(JsBridgeConstant.KEY_REGISTER_CALL_PARAMS);
            if (params == null) {
                params = new JSONObject();
            }
            executeJSBridgeMethod(protocol, id, methodName, params);
        } catch (JSONException exception) {
            InternalLogUtil.e(TAG, exception);
        }
    }

    /**
     * 执行已注册的JSBridgeMethod
     *
     * @param methodName
     * @param params
     */
    private void executeJSBridgeMethod(final String input, final String id, final String methodName, final JSONObject params) {
        final String exceptionMsg = String.format("response -> id = %s methodName = %s params = %s", id, methodName, params);
        JsBridgeMethod jsBridgeMethod = getJSBridgeMethod(methodName);
        if (jsBridgeMethod == null) {
            InternalLogUtil.e(TAG, new Exception(exceptionMsg));
            printAllRegisterMethods();
            return;
        }
        jsBridgeMethod.call(mContext, params, new JsBridgeCallback() {
            @Override
            public void call(JSONObject result) {
                try {
                    if (JsBridgeConstant.isResponseToJs(id, input)) {
                        String resultStr;
                        if (mJsBridgeInterceptor != null) {
                            resultStr = mJsBridgeInterceptor.output(input, result);
                        } else {
                            resultStr = result.toString();
                        }
                        String js = getCallbackJs(resultStr);
                        evaluateJavascript(js);
                    }
                } catch (JSONException e) {
                    InternalLogUtil.e(TAG, new Exception(exceptionMsg));
                }
            }
        });
    }

    /**
     * 调用H5的JavaScript方法
     *
     * @param js
     */
    private void evaluateJavascript(String js) {
        evaluateJavascript(js, null);
    }

    /**
     * 调用H5的JavaScript方法
     *
     * @param js
     * @param callback
     */
    private void evaluateJavascript(String js, JSExecuteCallback<String> callback) {
        mWebViewProxy.evaluateJavascript(js, callback);
    }


    /**
     * 格式化JavaScript方法
     *
     * @param result
     * @return
     */
    private String getCallbackJs(String result) {
        String jsCallbackName = TextUtils.isEmpty(mCallbackName) ? RESPONSE_CALL_BACK_METHOD_NAME : mCallbackName;
        return String.format(CALL_BACK_FORMAT, JS_BRIDGE_H5_OBJECT, jsCallbackName, result);
    }

    /**
     * 从Map中获取JavaScriptMethod
     *
     * @param method
     * @return
     */
    public JsBridgeMethod getJSBridgeMethod(String method) {
        return mMethodMap.get(method);
    }

    /**
     * 从Map中添加JavaScriptMethod
     *
     * @param methodName
     * @param jsBridgeMethod
     */
    @Override
    public void addJSBridgeMethod(String methodName, JsBridgeMethod jsBridgeMethod) {
        // 避免生命周期内，重复注册
        if (mMethodMap.containsKey(methodName)) {
            String exceptionMsg = String.format("%s methodName = %s", ERROR_JS_BRIDGE_METHOD_HAS_BEEN_ADDED, methodName);
//            if (BuildConfig.DEBUG) {
//                throw new RuntimeException(exceptionMsg);
//            } else {
//                LogUtils.e(methodName, new Exception(exceptionMsg));
//            }
        }
        mMethodMap.put(methodName, jsBridgeMethod);
    }

    /**
     * 从Map中删除JavaScriptMethod
     *
     * @param method
     */
    @Override
    public void removeJSBridgeMethod(String method) {
        mMethodMap.remove(method);
    }

    /**
     * 清空Map
     */
    @Override
    public void clearJSBridgeMethod() {
        mMethodMap.clear();
    }

    /**
     * 设置回调名字
     *
     * @param callbackName
     */
    @Override
    public void setJsCallbackName(String callbackName) {
        mCallbackName = callbackName;
    }

    /**
     * 设置JsInterfaceName, 用于H5 window.jsInterfaceName
     *
     * @param jsInterfaceName
     */
    @Override
    public void setJsInterfaceName(String jsInterfaceName) {
        mJsInterfaceName = jsInterfaceName;
        mWebViewProxy.removeJavascriptInterface(JS_BRIDGE_NATIVE_OBJECT);
        mWebViewProxy.addJavascriptInterface(new WebAppInterface(this), mJsInterfaceName);
    }

    /**
     * 设置拦截器，拦截输入输出的数据
     *
     * @param interceptor
     */
    @Override
    public void setJsBridgeRegisterInterceptor(JsBridgeInterceptor interceptor) {
        mJsBridgeInterceptor = interceptor;
    }

    @Override
    public void setJsBridgeJsCallInterceptor(JsBridgeJsCallInterceptor interceptor) {
        mJsBridgeJsCallInterceptor = interceptor;
    }

    /**
     * 调用已经注册的js方法
     *
     * @param params
     * @param responseCallback
     */
    @Override
    public void callJSBridgeMethod(String methodId, String methodName, JSONObject params, final JsBridgeResponseCallback responseCallback) {
        try {
            String inputStr = null;
            if (mJsBridgeJsCallInterceptor != null) {
                inputStr = mJsBridgeJsCallInterceptor.input(methodId, methodName, params);
            }
            String js = getDispatchJS(inputStr, methodId, methodName, params);
            JSBridgeResponseMethod jsBridgeResponseMethod = new JSBridgeResponseMethod();
            jsBridgeResponseMethod.mResponseCallBack = responseCallback;
            jsBridgeResponseMethod.methodId = methodId;
            jsBridgeResponseMethod.methodName = methodName;
            jsBridgeResponseMethod.params = params;
            if (!mWebViewProxy.isPageFinished()) {
                mCallJsCaches.add(jsBridgeResponseMethod);
            } else {
                mResponseCallBacks.put(methodId, jsBridgeResponseMethod);
                // h5实现问题，现在使用js调用javaScriptInterface 方法实现
                evaluateJavascript(js, null);
            }

        } catch (JSONException e) {
            InternalLogUtil.e(TAG, new Exception(String.format("call -> methodId = %s methodName = %s params = %s", methodId, methodName, params)));
        }

    }

    /**
     * @param js
     * @param responseCallback
     */
    private void callJSBridgeMethodWithCallback(String js, final JsBridgeResponseCallback responseCallback) {
        evaluateJavascript(js, new JSExecuteCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                // TODO: 02/03/2018  
            }
        });
    }


    public void evaluateResponseCallback(String value) {
        if (TextUtils.isEmpty(value)) {
            value = "";
        }
        JSONObject responseResult = null;
        try {
            if (mJsBridgeJsCallInterceptor != null) {
                responseResult = mJsBridgeJsCallInterceptor.output(value);
            } else {
                responseResult = new JSONObject(value);
            }
            String id = responseResult.optString(JsBridgeConstant.KEY_DISPATCH_RESPONSE_ID);
            JSONObject params = responseResult.optJSONObject(JsBridgeConstant.KEY_DISPATCH_RESPONSE_PARAMS);
            JSBridgeResponseMethod responseMethod = mResponseCallBacks.remove(id);
            if (responseMethod == null) {
                InternalLogUtil.e(TAG, new Exception(responseResult.toString()));
            } else {
                JsBridgeResponseCallback responseCallback = responseMethod.mResponseCallBack;
                if (responseCallback != null) {
                    responseCallback.call(params);
                }
            }
        } catch (JSONException e) {
            InternalLogUtil.e(TAG, new Exception(String.format("response ->   %s", responseResult != null ? responseResult.toString() : "")));
        }

    }


    @Override
    public void pageFinished() {
        if (mCallJsCaches.isEmpty()) {
            return;
        }
        for (JSBridgeResponseMethod jsBridgeResponseMethod : mCallJsCaches) {
            callJSBridgeMethod(jsBridgeResponseMethod.methodId, jsBridgeResponseMethod.methodName, jsBridgeResponseMethod.params, jsBridgeResponseMethod.mResponseCallBack);
            InternalLogUtil.d(TAG, String.format("pageFinished -> methodName = %s", jsBridgeResponseMethod.methodName));
        }
        mCallJsCaches.clear();
    }

    /**
     * 调用的js方法
     */
    private String getDispatchJS(String input, String methodId, String methodName, JSONObject params) throws JSONException {
        String jsParams;
        if (!TextUtils.isEmpty(input)) {
            jsParams = input;
        } else {
            JSONObject protocol = new JSONObject();
            protocol.put(JsBridgeConstant.KEY_DISPATCH_CALL_METHOD_NAME, methodId);
            protocol.put(JsBridgeConstant.KEY_DISPATCH_CALL_PARAMS, params);
            jsParams = protocol.toString();
        }
        return String.format(CALL_BACK_FORMAT, JS_BRIDGE_H5_OBJECT, DISPATCH_METHOD_NAME, jsParams);
    }


    private void printAllRegisterMethods() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, JsBridgeMethod> entry : mMethodMap.entrySet()) {
            sb.append(entry.getKey() + "\n");

        }
        InternalLogUtil.d(TAG, sb.toString());
    }

    @Override
    public void injectScript(String script) {
        mWebViewProxy.injectScript(script);
    }
}
