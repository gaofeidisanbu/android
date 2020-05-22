package com.yangcong345.webpage.bridge;

import android.text.TextUtils;
import android.util.Base64;

/**
 * Created by gaofei on 01/02/2018.
 */

public class Base64Util {

    private Base64Util() {

    }

    /**
     * base64 编码
     * @param str
     * @return
     */
    public static String encode(String str) {
        String encode = str;
        if (!TextUtils.isEmpty(str)) {
            encode = Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
        }
        return encode;
    }

    /**
     *  base64 解码
     * @param str
     * @return
     */
    public static String decode(String str) {
        String decode = str;
        if (!TextUtils.isEmpty(str)) {
            decode = new String(Base64.decode(str, Base64.NO_WRAP));
        }
        return decode;
    }
}
