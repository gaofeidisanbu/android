package com.gaofei.library.base;

import android.app.Application;

/**
 * Created by gaofei on 2017/9/1.
 */

public interface IApplicationInterface {
    void setApplication(Application application);

    void onCreate();

    void onTerminate();


}
