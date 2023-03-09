package com.gaofei.library;

import android.app.Activity;
import android.app.Application;
import android.os.Process;
import android.text.TextUtils;

import com.facebook.stetho.Stetho;
import com.gaofei.library.base.IApplicationInterface;
import com.gaofei.library.utils.CommonUtils;
import com.gaofei.library.utils.LogUtils;


import java.util.LinkedList;
import java.util.List;


/**
 * Created by gaofei on 2017/5/26.
 */

public class ProjectApplication implements IApplicationInterface {
    private static ProjectApplication mInstance = new ProjectApplication();
    private static Application mApplication;


    public static ProjectApplication getInstance() {
        return mInstance;
    }

    @Override
    public void setApplication(Application application) {
        this.mApplication = application;
    }

    @Override
    public void onCreate() {
        if(CommonUtils.isMainAppProgress(getContext())){
            LogUtils.d(" main pid "+Process.myPid());
        }else {
            LogUtils.d(" no main pid "+Process.myPid());
        }
        Stetho.initialize(
                Stetho.newInitializerBuilder(mApplication)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(mApplication))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(mApplication))
                        .build());
        Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        LogUtils.d(exceptionHandler.getClass().getName());
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(exceptionHandler));
//        if (LeakCanary.isInAnalyzerProcess(mApplication)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(mApplication);


    }

    public static Application getContext() {
        return mApplication;
    }

    @Override
    public void onTerminate() {

    }

    private final List<ActivityInfo> mStack = new LinkedList<>();

    public static class ActivityInfo {
        public String id;
        public String activityStr;
        public Activity activity;
        public List<ActivityState> states;

    }

    public enum ActivityState {
        onCreate(), onRestoreInstanceState(), onRestart(), onStart, onResume, onNewIntent, onPause, onSaveInstanceState, onStop, onDestroy, finalize;

        ActivityState() {

        }
    }


    private ProjectApplication() {

    }

    public void ActivityOnCreate(Activity activity) {
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.activity = activity;
        activityInfo.id = activity.getClass().getName();
        activityInfo.activityStr = activity.getClass().getSimpleName();
        List<ActivityState> states = new LinkedList<>();
        states.add(ActivityState.onCreate);
        activityInfo.states = states;
        mStack.add(activityInfo);
    }

    public void ActivityOnRestoreInstanceState(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.states.add(ActivityState.onRestoreInstanceState);
            }
        }
    }

    public void ActivityOnRestart(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.states.add(ActivityState.onRestart);
            }
        }
    }

    public void ActivityOnStart(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.states.add(ActivityState.onStart);
            }
        }
    }

    public void ActivityOnResume(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.states.add(ActivityState.onResume);
            }
        }
    }

    public void ActivityOnNewIntent(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.states.add(ActivityState.onNewIntent);
            }
        }
    }

    public void ActivityOnPause(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.states.add(ActivityState.onPause);
            }
        }
    }

    public void ActivityOnSaveInstanceState(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.states.add(ActivityState.onSaveInstanceState);
            }
        }
    }

    public void ActivityOnStop(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.states.add(ActivityState.onStop);
            }
        }
    }

    public void ActivityOnDestroy(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.activity = null;
                activityInfo.states.add(ActivityState.onDestroy);
            }
        }
    }

    public void ActivityFinalize(Activity activity) {
        checkActivityExist(activity);
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                activityInfo.states.add(ActivityState.finalize);
            }
        }
    }

    private void checkActivityExist(Activity activity) {
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getClass().getName(), activityInfo.id)) {
                return;
            }
        }
        throw new IllegalArgumentException("当前activity在Activity不存在 " + activity.getClass().getName());
    }

    public void printActivityStateInfo() {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (ActivityInfo activityInfo : mStack) {
            if (index != 0) {
                sb.append("\n");
            }
            sb.append(activityInfo.activity);
            sb.append("\n");
            sb.append("    " + activityInfo.id);
            sb.append("\n");
            int count = 0;
            for (ActivityState state : activityInfo.states) {
                if (count != 0) {
                    sb.append("|");
                }else {
                    sb.append("         ");
                }
                sb.append(state.toString());
                count++;
            }
            index++;
        }
        LogUtils.d(sb.toString());
    }

    public ActivityInfo getActivityInfo(Class activity){
        for (ActivityInfo activityInfo : mStack) {
            if (TextUtils.equals(activity.getName(), activityInfo.id)) {
                return activityInfo;
            }
        }
        return null;
    }

}
