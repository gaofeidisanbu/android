package com.gaofei.library.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by gaofei on 2017/7/12.
 */

public class FileUtils {
    public static String PATH_LOGCAT_DIR;
    public static String PATH_LOGCAT_FILE;

    public static void save(Context context, String data) {
        FileOutputStream out = null;
        PrintStream ps = null;
        data += "/n";
        try {
            out = new FileOutputStream(getLogFile(context), true);
            out.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    ps.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static File getLogFile(Context context) {
        File file = createLogDir(context);
        File newFile = new File(PATH_LOGCAT_DIR + File.separator + "log.txt");
        if (!file.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return newFile;
    }

    private static File createLogDir(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            PATH_LOGCAT_DIR = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "GF";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            PATH_LOGCAT_DIR = context.getFilesDir().getAbsolutePath()
                    + File.separator + "GF";
        }
        File file = new File(PATH_LOGCAT_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    private static File createLogFile(Context context) {
        File file = new File(PATH_LOGCAT_DIR + File.separator + "log.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}
