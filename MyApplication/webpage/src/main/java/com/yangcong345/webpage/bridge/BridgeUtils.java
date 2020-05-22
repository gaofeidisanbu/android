package com.yangcong345.webpage.bridge;

/**
 * Created by gaofei on 27/02/2018.
 */

public class BridgeUtils {

    /**
     *
     * @param value
     * @return
     */
    public static String backslashReplace(String value) {
        if (value != null && value.startsWith("\"")
                && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
            value = value.replace("\\\\\\\\", "#%#"); // TODO: killnono 17/8/22  fuck code
            value = value.replaceAll("\\\\", "");
            value = value.replace("#%#", "\\\\");
        }
        return value;
    }
}
