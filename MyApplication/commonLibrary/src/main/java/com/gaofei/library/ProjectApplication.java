package com.gaofei.library;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.gaofei.library.base.IApplicationInterface;

/**
 * Created by gaofei on 2017/5/26.
 */

public class ProjectApplication implements IApplicationInterface {
    private static ProjectApplication mInstance = new ProjectApplication();
    private static Application mApplication;

    private ProjectApplication() {

    }

    public static ProjectApplication getInstance() {
        return mInstance;
    }



    @Override
    public void setApplication(Application application) {
        this.mApplication = application;
    }

    @Override
    public void onCreate() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(mApplication)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(mApplication))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(mApplication))
                        .build());
    }

    public static Application getContext(){
        return mApplication;
    }

    @Override
    public void onTerminate() {

    }
}
