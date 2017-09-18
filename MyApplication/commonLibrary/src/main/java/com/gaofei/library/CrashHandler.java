package com.gaofei.library;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by gaofei on 2017/9/16.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String LOG_SAVE_FOLDER = "/GF";
    public static final String LOG_NAME_HEAD = "/exception";
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    public CrashHandler(Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler) {
        this.mDefaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // 这种方式写文件不太好，有可能丢失exception日志
        createLogs(e);
        mDefaultUncaughtExceptionHandler.uncaughtException(t, e);
    }

    /**
     * 创建日志文件
     *
     * @param throwable 要记录的异常
     * @return 执行结果
     */
    private void createLogs(Throwable throwable) {
        if (throwable == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= 23 && !checkPermission()) {
            Log.e("异常处理--保存日志", "保存失败 , Android 6.0+ 系统. 内存卡读写权限没有获取");
            return;
        }

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //如果内存卡或内置储存已经挂载

            //创建储存目录
            String savePath = Environment.getExternalStorageDirectory().getPath() + LOG_SAVE_FOLDER + LOG_NAME_HEAD;
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            if (file.canWrite()) {
                //如果目录可以写入

                FileWriter fileWriter = null;
                PrintWriter printWriter = null;

                //得到当前的日期与时间 , 精确到秒
                String exceptionTime = DateFormat.format("yyyy-MM-dd_hh:mm:ss", new Date()).toString();

                //创建日志文件对象
                file = new File(savePath + "/" + exceptionTime + ".log");
                try {
                    if (file.createNewFile()) {
                        //如果文件创建成功 , 则写入文件
                        fileWriter = new FileWriter(file, true);
                        printWriter = new PrintWriter(fileWriter);
                        printWriter.println("Date:" + exceptionTime + "\n");
                        printWriter.println("Exception Class Name: ");
                        printWriter.println(throwable.getStackTrace()[0].getClassName());
                        printWriter.println("");
                        printWriter.println("Exception Class Position: ");
                        printWriter.println("Line number: " + throwable.getStackTrace()[0].getLineNumber());
                        printWriter.println("");
                        printWriter.println("Exception Cause: ");
                        printWriter.println(throwable.getMessage());
                        printWriter.println("");
                        printWriter.println("-----------------------------------\nException Message: \n");
                        for (int i = 0; i < throwable.getStackTrace().length; i++) {
                            printWriter.println(throwable.getStackTrace()[i]);
                        }

                        Log.w("异常处理--保存日志", "日志保存成功");
                    } else {
                        Log.e("异常处理--保存日志", "保存失败 , 存在相同名称的日志文件");
                    }
                } catch (IOException e) {
                    Log.e("异常处理--保存日志", "保存失败 , 无法创建日志文件或写入流失败");
                } finally {
                    //清空与关闭用到的流
                    if (printWriter != null) {
                        printWriter.flush();
                        printWriter.close();
                    }
                    if (fileWriter != null) {
                        try {
                            fileWriter.flush();
                            fileWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            } else {
                //目录不可写入 , 操作失败
                Log.e("异常处理--保存日志", "保存失败 , 无法写入目录");
                file = null;
                return;
            }

        } else {
            Log.e("异常处理--保存日志", "保存失败 , 储存未挂载");
            return;
        }

    }

    /**
     * 检查内存卡读写权限 针对Android 6.0+
     *
     * @return 是否有权限
     */
    @TargetApi(23)
    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(ProjectApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

}
