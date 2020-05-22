package com.yangcong345.webpage.page;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yangcong345.webpage.BaseBridgeWebViewV2Activity;
import com.yangcong345.webpage.R;
import com.yangcong345.webpage.YCAction1;
import com.yangcong345.webpage.YCAction2;
import com.yangcong345.webpage.YCHandler;
import com.yangcong345.webpage.YCResponseCallback;
import com.yangcong345.webpage.view.JsBridgeWebViewV2;
import com.yangcong345.webpage.view.YCLoadBridgeWebViewV2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gaofei
 * 该类仅用来演示如何使用h5 bridge，其中以下几点需要特别注意：
 * 1，该类仅用来演示，赞不能测试
 * 2，h5 bridge依赖h5的实现，该{@link mUrl}必须也已经实现h5增强，
 * 才能通过h5 bridge定义的接口方式相互调用
 * 3,h5和native如果建立bridge成功，可通过浏览器测试{@link com.yangcong345.foundation.jsbridge.SimpleJsBridge#JS_BRIDGE_NATIVE_OBJECT}
 * 和{@link com.yangcong345.foundation.jsbridge.SimpleJsBridge#JS_BRIDGE_H5_OBJECT}对象是否存在。
 * 4，h5的具体实现需要native和h5相互协商实现，具体实现方式参考{@link com.yangcong345.foundation.jsbridge.SimpleJsBridge}
 * 5，可通过关键字{@link com.yangcong345.foundation.jsbridge.SimpleJsBridge#TAG }查看所有和h5交互日志
 * 6，目前已经定义一些通用的注册方式实现方式,参考{@link com.yangcong345.main.phone.presentation.webpage.bridge.CommonH5RegisterFactory}的子类
 * 7，接口协议文档参考:https://shimo.im/doc/wqCD2QhWnLQSn6LZ
 * 9，Activity的基本实现，参考{@link BaseBridgeWebViewV2Activity}
 * 11，Dialog的基本实现，参考{@link BaseBridgeWebViewDialogFragment}
 * 12，错误排查，可以尝试以下流程
 * <ul>
 *     <li>
 *         通过关键字{@link com.yangcong345.foundation.jsbridge.SimpleJsBridge#TAG }过滤日志
 *     </li>
 *     <li>
 *         如果日志不存在，通过浏览器查看{@link com.yangcong345.foundation.jsbridge.SimpleJsBridge#JS_BRIDGE_NATIVE_OBJECT}
 *         和{@link com.yangcong345.foundation.jsbridge.SimpleJsBridge#JS_BRIDGE_H5_OBJECT}，第一个对象为native注入的，第二个为h5自己注入的
 *         该对象是否存在，如果不存在，h5 bridge没有建立 @和志强 @高飞
 *     </li>
 *     <li>
 *         日志存在，则查看日志详细信息，通过日志信息，基本都能定位问题
 *     </li>
 * </ul>
 *
 */

public class FooBridgeActivity extends Activity {
    private JsBridgeWebViewV2 mWebView;
    private String mUrl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foo_bridge);
        initView();
    }

    private void initView() {
        YCLoadBridgeWebViewV2 ycLoadBridgeWebView = findViewById(R.id.webView);
        mWebView = ycLoadBridgeWebView.getWebView();
        mWebView.loadUrl(mUrl);
        nativeCallJsMethod();
        jsCallNativeMethod();
    }

    /**
     * js调用native注册方法，其中有以下几点需要特别注意：
     * 1，方法只能在onCreate中注册，目前这种策略是位防止同名方法被覆盖，后续可考虑改进
     * 2，js调用native有四种方式，涉及参数和回调的四种情况
     * 3，其中{@link YCAction1},{@link YCHandler}接受js传递的参数使用map或json(理论支持'所有'类型)
     * ，这是Android，IOS和h5协商的默认协议
     * 4，{@link com.yangcong345.webpage.YCResponseCallback},代表响应到h5的结果，有两个参数，代表方法调用成功
     * 与否和返回的数据，第二个参数使用map或json，和第三条的解释一样
     */
    private void jsCallNativeMethod() {
        // 没有参数和回调
        mWebView.registerHandlerByName("methodName1", () -> {
            // 业务处理代码
        });
        // 处理h5传递过来的参数
        mWebView.registerHandlerByName("methodName2", (YCAction1<Map<String, Object>>) data -> {
            // data 业务处理代码
        });
        mWebView.registerHandlerByName("methodName3", (YCAction2<Map<String, Object>>) responseCallBack -> {
            //  业务处理代码
            // 处理回调结果
            responseCallBack.callback(true, new HashMap<>());
        });
        mWebView.registerHandlerByName("methodName4", (YCHandler<Map<String, Object>, Map<String, Object>>) (requestData, responseCallBack) -> {
            //  业务处理代码
            // 处理回调结果
            responseCallBack.callback(true, new HashMap<>());
        });
    }

    /**
     * native调用js注册方法，其中有以下几点需要特别注意：
     * 1，如果js bridge成功建立，如果h5中没有注册方法，会收到警告信息
     * 2， 方法参数类型和协议同{@link FooBridgeActivity#jsCallNativeMethod}
     */
    private void nativeCallJsMethod() {
        // 没有数据传递
        mWebView.callHandler("methodName1");
        // 有数据传递
        mWebView.callHandler("methodName2", new HashMap<>());
        // 有数据传递和回调处理
        mWebView.callHandler("methodName3", new HashMap<>(), (YCResponseCallback<Map<String, Object>>) (status, responseData) -> {
            // 获取回调结果，并处理
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 错误注册方式
        mWebView.registerHandlerByName("methodName4", (YCHandler<Map<String, Object>, Map<String, Object>>) (requestData, responseCallBack) -> {
            //  业务处理代码
            // 处理回调结果
            responseCallBack.callback(true, new HashMap<>());
        });
    }
}
