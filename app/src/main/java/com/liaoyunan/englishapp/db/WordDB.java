package com.liaoyunan.englishapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.liaoyunan.englishapp.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quhaofeng on 16-4-29.
 */
public class WordDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "english_app";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;
    /**
     * 构造函数私有化
     */
    private static WordDB sWordDB;

    private SQLiteDatabase db;

    /**
     * 创建并获取数据库实例
     */
    private WordDB(Context context) {
        WordOpenHelper dbHelper = new WordOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取WordDB实例
     */
    public synchronized static WordDB getInstance(Context context) {
        if (sWordDB == null) {
            sWordDB = new WordDB(context);
        }
        return sWordDB;
    }

    /**
     * 将word实例存储到WordLib表
     */
    public void saveWordLib(Word input) {
        if (input != null) {
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (Word.RECORDSBean word : input.getRECORDS()) {
                    values.put("word", word.getWord());
                    values.put("yinbiao", word.getYinbiao());
                    values.put("meaning", word.getMeaning());
                    db.insert("WordLib", null, values);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.w("TAG", "SaveWordLibErr");
            } finally {
                db.endTransaction();
                Log.w("TAG", "SaveWordLibComplete");
            }
        }
    }

    /**
     * 从词库（WordLib表）获取单词列表
     */
    public List<Word.RECORDSBean> loadWordLib() {
        List<Word.RECORDSBean> list = new ArrayList<Word.RECORDSBean>();
        Cursor cursor = db.query("WordLib", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Word.RECORDSBean word = new Word.RECORDSBean();
                word.setWord(cursor.getString(cursor.getColumnIndex("word")));
                word.setYinbiao(cursor.getString(cursor.getColumnIndex("yinbiao")));
                word.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));
                list.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 将word实例存储到WordCollect表
     */
    public void saveWordCollect(Word input) {
        if (input != null) {
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (Word.RECORDSBean word : input.getRECORDS()) {
                    values.put("word", word.getWord());
                    values.put("yinbiao", word.getYinbiao());
                    values.put("meaning", word.getMeaning());
                    db.insert("WordCollect", null, values);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.w("TAG", "SaveWordCollectErr");
            } finally {
                db.endTransaction();
                Log.w("TAG", "SaveWordCollectComplete");
            }
        }
    }

    /**
     * 从单词本（WordCollect表）获取单词列表
     */
    public List<Word.RECORDSBean> loadWordCelect() {
        List<Word.RECORDSBean> list = new ArrayList<Word.RECORDSBean>();
        Cursor cursor = db.query("WordCollect", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Word.RECORDSBean word = new Word.RECORDSBean();
                word.setWord(cursor.getString(cursor.getColumnIndex("word")));
                word.setYinbiao(cursor.getString(cursor.getColumnIndex("yinbiao")));
                word.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));
                list.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 创建测试数据
     */
    public void createTestData() {
        List<Word.RECORDSBean> list = new ArrayList<Word.RECORDSBean>();
        for (int i = 0; i < 100; i++) {
            Word.RECORDSBean word = new Word.RECORDSBean();
            word.setWord("a" + i);
            word.setYinbiao("yinbiao" + i);
            word.setMeaning("yige" + i);
            list.add(word);
        }
        Word input = new Word();
        input.setRECORDS(list);
        saveWordLib(input);
        saveWordCollect(input);
    }

    /**
     * 存储Index
     */
    public void saveIndex(int index) {
        if (index >= 0) {
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                values.put("myIndex", index);
                db.insert("ViewIndex", null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.w("TAG", "SaveIndexErr");
            } finally {
                db.endTransaction();
                Log.w("TAG", "SveIndecComplete");
            }
        }else {
            ContentValues values = new ContentValues();
            values.put("myIndex", -1);
            db.insert("ViewIndex", null, values);
        }
    }

    /**
     * 获取Index
     */
    public int loadIndex() {
        int index = 0;
        Cursor cursor = db.query("ViewIndex", null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            index = cursor.getInt(cursor.getColumnIndex("myIndex"));
        } else {
            index = -1;
        }
        cursor.close();
        return index;
    }

    public int loadMaxIndex() {
        int index = 0;
        Cursor cursor = db.query("ViewIndex", null, null, null, null, null, "myIndex");
        if (cursor.moveToLast()) {
            index = cursor.getInt(cursor.getColumnIndex("myIndex"));
        }
        cursor.close();
        return index;
    }

    /**
     * 将生词添加到单词本
     */
    public void addToCollect(Word.RECORDSBean word) {
        ContentValues values = new ContentValues();
        values.put("word", word.getWord());
        values.put("yinbiao", word.getYinbiao());
        values.put("meaning", word.getMeaning());
        db.insert("WordCollect", null, values);
        Log.w("TAG", word.toString());
    }

    public void deleteFromCol(String input) {
        Log.w("TAG", input.toString());
        db.delete("WordCollect", "word = ?", new String[]{input});
    }

    public void deleteAll() {
        db.delete("ViewIndex", null, null);
        db.delete("WordLib", null, null);
    }
}