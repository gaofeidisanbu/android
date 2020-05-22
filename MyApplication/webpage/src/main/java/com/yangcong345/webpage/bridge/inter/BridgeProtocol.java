package com.yangcong345.webpage.bridge.inter;

import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaofei on 06/02/2018.
 */

public interface BridgeProtocol {

    <T> T jsCallNativeRequestPro(JSONObject jsonObject) throws Exception;

    <R> JSONObject jsCallNativeResponsePro(boolean status, R data) throws JSONException;

    <T> JSONObject nativeCallJsRequestPro(T t) throws JSONException;

    boolean nativeCallJsResponseProStatus(JSONObject jsonObject) throws Exception;

    Pair<Boolean, Integer> nativeCallJsResponseProPair(JSONObject jsonObject);

    <R> R nativeCallJsResponseProData(JSONObject jsonObject);

    String getUniqueId(boolean isResponse, String method);
}
