package com.gaofei.app.act;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gaofei.app.MainActivity;
import com.gaofei.app.R;
import com.gaofei.app.act.adapter.BaseRecyclerAdapter;
import com.gaofei.app.act.holder.ViewHolderHandler;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;
import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.AndroidProcess;
import com.jaredrummler.android.processes.models.Stat;
import com.jaredrummler.android.processes.models.Statm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gaofei on 2017/9/11.
 */

public class ProcessAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(MainActivity.process );
        setContentView( R.layout.act_process);
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(linearLayoutManager);
        adapter.setOnBaseAdapterListener(new BaseRecyclerAdapter.OnBaseAdapterListener() {
            @Override
            public void click(View view, ViewHolderHandler.Item item) {
                throw new IllegalArgumentException();
            }
        });
        adapter.addList(getListData());
        recycler.addItemDecoration(new MainActivity.MyItemDecoration(this));
        recycler.setAdapter(adapter);
    }

    public List<ViewHolderHandler.Item> getListData() {
        List<ProcessInfo> list = getProgressInfo(this);
        List<ViewHolderHandler.Item> newList = new ArrayList<>(list.size());
        for (ProcessInfo processInfo : list) {
            ViewHolderHandler.Item item = new ViewHolderHandler.Item();
            item.itemType = ViewHolderHandler.ItemType.RECYCLER_PROCESS;
            item.object = processInfo;
            newList.add(item);
        }
        return newList;
    }


    public List<ProcessInfo> getProgressInfo(Context context) {
        getProgressInfoAndroidProcess(context);
        getProgressInfoAndroidRunningAppProcessInfo(context);
        return getProgressInfoAndroidAppProcess(this);
    }

    private List<ProcessInfo> getProgressInfoBeforeL(Context context) {
        List<ProcessInfo> list = new ArrayList<>();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
        if (runningAppProcessInfoList != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
                ProcessInfo progressInfo = new ProcessInfo();
                progressInfo.id = processInfo.processName;
                progressInfo.progressName = processInfo.processName;
                progressInfo.importance = processInfo.importance;
                progressInfo.pid = processInfo.pid;
                progressInfo.uid = processInfo.uid;
                list.add(progressInfo);
            }
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private List<ProcessInfo> getProgressInfoByUSM() {
        LogUtils.d("getProgressInfo2");
        List<ProcessInfo> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long endt = calendar.getTimeInMillis();//结束时间
        calendar.add(Calendar.DAY_OF_MONTH, -1);//时间间隔为一个月
        long statt = calendar.getTimeInMillis();//开始时间
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
        //获取一个月内的信息
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, statt, endt);
        for (UsageStats usageStats : queryUsageStats) {
            ProcessInfo progressInfo = new ProcessInfo();
            progressInfo.progressName = usageStats.getPackageName();
            progressInfo.id = usageStats.getPackageName();
            list.add(progressInfo);
        }

        return list;
    }

    private List<ProcessInfo> getProgressInfoAndroidProcess(Context context) {
        LogUtils.d("AndroidProcess ------------------------------------------------");
        List<ProcessInfo> list = new ArrayList<>();
        List<AndroidProcess> processes = AndroidProcesses.getRunningProcesses();
        for (AndroidProcess process : processes) {
            ProcessInfo progressInfo = new ProcessInfo();
            String processName = process.name;
            Stat stat = null;
            try {
                stat = process.stat();
                int pid = stat.getPid();
                int parentProcessId = stat.ppid();
                long startTime = stat.stime();
                int policy = stat.policy();
                char state = stat.state();

                Statm statm = process.statm();
                long totalSizeOfProcess = statm.getSize();
                long residentSetSize = statm.getResidentSetSize();

                LogUtils.d(process.pid + " " + process.name);
                progressInfo.id = processName;
                progressInfo.pid = pid;
                list.add(progressInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return list;
    }



    private List<ProcessInfo> getProgressInfoAndroidAppProcess(Context context) {
        LogUtils.d("AndroidAppProcess ------------------------------------------------");
        List<ProcessInfo> list = new ArrayList<>();
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
        for (AndroidAppProcess process : processes) {
            ProcessInfo progressInfo = new ProcessInfo();
            String processName = process.name;
            Stat stat = null;
            try {
                stat = process.stat();
                int pid = stat.getPid();
                int parentProcessId = stat.ppid();
                long startTime = stat.stime();
                int policy = stat.policy();
                char state = stat.state();

                Statm statm = process.statm();
                long totalSizeOfProcess = statm.getSize();
                long residentSetSize = statm.getResidentSetSize();

                try {
                    PackageInfo packageInfo = process.getPackageInfo(context, 0);
                    progressInfo.progressName = process.pid + "" + process.uid + process.foreground + process.getPackageName();
                    String appName = packageInfo.applicationInfo.loadLabel(
                            context.getPackageManager()).toString();
                    String packageName = packageInfo.packageName;
                    String versionName = packageInfo.versionName;
                    long lastUpdateTime = packageInfo.lastUpdateTime;
                    LogUtils.d(process.pid + " " + process.name + " " + appName + " " + packageName + " " + versionName + " " + lastUpdateTime);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                progressInfo.id = processName;
                progressInfo.pid = pid;
                list.add(progressInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return list;
    }

    private List<ProcessInfo> getProgressInfoAndroidRunningAppProcessInfo(Context context) {
        LogUtils.d("AndroidRunningAppProcessInfo ------------------------------------------------");
        List<ProcessInfo> list = new ArrayList<>();
        List<ActivityManager.RunningAppProcessInfo> processes = AndroidProcesses.getRunningAppProcessInfo(context);
        for (ActivityManager.RunningAppProcessInfo process : processes) {
            ProcessInfo progressInfo = new ProcessInfo();
            String processName = process.processName;
            Stat stat = null;
            LogUtils.d(process.pid + " " + process.processName);
        }

        return list;
    }

    public static class ProcessInfo {
        public String progressName;
        public String id;
        public int importance;
        public int pid;
        public int uid;
    }
}
