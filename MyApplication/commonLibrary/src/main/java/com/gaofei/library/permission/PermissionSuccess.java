package com.gaofei.library.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 每个class 可以又多个PermissionSuccess，但是每一个的requestCode必须不相同
 * Created by gaofei on 2017/9/18.
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionSuccess {
    int requestCode() default -1;
}
