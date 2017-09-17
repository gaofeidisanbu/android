package com.gaofei.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gaofei on 2017/7/7.
 */

public class TestMain {
    public static String BASE_H5_URL = "https://h5.yangcong345.com";
    public static final String TAG = "TestMain";
    public static String URL_TEACHER_GUIDE = BASE_H5_URL + "/teacherH5-login.html#/welcome?token=%s&deviceType=%s";
    public static String URL_FORGET_PSW = BASE_H5_URL + "/mobile_forget_password.html";

    public static void main(String[] args) {
        try {
//            rxjava();
//            json();
//            generic();
//            addPubParam(URL_TEACHER_GUIDE);
//            addPubParam(URL_FORGET_PSW);
//            System.out.println(convertTime(747034096));
            testUrl();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
            System.out.println(e);
        }
    }

    private static void testUrl() {
        try {
            String url = "http://10.8.8.8:5000/ladder/user/level?publisherId=1&semesterId=13&stageId=2&subjectId=1";
            System.out.println(URLEncoder.encode(url));;
            System.out.println(URLDecoder.decode(URLEncoder.encode(url)));
            System.out.println(URLDecoder.decode("http%3A%2F%2F10.8.8.8%3A5000%2Fladder%2Fuser%2Flevel%3FpublisherId%3D1%26semesterId%3D13%26stageId%3D2%26subjectId%3D1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String addPubParam(String url) {
        String queryParam = "";
        if (url.contains("?")) {
            queryParam = "&appVersion=" + "1.1";
        } else {
            queryParam = "?appVersion=" + "1.1";
        }
        url += queryParam;
        System.out.println(url);
        return url;
    }


    public static class B<T> {
        public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
            return null;
        }

        public final <R> Observable<R> flatMap1(Function<T, ? extends ObservableSource<? extends R>> mapper) {
            return null;
        }

        public final <R> Observable<R> flatMap2(Function<? extends T, ? extends ObservableSource<? extends R>> mapper) {
            return null;
        }
    }

    public static void fun() {
        String str = "{\"a\":1,\"data\":[1]}";
        Gson gson = new Gson();
        Object object = gson.fromJson(str, Object.class);
        if (object != null) {
            Map<String, Object> map = (Map) object;
            List list = (List) map.get("data");
            System.out.println(object.getClass().getName() + " value = " + object.toString() + " type = " + list.get(0).getClass().getName());
        } else {
            System.out.println("null");
        }
    }



    public static void fun3() {
//        System.out.println(isMethodTypeMatch(int.class,int.class));
//        System.out.println(isMethodTypeMatch(boolean.class,Boolean.TYPE));
//        System.out.println(isMethodTypeMatch(Map.class,Map.class));
//        System.out.println(isMethodTypeMatch(Map.class,HashMap.class));
//        System.out.println((Class<Integer>) Integer[].class.getComponentType());
//        for (int i = 0; i < 20; i++) {
//            isPercent(20);
//        }

//        System.out.println(convertTime(856321932));
    }

    private static String convertTime(long mill) {
        String str = "";
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = mill / dd;
        long hour = (mill - day * dd) / hh;
        long minute = (mill - day * dd - hour * hh) / mi;
        if (day > 0) {
            str = str + day + "天";
        }
        str = str + hour + "小时" + minute + "分";
        return str;
    }

    private static boolean isPercent(int percent) {
        boolean is = false;
        Random random = new Random();
        int temp = random.nextInt(100);
        if (temp < percent) {
            is = true;

        }
        System.out.println(temp);
        return is;
    }

    public static boolean isMethodTypeMatch(Class methodPara, Class inputPara) {
        if (methodPara == null && inputPara == null) {
            return true;
        }
        if (methodPara == null || inputPara == null) {
            return false;
        }

        if (methodPara.isPrimitive() && inputPara.isPrimitive()) {
            return methodPara == inputPara;
        }

        if (!methodPara.isPrimitive() && !inputPara.isPrimitive()) {
            return methodPara.isAssignableFrom(inputPara);
        }


        return false;
    }

    public static void rxjava() {
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        Observable.fromIterable(list).subscribe(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                System.out.println("fromIterable = " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println("subscribe ---->" + "  observable threadId= " + Thread.currentThread().getId());
                e.onNext("aaa");
                e.onNext("bbb");
                e.onNext("ccc");
                e.onNext("ddd");
                e.onNext("eee");
                e.onComplete();
            }
        }).skip(2).take(1).map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                return "map ---->" + s;
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread());
        System.out.println("  mainId = " + Thread.currentThread().getId());
        observable.subscribe(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                System.out.println("create1 ---->" + s + "  subscribe threadId = " + Thread.currentThread().getId());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        observable.subscribe(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                System.out.println("create2 ---->" + s + "  subscribe threadId = " + Thread.currentThread().getId());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        observable.subscribe(new Consumer() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                System.out.println("accept ---->" + o);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });

        Observable.create(new ObservableOnSubscribe<Map>() {
            @Override
            public void subscribe(ObservableEmitter<Map> e) throws Exception {
                e.onNext(new HashMap() {
                });
            }
        }).<Map>flatMap(new Function<Object, ObservableSource<HashMap>>() {
            @Override
            public ObservableSource<HashMap> apply(@NonNull Object o) throws Exception {
                return Observable.just(new HashMap());
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object s) throws Exception {
                System.out.println("accept flatMap---->" + s);
            }
        });

    }

    public static void json() {
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new HashMap<>();
        map.put("aa", true);
        map.put("bb", "\\cc");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("c", "aa");
        map.put("cc", map1);
        String str = gson.toJson(map).toString();
        System.out.println("before" + str);
        String messageJSON = str
                .replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"")
                .replaceAll("\'", "\\\\\'").replaceAll("\n", "\\\\\n")
                .replaceAll("\r", "\\\\\r").replaceAll("\f", "\\\\\f");
        System.out.println("after" + messageJSON);

        System.out.println("\\" + " " + "\""+ " " + "\\" + " "+ "\\\\");
        String sr  = "{\"data\":1,\"value\":\" \\\\\\\\ \"}";
        System.out.println(sr);
        str = sr.replaceAll("\\\\\\\\","\\\\");
        System.out.println(str);
    }

    public static class Mytest implements Iterable<Mytest> {
        public int size = 5;

        @Override
        public Iterator iterator() {
            return new MyIterator();
        }

        public class MyIterator implements Iterator {

            @Override
            public boolean hasNext() {
                if (size > 0) {
                    size--;
                    return true;
                }
                return false;
            }

            @Override
            public Object next() {
                return new Object();
            }
        }
    }

    public static class Food {
    }

    public static class Fruit extends Food {
    }

    public static class Apple extends Fruit {
    }

    public static class RedApple extends Apple {
    }

    public static void generic() {
//        A<C, C> a = new A<>();
//        TypeVariable[] types = a.getClass().getTypeParameters();
//        for (TypeVariable type : types) {
//            System.out.println(type);
//        }
//        Method[] methods = a.getClass().getDeclaredMethods();
//        for (Method method : methods) {
//            Type[] params = method.getGenericParameterTypes();
//            for (Type type : params)
//                System.out.println(type);
////        }
//       List list = new ArrayList<String>();
//        list.add(null);
//        getBridgeSupportType(new ArrayList<>());
        try {
            Constructor<A> c = A.class.getConstructor(Integer.class);
            System.out.println(c);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    static class A<C, M> {

        public A(String str) {

        }

        public A(Integer str) {

        }

        public C fun() {
            return (C) new String("");
        }
    }

//    static class C {
//        public void foo(Object a) {
//        }
//    }

    static class C<A> {
        public void foo(A a) {
            System.out.println(a.getClass());
        }
    }

    public static <T> boolean getBridgeSupportType(T obj) {
//        if (obj == null) {
//            return true;
//        }
//        System.out.println(obj.getClass());
//        if (obj instanceof Map || obj instanceof List || obj instanceof String || obj instanceof Boolean || obj instanceof Number) {
//            return true;
//        }
        return false;
    }



}