package com.example.mylibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.mylibrary.ProjectApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2014 Nono_Lilith All right reserved.
 * -------------------------------------------------------
 * <p/>
 * Description: CommonUtils
 * Author: Nono(陈凯)
 * CreateDate: 2014-2-26 下午2:41:30
 * version 1.0.0
 */
public class CommonUtils {
    //通用线程池，可以执行通用的异步操作
    public static ExecutorService GLOBAL_EXECUTOR = Executors.newCachedThreadPool();

    public static void doInBackground(Runnable runnable) {
        if (runnable != null) {
            GLOBAL_EXECUTOR.submit(runnable);
        }
    }

    @NonNull
    public static Pair<String, String[]> buildSqlInSegment(Object[] ids) {
        StringBuilder sql = new StringBuilder();
        List<String> args = new ArrayList<>();

        String item = " in (?";
        int length = ids.length;
        for (int i = 0; i < length && ids[i] != null; i++) {
            Object id = ids[i];
            sql.append(item);
            args.add(id.toString());

            item = ", ?";
        }
        sql.append(")");

        return new Pair<>(sql.toString(), args.toArray(new String[args.size()]));
    }

    public static void clearFragmentBackstack(FragmentManager fm) {
        if (fm == null) return;

        int count = fm.getBackStackEntryCount();
        if (count > 0) {
            FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(0);
            boolean b = fm.popBackStackImmediate(entry.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }

    public static boolean popBackStack(FragmentManager fm) {
        if (fm == null) return false;

        int count = fm.getBackStackEntryCount();
        count--;

        if (count <= 0) {
            return false;
        }

        fm.popBackStack();
        return true;
    }

    public static void changeWindowOrientation(Activity act, int orientation) {
        if (act == null) return;

        if (orientation < ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED || orientation > ActivityInfo.SCREEN_ORIENTATION_LOCKED) {
            return;
        }

        if (act.getRequestedOrientation() != orientation) {
            act.setRequestedOrientation(orientation);
        }
    }




    public static Map<String, Object> cursor2Map(Cursor cursor) {
        Map<String, Object> map = new HashMap<>();
        if (cursor != null && cursor.getColumnCount() > 0) {
            int count = cursor.getColumnCount();
            for (int i = 0; i < count; i++) {
                int type = cursor.getType(i);

                Object value = null;
                switch (type) {
                    case Cursor.FIELD_TYPE_STRING:
                        value = cursor.getString(i);
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        value = cursor.getInt(i);
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        value = cursor.getFloat(i);
                        break;
                    case Cursor.FIELD_TYPE_NULL:
                        LogUtils.d("未知的参数类型,type:" + type);
                        break;
                    case Cursor.FIELD_TYPE_BLOB:
                        LogUtils.d("未知的参数类型,type:" + type);
                        break;
                }
                if (value != null) {
                    String name = cursor.getColumnName(i);
                    map.put(name, value);
                }
            }
        }
        return map;
    }

    public static ContentValues map2ContentValues(Map<String, Object> map) {
        ContentValues values = new ContentValues();
        if (map != null && map.size() > 0) {
            Set<Map.Entry<String, Object>> set = map.entrySet();
            Iterator<Map.Entry<String, Object>> itr = set.iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Object> entry = itr.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value != null) {
                    if (value instanceof String) {
                        values.put(key, (String) value);
                    } else if (value instanceof Integer) {
                        values.put(key, (Integer) value);
                    } else if (value instanceof Float) {
                        values.put(key, (Float) value);
                    } else {
                        LogUtils.d("未知的参数类型,key:" + key + ",value:" + value);

                    }
                } else {
                    LogUtils.d("未知的参数类型,key:" + key + ",value:" + value);
                }
            }
        }
        return values;
    }

    public static boolean isMainAppProgress(Context context) {

        boolean appProgress = false;

        if (context != null) {
            try {
                int pid = android.os.Process.myPid();
                ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {

                    if (appProcess.pid == pid && TextUtils.equals(appProcess.processName, context.getPackageName())) {
                        appProgress = true;
                    }
                }
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }

        return appProgress;

    }

    public static void hideSoftInput(Activity act) {
        if (act != null) {
            InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(act.getWindow().getDecorView().getApplicationWindowToken(),
                        0);
            }
        }
    }

    @Deprecated
    public static void showServerErrorMsg() {
    }

    public static String B2GB(long num) {
        double result = num * 1.0 / (1024 * 1024 * 1024);
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(result);
    }

    public static String B2MB(long num) {
        double result = num * 1.0 / (1024 * 1024);
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(result);
    }

    public static JSONObject getJsonObjFromJson(String key, JSONObject obj) {
        JSONObject result = null;
        if (obj != null) {
            try {
                result = obj.getJSONObject(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return result;
    }


    public static JSONArray getJsonArrayFromJson(String key, JSONObject obj) {
        JSONArray result = null;
        if (obj != null) {
            try {
                result = obj.getJSONArray(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return result;
    }


    /**
     * 解析Jsonarr为list形式
     *
     * @param arr
     * @return
     */
    public static List<String> parseJsonArrayToList(JSONArray arr) {
        List<String> list = new ArrayList<String>();
        if (arr != null && arr.length() > 0) {
            for (int i = 0; i < arr.length(); i++) {
                try {
                    list.add(arr.getString(i));
                } catch (Exception e) {
                    LogUtils.e(e);
                }
            }
        }
        return list;
    }

    /**
     * 获取json中的指定int值，默认为0
     *
     * @param key
     * @param obj
     * @return
     */
    public static int getIntFromJsonObj(String key, JSONObject obj) {
        int i = 0;
        if (obj != null) {
            try {
                i = obj.getInt(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return i;
    }

    /**
     * 获取json中的指定int值，默认为0
     *
     * @param key
     * @param obj
     * @return
     */
    public static long getLongFromJsonObj(String key, JSONObject obj) {
        long i = 0;
        if (obj != null) {
            try {
                i = obj.getLong(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return i;
    }


    /**
     * 获取Json中的指定字符串，默认为空字符串
     *
     * @param key
     * @param obj
     * @return
     */
    public static String getStringFromJsonObj(String key, JSONObject obj) {
        String str = "";
        if (obj != null) {
            try {
                str = obj.getString(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return str;
    }

    public static boolean getBooleanFromJsonObj(String key, JSONObject obj) {
        boolean b = false;
        if (obj != null) {
            try {
                b = obj.getBoolean(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return b;
    }

    /**
     * >.<
     *
     * @param key
     * @param jsonArray
     * @param <T>
     * @return
     */
    public static <T> T get(String key, JSONArray jsonArray) {
        T t = null;
        if (jsonArray != null) {
            try {
                t = (T) jsonArray.get(1);
            } catch (JSONException e) {
                LogUtils.e(e);
            }
        }
        return t;
    }

    /**
     * 获取系统当前时间24HH:mm
     *
     * @return hh:mm
     */
    public static String getCurrentTimeHM() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(Calendar.getInstance().getTimeInMillis());
    }

    public static StringBuilder mFormatBuilder;
    public static Formatter mFormatter;

    public static String stringForTime(int timeMs) {
        if (mFormatBuilder == null) {
            mFormatBuilder = new StringBuilder();
        }
        if (mFormatter == null) {
            mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        }

        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * @param time time
     * @return xx
     * @since 1.0.0
     */
    public static String getHourMin(String time) {
        try {
            if (time.contains(" ")) {
                String[] strs = time.split(" ");
                if (strs.length > 1) {
                    String s = strs[1];
                    time = s.substring(0, 5);
                }
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }

        return time;
    }


    /**
     * @param s string
     * @return 判断字符串是否为空
     */
    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.equals("null");
    }

    /**
     * @param editText et
     * @return 判断EditText输入框内是否有值
     */
    public static boolean isEmpty(EditText editText) {
        return isEmpty(editText.getText().toString());
    }

    /**
     * 判断输入字符含不含特殊字
     *
     * @param str str
     * @return b
     */
    public static boolean IsContainSpecail(String str) {
        Pattern pattern = Pattern
                .compile("^[\u4e00-\u9fa5_a-zA-Z0-9,!？.，。！?]+$");
        Matcher matcher = pattern.matcher(str);
        // 当条件满足时，将返回true，否则返回false
        return matcher.matches();

    }


    /**
     * @param str 只允许中文
     * @return 通过返回TRUE   校验失败返回FALSE
     * @throws java.util.regex.PatternSyntaxException
     */
    public static boolean TrueNameFilter(String str) {
        boolean tf;
        try {
            if (isEmpty(str)) {
                return false;
            }
            String chinese = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";//仅中文
            //用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
            Pattern pattern = Pattern.compile(chinese);
            tf = pattern.matcher(str).matches();
        } catch (Exception e) {
            return false;
        }
        return tf;
    }

    /**
     * 手机号码验证
     *
     * @param phone p
     * @return b
     */
    public static boolean validateMoblie(String phone) {
        int l = phone.length();
        boolean rs = false;
        switch (l) {
            case 7:
                if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4}$", phone)) {
                    rs = true;
                }
                break;
            case 11:
                if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", phone)) {
                    rs = true;
                }
                break;
            default:
                rs = false;
                break;
        }
        return rs;
    }

    public static boolean matchingText(String expression, String text) {
        Pattern p = Pattern.compile(expression);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否符合符合姓名字符，只支持字母和中文
     * * @Author: Nono(陈凯)
     *
     * @param str s
     * @return true则表示不包涵了特殊字符，false表示包含特殊字符
     * @since 1.0.0
     */
    public static boolean validateIsVaildTrueName(String str) {
        String regEx = "^[\u4e00-\u9fa5a-zA-Z]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 文本全角化
     *
     * @param input i
     * @return string
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    /**
     * @param context c
     * @param pxValue 像素px
     * @return dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param context  c
     * @param dipValue dp
     * @return px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * @param context c
     * @param pxValue px
     * @return sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * @param context c
     * @param spValue sp
     * @return px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽高数据
     *
     * @param context c
     * @return point(x, y);
     */
    public static Point getDevicePoint(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    /**
     * 获取视频缩略图
     *
     * @param filePath 视频路径
     * @return bitmap
     */
    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath, new HashMap<String, String>());
            bitmap = retriever.getFrameAtTime();
        } catch (RuntimeException e) {
            LogUtils.e(e);
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                LogUtils.e(e);
            }
        }
        return bitmap;
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            LogUtils.e(e);
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                LogUtils.e(e);
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static double getDoubleFromMap(String key, Map<String, Object> map) {
        try {
            if (map != null && map.containsKey(key)) {
                Number n = (Number) map.get(key);
                return n.doubleValue();
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }

        return 0;
    }


    /**
     * 获取Map中的指定字符串，默认为空字符串
     *
     * @param key
     * @param map
     * @return
     */
    public static String getStringFromMap(String key, Map<String, Object> map) {
        String str = "";
        if (map != null && map.containsKey(key)) {
            try {
                str = (String) map.get(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return str;
    }

    /**
     * @param key
     * @param map
     * @return
     */
    public static boolean getBooleanFromMap(String key, Map<String, Object> map) {
        boolean b = false;
        if (map != null && map.containsKey(key)) {
            try {
                b = (boolean) map.get(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return b;
    }

    /**
     * @param key
     * @param map
     * @return
     */
    public static int getIntFromMap(String key, Map<String, Object> map) {
        try {
            if (map != null && map.containsKey(key)) {
                Number n = (Number) map.get(key);
                return n.intValue();
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return 0;
    }

    /**
     * @param key
     * @param map
     * @return
     */
    public static long getLongFromMap(String key, Map<String, Object> map) {
        try {
            if (map != null && map.containsKey(key)) {
                Number n = (Number) map.get(key);
                return n.longValue();
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }

        return 0;
    }

    /**
     * @param key
     * @param map
     * @return
     */
    public static Map<String, Object> getMapFromMap(String key, Map<String, Object> map) {
        Map<String, Object> rMap = new HashMap();
        if (map != null && map.containsKey(key)) {
            try {
                rMap = (map.get(key) != null ? (Map<String, Object>) map.get(key) : rMap);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return rMap;
    }

    /**
     * @param key
     * @param map
     * @return
     */
    public static List<Map<String, Object>> getMapListFromMap(String key, Map<String, Object> map) {
        List<Map<String, Object>> rList = new ArrayList<>();
        if (map != null && map.containsKey(key)) {
            try {
                rList = map.get(key) != null ? (List<Map<String, Object>>) map.get(key) : rList;
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return rList;
    }

    public static List<String> getStringListFromMap(String key, Map<String, Object> map) {
        List<String> rList = new ArrayList<>();
        if (map != null && map.containsKey(key)) {
            try {
                rList = (List<String>) map.get(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return rList;
    }

    public static <T> List<T> getListFromMap(String key, Map<String, Object> map) {
        List<T> rList = new ArrayList<>();
        if (map != null && map.containsKey(key)) {
            try {
                rList = (List<T>) map.get(key);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return rList;
    }








    public static Spannable getSpanText(String str, int color, int start, int end) {
        Spannable spannable = new SpannableString(str);
        spannable.setSpan(new ForegroundColorSpan(GFResourceManager.getColor(color)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 获取spannable文本
     *
     * @param str
     * @param color  R.color.yc_blue5_primary
     * @param target
     * @return
     */
    public static Spannable getSpanText(String str, @ColorRes int color, String... target) {
        Spannable spannable = new SpannableString(str);

        for (String label : target) {
            int start = str.indexOf(label);
            int end = start + label.length();
            if (start < 0) continue;

            spannable.setSpan(new ForegroundColorSpan(GFResourceManager.getColor(color)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return spannable;
    }

    public static Spannable getSpanText(int res, int color, String... target) {
        String str = GFResourceManager.getString(res);
        return getSpanText(str, color, target);
    }

    /**
     * 获取spannable文本
     *
     * @param str
     * @return
     */
    public static Spannable getSpanText(String str) {
        return new SpannableString(str);
    }

    public static Spannable getSpanTextOfPattern(String desc, String highlightPattern, @ColorRes int highlight) {
        Pattern pattern = Pattern.compile(highlightPattern);
        Matcher matcher = pattern.matcher(desc);
        List<String> matchContents = new ArrayList<>();
        while (matcher.find()) {
            matchContents.add(matcher.group());
        }

        return CommonUtils.getSpanText(desc, highlight, matchContents.toArray(new String[0]));
    }

    public static Spannable getImageSpanText(String str, int drawable) {
        str += "h";
        Drawable d = GFResourceManager.getDrawable(drawable);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        Spannable spannable = new SpannableString(str);
        spannable.setSpan(new ImageSpan(d, ImageSpan.ALIGN_BASELINE), str.length() - 1, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    @Deprecated
    public static <T> void waitForNetResponse() {
    }

    @Deprecated
    public static <T> void notifyNetResponse() {
    }

    /**
     * 转换圆角drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable getRoundedDrawable(Bitmap bitmap) {
        return getRoundedDrawable(bitmap, bitmap.getWidth() * 0.5f);
    }

    public static Drawable getRoundedDrawable(Bitmap bitmap, float cornerRadius) {
        RoundedBitmapDrawable rd = RoundedBitmapDrawableFactory.create(GFResourceManager.getResource(), bitmap);
        rd.setCornerRadius(cornerRadius);
        return rd;
    }

    public static Drawable getRoundedDrawable(int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(GFResourceManager.getResource(), resId);
        return getRoundedDrawable(bitmap, CommonUtils.dip2px(ProjectApplication.getContext(), 3));
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private static SimpleDateFormat sJsonTimeFormatter;

    static {
        sJsonTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        sJsonTimeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static Date getDateFromJsonTime(String jsonTime) {
        Date date = new Date();
        try {
            date = sJsonTimeFormatter.parse(jsonTime);
        } catch (Exception e) {
            LogUtils.e(e);
        }

        return date;
    }

    /**
     * Compares two version strings.
     * <p/>
     * Use this instead of String.compareTo() for a non-lexicographical
     * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
     *
     * @param str1 a string of ordinal numbers separated by decimal points.
     * @param str2 a string of ordinal numbers separated by decimal points.
     * @return The result is a negative integer if str1 is _numerically_ less than str2.
     * The result is a positive integer if str1 is _numerically_ greater than str2.
     * The result is zero if the strings are _numerically_ equal.
     * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
     */
    public static int versionCompare(String str1, String str2) {
        String[] vals1 = str1.split("\\.");
        String[] vals2 = str2.split("\\.");
        int i = 0;
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }
        // compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(vals1.length - vals2.length);
    }

    public static String getJsonStringFromAsset(String file) {
        try {
            InputStream is = ProjectApplication.getContext().getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            LogUtils.e(e);
            return null;
        }
    }

    public static class CourseData {
        public String subject;
        public String chapterId;
        public String themeId;
        public String topicId;
    }

    private static CourseData sLastCourseData = new CourseData();

    public static CourseData getLastCourseData() {
        return sLastCourseData;
    }


    public static void setBackgroundResource(@NonNull View view, @DrawableRes int resId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int paddingTop = view.getPaddingTop();
            int paddingLeft = view.getPaddingLeft();
            int paddingRight = view.getPaddingRight();
            int paddingBottom = view.getPaddingBottom();
            view.setBackgroundResource(resId);
            view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        } else {
            view.setBackgroundResource(resId);
        }
    }

    public static boolean isXlarge(Context context) {
        int sw = context.getApplicationContext().getResources().getConfiguration().smallestScreenWidthDp;
        return sw >= 720;
    }

    public static boolean isCameraUsable() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5。MX5通过Camera.open()拿到的Camera对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

}
