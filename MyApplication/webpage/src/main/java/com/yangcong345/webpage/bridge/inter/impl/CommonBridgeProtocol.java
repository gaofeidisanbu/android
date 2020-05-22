package com.yangcong345.webpage.bridge.inter.impl;

import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yangcong345.webpage.bridge.inter.BridgeProtocol;
import com.yangcong345.foundation.jsbridge.JsBridgeConstant;
import com.yangcong345.webpage.log.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaofei on 2018/2/22.
 */

public class CommonBridgeProtocol implements BridgeProtocol {

    public static final String PROTOCOL_STATUS = "ok";
    public static final String PROTOCOL_DATA = "data";
    public static final String PROTOCOL_ERROR_CODE = "errCode";
    public static final int PROTOCOL_ERROR_CALL_METHOD_EXIST = 404;
    public static final int ERROR_CODE_DEFAULT = -100;
    public static int uniqueId = 0;
    private static final String EXCEPTION_STATUS = "status is false";
    private static final String EXCEPTION_JSON = "json convert is exception";

    @Override
    public <T> T jsCallNativeRequestPro(JSONObject jsonObject) throws Exception {
        boolean status = jsonObject.getBoolean(PROTOCOL_STATUS);
        T t = null;
        if (status) {
            JSONObject jsonData = jsonObject.optJSONObject(PROTOCOL_DATA);
            Gson gson = new GsonBuilder().create();
            t = gson.fromJson(jsonData.toString(), (Class<T>) Object.class);
        } else {
            throw new Exception("status is false");
        }
        return t;
    }

    @Override
    public <R> JSONObject jsCallNativeResponsePro(boolean status, R data) throws JSONException {
        Map map = new HashMap();
        map.put(PROTOCOL_STATUS, status);
        map.put(PROTOCOL_DATA, data == null ? "" : data);
        Gson gson = new GsonBuilder().create();
        return new JSONObject(gson.toJson(map));
    }

    @Override
    public <T> JSONObject nativeCallJsRequestPro(T t) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put(PROTOCOL_STATUS, true);
        jsonObject.put(PROTOCOL_DATA, t);
        Gson gson = new GsonBuilder().create();
        return new JSONObject(gson.toJson(jsonObject));
    }

    @Override
    public boolean nativeCallJsResponseProStatus(JSONObject jsonObject) throws Exception {
        boolean status = jsonObject.optBoolean(PROTOCOL_STATUS);
        if (!status) {
            int errCode = jsonObject.optInt(PROTOCOL_ERROR_CODE);
            if (errCode == PROTOCOL_ERROR_CALL_METHOD_EXIST) {
                LogUtils.w(errCode);
            } else {
                throw new Exception(jsonObject.toString());
            }
        }
        return status;
    }

    @Override
    public Pair<Boolean, Integer> nativeCallJsResponseProPair(JSONObject jsonObject) {
        boolean status = jsonObject.optBoolean(PROTOCOL_STATUS);
        int errCode = ERROR_CODE_DEFAULT;
        if (!status) {
            errCode = jsonObject.optInt(PROTOCOL_ERROR_CODE);
        }
        return Pair.create(status, errCode);
    }


    @Override
    public <R> R nativeCallJsResponseProData(JSONObject jsonObject) {
        JSONObject jsonData = jsonObject.optJSONObject(PROTOCOL_DATA);
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonData.toString(), (Class<R>) Object.class);
    }

    @Override
    public String getUniqueId(boolean isResponse, String method) {
        if (isResponse) {
            return String.format("%s_%d", method, ++uniqueId);
        } else {
            return JsBridgeConstant.DISPATCH_RESPONSE_VALUE_NOT_RESPONSE;
        }

    }


}
