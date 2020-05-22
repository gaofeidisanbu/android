package com.yangcong345.foundation.jsbridge;

import org.json.JSONObject;

/**
 * Created by gaofei on 28/02/2018.
 */

public class JsBridgeProtocolManager implements JsBridgeProtocol {
//    private JsBridgeProtocol mDefaultProtocol = new DefaultJsBridgeProtocol();
    private JsBridgeProtocol mCurrProtocol;


    public void setJsBridgeProtocol(JsBridgeProtocol builder) {
        this.mCurrProtocol = builder;
    }

    @Override
    public JSONObject jsCallNativeRequest(String protocol) {
        if (mCurrProtocol != null) {
             mCurrProtocol.jsCallNativeRequest(protocol);
        } else {
//            mDefaultProtocol.jsCallNativeRequest(protocol);
        }
        return new JSONObject();
    }

    @Override
    public String jsCallNativeRequestToRes(JSONObject input, JSONObject output) {
        return null;
    }

    @Override
    public String nativeCallJsRequest(JSONObject input) {
        return null;
    }

    @Override
    public String nativeCallJsRequestToRes(String protocol) {
        return null;
    }



}
