package com.liaoyunan.englishapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Quhaofeng on 16-4-29.
 */
public class MyApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return sContext;
    }
}
