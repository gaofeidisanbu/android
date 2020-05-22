package com.yangcong345.webpage.bridge.inter.impl;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;

import com.yangcong345.foundation.jsbridge.JsBridge;
import com.yangcong345.foundation.jsbridge.JsBridgeMethod;
import com.yangcong345.webpage.IWebViewContainer;
import com.yangcong345.webpage.Response;
import com.yangcong345.webpage.YCAction;
import com.yangcong345.webpage.YCAction1;
import com.yangcong345.webpage.YCAction2;
import com.yangcong345.webpage.YCHandler;
import com.yangcong345.webpage.YCResponseCallback;
import com.yangcong345.webpage.YCResponseCallback2;
import com.yangcong345.webpage.YCResponseCallbackWrapper;
import com.yangcong345.webpage.bridge.inter.BridgeProtocol;
import com.yangcong345.webpage.bridge.inter.Request;
import com.yangcong345.webpage.bridge.inter.ServiceRequestWrapper;
import com.yangcong345.webpage.bridge.inter.WebViewService;
import com.yangcong345.webpage.log.LogUtils;
import com.yangcong345.webpage.module.H5ModulePackageManager;
import com.yangcong345.webpage.module.IH5Module;
import com.yangcong345.webpage.module.ModuleInfo;
import com.yangcong345.webpage.module.ModuleManager;

import org.json.JSONException;

import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by gaofei on 06/02/2018.
 */

public class WebViewServiceImpl implements WebViewService {

    private JsBridge mJSBridge;

    private BridgeProtocol mProtocol;

    private Handler mMainHandlerThread = new Handler(Looper.getMainLooper());

    private ModuleManager mModuleManager = new ModuleManager();


    public WebViewServiceImpl(JsBridge jsBridge, BridgeProtocol protocol) {
        this.mJSBridge = jsBridge;
        this.mProtocol = protocol;
    }

    @Override
    public void registerHandlerByName(String methodName, YCAction request) {
        checkRequest(methodName, request);
        registerHandlerInner(new ServiceRequestWrapper(methodName, request));
    }

    @Override
    public <T> void registerHandlerByName(String methodName, YCAction1<T> request) {
        checkRequest(methodName, request);
        registerHandlerInner(new ServiceRequestWrapper(methodName, request));
    }

    @Override
    public <R> void registerHandlerByName(String methodName, YCAction2<R> request) {
        checkRequest(methodName, request);
        registerHandlerInner(new ServiceRequestWrapper(methodName, request));
    }

    @Override
    public <T, R> void registerHandlerByName(String methodName, YCHandler<T, R> request) {
        checkRequest(methodName, request);
        registerHandlerInner(new ServiceRequestWrapper(methodName, request));
    }

    private final <T, R> void registerHandlerInner(ServiceRequestWrapper<T, R> serviceRequestWrapper) {
        String methodName = serviceRequestWrapper.getMethodName();
        JsBridgeMethod jsBridgeMethod = (context, params, callback) -> {
            mMainHandlerThread.post(() -> {
                String exceptionMsg = String.format("methodName%s-params%s", methodName, params.toString());
                try {
                    Request request = serviceRequestWrapper.getRequest();
                    if (request instanceof YCAction) {
                        ((YCAction) request).request();
                    } else if (request instanceof YCAction1) {
                        ((YCAction1) request).request(mProtocol.jsCallNativeRequestPro(params));

                    } else if (request instanceof YCAction2) {
                        ((YCAction2) request).request((status, responseData) -> {
                            try {
                                callback.call(mProtocol.jsCallNativeResponsePro(status, responseData));
                            } catch (JSONException e) {
                                LogUtils.e(exceptionMsg, e);
                            }
                        });
                    } else if (request instanceof YCHandler) {
                        ((YCHandler) request).request(mProtocol.jsCallNativeRequestPro(params), (status, responseData) -> {
                            try {
                                callback.call(mProtocol.jsCallNativeResponsePro(status, responseData));
                            } catch (JSONException e) {
                                LogUtils.e(exceptionMsg, e);
                            }
                        });

                    } else {
                        LogUtils.e(request.getClass().getSimpleName(), new Exception());
                    }
                } catch (JSONException e) {
                    LogUtils.e(exceptionMsg, e);
                } catch (Exception e) {
                    LogUtils.e(exceptionMsg, e);
                }
            });
        };

        mJSBridge.addJSBridgeMethod(methodName, jsBridgeMethod);
    }


    @Override
    public void callHandler(String methodName) {
        callHandler(methodName, null);
    }

    @Override
    public <T> void callHandler(String methodName, T t) {
        doCallHandler(methodName, t, new YCResponseCallbackWrapper());
    }

    @Override
    public <T, R> void callHandler(String methodName, T t, YCResponseCallback<R> responseCallBack) {
        doCallHandler(methodName, t, new YCResponseCallbackWrapper<>(responseCallBack));
    }

    public <T, R> void callHandler(String methodName, T t, YCResponseCallback2<R> responseCallBack) {
        doCallHandler(methodName, t, new YCResponseCallbackWrapper<>(responseCallBack));
    }

    private final <T, R> void doCallHandler(String methodName, T t, YCResponseCallbackWrapper responseCallbackWrapper) {
        String methodId = mProtocol.getUniqueId(responseCallbackWrapper != null, methodName);
        final String exceptionMsg = String.format("methodId = %s methodName = %s data = %s", methodId, methodName, t);
        try {
            mJSBridge.callJSBridgeMethod(methodId, methodName, mProtocol.nativeCallJsRequestPro(t), data -> {
                mMainHandlerThread.post(() -> {
                    Response callback = responseCallbackWrapper.getResponse();
                    if (callback instanceof YCResponseCallback) {
                        try {
                            boolean status = mProtocol.nativeCallJsResponseProStatus(data);
                            R response = null;
                            if (status) {
                                response = mProtocol.nativeCallJsResponseProData(data);
                            }
                            ((YCResponseCallback) callback).callback(status, response);
                        } catch (Exception e) {
                            LogUtils.e(exceptionMsg, e);
                        }
                    } else if (callback instanceof YCResponseCallback2) {
                        Pair<Boolean, Integer> pair = mProtocol.nativeCallJsResponseProPair(data);
                        R response = null;
                        if (pair.first) {
                            response = mProtocol.nativeCallJsResponseProData(data);
                        }
                        ((YCResponseCallback2) callback).callback(pair, response);
                    } else {
                        LogUtils.d(new Exception("response is null !"));
                    }

                });
            });
        } catch (JSONException e) {
            LogUtils.e(exceptionMsg, e);
        }
    }

    private final <T, R> void doCallHandler(String methodName, T t, YCResponseCallback<R> responseCallBack) {
        String methodId = mProtocol.getUniqueId(responseCallBack != null, methodName);
        final String exceptionMsg = String.format("methodId = %s methodName = %s data = %s", methodId, methodName, t);
        try {
            mJSBridge.callJSBridgeMethod(methodId, methodName, mProtocol.nativeCallJsRequestPro(t), data -> {
                mMainHandlerThread.post(() -> {
                    if (responseCallBack != null) {
                        try {
                            boolean status = mProtocol.nativeCallJsResponseProStatus(data);
                            R response = null;
                            if (status) {
                                response = mProtocol.nativeCallJsResponseProData(data);
                            }
                            responseCallBack.callback(status, response);
                        } catch (Exception e) {
                            LogUtils.e(exceptionMsg, e);
                        }
                    }
                });
            });
        } catch (JSONException e) {
            LogUtils.e(exceptionMsg, e);
        }
    }

    private void checkRequest(String methodName, Request request) {
        if (TextUtils.isEmpty(methodName)) {
            throw new IllegalArgumentException(String.format("%s is null", methodName));
        }
        if (request == null) {
            throw new IllegalArgumentException(String.format("%s's request is null", methodName));
        }
    }

    @Override
    public void removeHandler(String methodName) {
        mJSBridge.removeJSBridgeMethod(methodName);
    }

    @Override
    public void injectScript(String script) {
        mJSBridge.injectScript(script);
    }

    @Override
    public IH5Module installModule(Activity activity, WebViewService webView, ModuleInfo module, Map<String, Object> contextMap, IWebViewContainer iWebViewContainer) {
        Class clazz = module.getClazz();
        try {
            IH5Module h5Module = (IH5Module) clazz.newInstance();
            mModuleManager.addH5Module(h5Module);
            h5Module.initModule(activity, module.getModuleName(), webView, contextMap,iWebViewContainer);
           return h5Module;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public IH5Module installModule(Activity activity, WebViewService webView, String moduleName, Map<String, Object> contextMap,IWebViewContainer iWebViewContainer) {
        ModuleInfo module = H5ModulePackageManager.INSTANCE.getH5Module(moduleName);
        if (module != null) {
            Class clazz = module.getClazz();
            try {
                IH5Module h5Module = (IH5Module) clazz.newInstance();
                mModuleManager.addH5Module(h5Module);
                h5Module.initModule(activity, module.getModuleName(), webView, contextMap,iWebViewContainer);
                return h5Module;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void uninstallModule(IH5Module module) {
        Observable.fromIterable(module.getModuleHandlerList())
        .subscribe(handlerName -> removeHandler(handlerName));
    }


    @Override
    public void uninstallModule(String moduleName) {
        IH5Module ih5Module = mModuleManager.getH5Module(moduleName);
        if (ih5Module != null) {
            uninstallModule(ih5Module);
            mModuleManager.removeH5Module(ih5Module);
        }
    }
}
