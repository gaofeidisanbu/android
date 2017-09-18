package com.gaofei.library.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei on 2017/9/18.
 */

public class PermissionHandler {
    // shouldShowRequestPermissionRationale 没加
    public static void requestPermission(Object object, String[] permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> denieds = findDeniedPermission(object, permission);
            if (denieds != null && denieds.size() > 0) {
                if (object instanceof Activity) {
                    ((Activity) object).requestPermissions(denieds.toArray(new String[denieds.size()]), requestCode);
                } else if (object instanceof Fragment) {
                    ((Fragment) object).requestPermissions(denieds.toArray(new String[denieds.size()]), requestCode);
                } else {
                    new IllegalArgumentException();
                }
            } else {
                doExcuteSuccess(object, requestCode);
            }
        } else {
            doExcuteSuccess(object, requestCode);
        }
    }

    public static List<String> findDeniedPermission(Object object, String[] permissions) {
        List<String> list = new ArrayList<>();
        Context context = null;
        if (object instanceof Activity) {
            context = (Context) object;
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getContext();
        }
        if (context != null) {
            for (String permission : permissions) {
                int permissionResult = ContextCompat.checkSelfPermission(context, permission);
                if (permissionResult == PackageManager.PERMISSION_DENIED) {
                    list.add(permission);
                }
            }
        }
        return list;
    }


    private static void doExcuteSuccess(Object object, int requestCode) {
        Method method = findMethodWithRequestCode(object.getClass(), PermissionSuccess.class, requestCode);
        if (method != null) {
            try {
                method.invoke(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    private static void doExcuteFail(Object object, int requestCode) {
        Method method = findMethodWithRequestCode(object.getClass(), PermissionFail.class, requestCode);
        if (method != null) {
            try {
                method.invoke(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    // 其实request
    public static <A extends Annotation> Method findMethodWithRequestCode(Class clazz, Class<A> annotation, int requestCode) {
        for (Method method : clazz.getDeclaredMethods()) {
            A permissionSuccess = method.getAnnotation(annotation);
            if (permissionSuccess != null) {
                if (isEqualRequestCodeFromAnntation(permissionSuccess, requestCode)) {
                    return method;
                }
            }

        }
        return null;
    }

    private static boolean isEqualRequestCodeFromAnntation(Annotation a, int requestCode) {
        // 可能有问题
        int code = -2;
        if (a instanceof PermissionSuccess) {
            code = ((PermissionSuccess) a).requestCode();
        } else if (a instanceof PermissionFail) {
            code = ((PermissionFail) a).requestCode();
        }
        if (code == requestCode) {
            return true;
        }
        return false;
    }

    public static void onRequestPermissionsResult(Object object, int requestCode, String[] permissions, int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[0];
            int grantResult = grantResults[0];
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                doExcuteSuccess(object, requestCode);
            } else {
                doExcuteFail(object, requestCode);
            }
        }
    }
}
