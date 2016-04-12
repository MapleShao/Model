package com.maple.model;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;


import java.io.File;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;

public class AudioApp extends Application {
    private static AudioApp app;
    private static Handler sHandler = new Handler();

    public static String rootPath = "/Audio/";
    public static String lrcPath = "/Audio/lrc";

    /**
     * 服务器连接地址
     */
    public final static String BASE_URL = "http://192.168.0.158:8080/wansi";
    /**
     * 服务器解析根地址
     */
    public final static String HOME_CATEGORIES = BASE_URL + "/home.json";
    // 正式服务，测试服务器
    public static final String PHOTOS_URL = BASE_URL + "/photos/photos_1.json";

    @Override
    public void onCreate() {
        app = this;
        super.onCreate();
        Log.e("AudioApp", "app创建");

        // 初始化 存储路径
        initPath();
        // 设置异常的处理类
        // Thread.currentThread().setUncaughtExceptionHandler(
        // new MyUncaughtExceptionHandler());
    }

    /**
     * 初始化存储路径
     */
    private void initPath() {
        String ROOT = "";
        // Log.e("", "实际SD卡" + rootPath);
        //
        // String sdpath = Environment.getExternalStorageDirectory().toString();
        // Log.e("", "系统方法：" + sdpath);
        //
        // String data = Environment.getDataDirectory().toString();
        // Log.e("", "data:" + data);
        // String down = Environment.getDownloadCacheDirectory().toString();
        // Log.e("", "cache:" + down);
        // String state = Environment.getExternalStorageState();
        // Log.e("", "state:" + state);
        // String root = Environment.getRootDirectory().getPath();
        // Log.e("", "root:" + root);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ROOT = Environment.getExternalStorageDirectory().getPath();
        //  ROOT = StorageUtils.getSdcardPath(this);
        }
        rootPath = ROOT + rootPath;
        lrcPath = ROOT + lrcPath;

        File lrcFile = new File(lrcPath);
        if (lrcFile.exists()) {
            lrcFile.mkdirs();
        }
    }

    private class MyUncaughtExceptionHandler implements
            UncaughtExceptionHandler {
        // 异常处理代码
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // 死前一言
            System.out.println("发现一个异常，但是被哥捕获了");
            PrintStream err;
            try {
                err = new PrintStream(new File("/mnt/sdcard/err.txt"));
                ex.printStackTrace(err);
            } catch (Exception e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid()); // 杀死进程，自杀，闪退
            // 防崩设计
        }
    }

    /**
     * 返回LeaderApp对象
     */
    public static AudioApp app() {
        return app;
    }

    public static void postUi(Runnable run) {
        sHandler.post(run);
    }
}
