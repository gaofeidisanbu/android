package com.yangcong345.webpage;

import android.util.Pair;

/**
 * Created by gaofei on 06/02/2018.
 */

public interface YCResponseCallback2<R> extends Response {

    void callback(Pair<Boolean, Integer> pair, R responseData);

}
