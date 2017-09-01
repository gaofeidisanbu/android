package com.example.mylibrary.base;

import android.app.Application;

import com.example.mylibrary.ProjectApplication;

/**
 * Created by gaofei on 2017/9/1.
 */

public class ProjectProxy implements IApplicationInterface {
    private final static ProjectProxy mInstance = new ProjectProxy();

    private ProjectProxy() {

    }

    public static ProjectProxy getInstance() {
        return mInstance;
    }

    @Override
    public void setApplication(Application application){
        ProjectApplication.getInstance().setApplication(application);
    }

    @Override
    public void onCreate() {
        ProjectApplication.getInstance().onCreate();
    }

    @Override
    public void onTerminate() {
        ProjectApplication.getInstance().onTerminate();
    }
}
