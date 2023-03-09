package com.gaofei.app.act;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaofei3 on 2022/8/30
 * Describe:
 */
public class ExceptionTest {


    public void fun(String s) {
        try {
            fun2(s);
        } catch (Exception e) {
            RuntimeException exception = new RuntimeException("dddddddddddd");
            exception.fillInStackTrace().initCause(e);
            throw exception;
        }

    }

    public void fun3(String s) {
        try {
            fun2(s);
        } catch (Exception e) {
            RuntimeException exception = new RuntimeException("dddddddddddd");
            exception.initCause(e);
            throw exception;
        }
    }

    public void fun2(String s) throws JSONException {
        s.toString();
    }

    public void fun4(String s) {
        try {
            fun5(s);
        } catch (JSONException e) {
//                Log.e("ycmaaa", "caaaaaaaaaad", e);
            throw new IllegalArgumentException("cc", e);
        } catch (Exception e) {
//                Log.e("ycmaaa", "caaaaaaaaaad", e);
            throw new IllegalArgumentException("cc", e);
        }
    }


    public void fun5(String s) throws JSONException {
        new JSONObject(s);
    }
}
