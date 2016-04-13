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

    public static String rootPath = "/Audio/";
    public static String lrcPath = "/Audio/lrc";
    public static String errorPath = "/Audio/error.txt";

    @Override
    public void onCreate() {
        app = this;
        super.onCreate();
        Log.e("AudioApp", "app创建");

        // 初始化 存储路径
        initPath();
        // 设置异常的处理类
        Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }

    /**
     * 初始化存储路径
     */
    private void initPath() {
        String ROOT = "";// /storage/emulated/0
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ROOT = Environment.getExternalStorageDirectory().getPath();
            Log.e("11", "系统方法：" + ROOT);
            //  ROOT = StorageUtils.getSdcardPath(this);
        }
        rootPath = ROOT + rootPath;
        lrcPath = ROOT + lrcPath;
        errorPath = ROOT + errorPath;

        File lrcFile = new File(lrcPath);
        if (!lrcFile.exists()) {
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
                //  err = new PrintStream(new File("/mnt/sdcard/err.txt"));
                err = new PrintStream(new File(errorPath));
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

}
