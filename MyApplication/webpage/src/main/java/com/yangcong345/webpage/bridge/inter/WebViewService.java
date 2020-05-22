package com.yangcong345.webpage.bridge.inter;


import android.app.Activity;

import com.yangcong345.webpage.IWebViewContainer;
import com.yangcong345.webpage.YCAction;
import com.yangcong345.webpage.YCAction1;
import com.yangcong345.webpage.YCAction2;
import com.yangcong345.webpage.YCHandler;
import com.yangcong345.webpage.YCResponseCallback;
import com.yangcong345.webpage.YCResponseCallback2;
import com.yangcong345.webpage.module.IH5Module;
import com.yangcong345.webpage.module.ModuleInfo;

import java.util.Map;

/**
 * Created by gaofei on 06/02/2018.
 */

public interface WebViewService {
    /**
     * @param methodName 注册方法名
     * @param request
     */
    void registerHandlerByName(String methodName, YCAction request);

    /**
     * @param methodName 注册方法名
     * @param request    业务处理逻辑
     * @param <T>        请求数据类型
     */
    <T> void registerHandlerByName(String methodName, YCAction1<T> request);

    /**
     * @param methodName 注册方法名
     * @param request    业务处理逻辑
     * @param <R>响应数据类型
     */
    <R> void registerHandlerByName(String methodName, YCAction2<R> request);

    /**
     * @param methodName 注册方法名
     * @param request    业务处理代码
     * @param <T>请求数据类型
     * @param <R>响应数据类型
     */
    <T, R> void registerHandlerByName(String methodName, YCHandler<T, R> request);

    /**
     * @param methodName 调用js方法名称
     */
    void callHandler(String methodName);

    /**
     * @param methodName 调用js方法名称
     * @param t          js传递的数据
     * @param <T>        js传递的数据类型
     */
    <T> void callHandler(String methodName, T t);

    /**
     * @param methodName       调用js方法名称
     * @param t                js传递的数据
     * @param responseCallBack 回调函数
     * @param <T>              js传递的数据类型
     * @param <R>响应js的数据类型
     */
    <T, R> void callHandler(String methodName, T t, YCResponseCallback<R> responseCallBack);

    <T, R> void callHandler(String methodName, T t, YCResponseCallback2<R> responseCallBack);

    void injectScript(String script);

    IH5Module installModule(Activity activity, WebViewService webView, ModuleInfo module, Map<String, Object> contextMap, IWebViewContainer iWebViewContainer);

    IH5Module installModule(Activity activity, WebViewService webView, String moduleName, Map<String, Object> contextMap,IWebViewContainer iWebViewContainer);

    void uninstallModule(IH5Module module);


    void uninstallModule(String moduleName);

    void removeHandler(String methodName);


}
