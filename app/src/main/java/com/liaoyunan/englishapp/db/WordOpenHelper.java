package com.liaoyunan.englishapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Quhaofeng on 16-4-29.
 */
public class WordOpenHelper extends SQLiteOpenHelper {

    /**
     * WordLib--词库表建表语句
     */
    public static final String CREATE_WORD_LIB = "create table WordLib (" +
            "ID integer primary key autoincrement," +
            "word text," +
            "yinbiao text," +
            "meaning text)";
    /**
     * WordCollect表建表语句
     */
    public static final String CREATE_WORD_COLLECT = "create table WordCollect (" +
            "ID integer primary key autoincrement," +
            "word text," +
            "yinbiao text," +
            "meaning text)";

    /**
     * 创建存储Index的表，用于保存浏览位置
     */
    public static final String CREATE_VIEW_INDEX = "create table ViewIndex (" +
            "ID integer primary key autoincrement," +
            "myIndex integer)";

    public WordOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORD_LIB);//创建WordLib表
        db.execSQL(CREATE_WORD_COLLECT);//创建WordCollet表
        db.execSQL(CREATE_VIEW_INDEX);//创建ViewIndex表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
