package com.liaoyunan.englishapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.liaoyunan.englishapp.GsonRequest;
import com.liaoyunan.englishapp.R;
import com.liaoyunan.englishapp.db.WordDB;
import com.liaoyunan.englishapp.model.Word;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static RequestQueue mQueue;

    private TextView mTextView;

    private String result = null;

    private String url = "https://raw.githubusercontent.com/mr-wind/EnglishTest/master/CET4.json";

    private String url4 = "https://raw.githubusercontent.com/mr-wind/EnglishTest/master/CET4.json";

    private String url6 = "https://raw.githubusercontent.com/mr-wind/EnglishTest/master/CET6.json";

    private WordDB wordDB;

    private Word myWord;

    private List<Word.RECORDSBean> wordList = new ArrayList<Word.RECORDSBean>();

    private List<Word.RECORDSBean> wordBookList = new ArrayList<Word.RECORDSBean>();

    private Button wordBookBtn;

    private Button wordViewBtn;

    private Button wordTestBtn;

    private Button cet4Btn;

    private Button cet6Btn;

    private TextView learnMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);

        wordDB = WordDB.getInstance(this);

        mTextView = (TextView) findViewById(R.id.text_view);

        wordBookBtn = (Button) findViewById(R.id.word_book_btn);

        wordBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WordBookActivity.class);
                startActivity(intent);
            }
        });

        wordViewBtn = (Button) findViewById(R.id.view_word_btn);

        wordViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LearnWordActivity.class);
                startActivity(intent);
            }
        });

        wordTestBtn = (Button) findViewById(R.id.word_test_btn);

        wordTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WordTestActivity.class);
                startActivity(intent);
            }
        });

        cet4Btn = (Button) findViewById(R.id.get_CET4_btn);
        cet4Btn.setOnClickListener(this);
        cet6Btn = (Button) findViewById(R.id.get_CET6_btn);
        cet6Btn.setOnClickListener(this);

        learnMax = (TextView) findViewById(R.id.learn_max);
        init();
    }

    /**
     * 初始化函数
     */
    public void init() {
        wordList = wordDB.loadWordLib();
        if (wordList.size() <= 0) {
            //wordDB.createTestData();//添加了测试数据
            getWordLib();
        }
        if (wordDB.loadMaxIndex() >= 0){
            learnMax.setText("总共学习了：" + wordDB.loadMaxIndex() + "个单词");
        }
    }

    /**
     * 从网上获取获取单词库
     */
    public void getWordLib() {
        GsonRequest<Word> gsonRequest = new GsonRequest<Word>(url, Word.class,
                new Response.Listener<Word>() {
                    @Override
                    public void onResponse(Word word) {
                        //result = word.getRECORDS().get(3).getMeaning();
                        myWord = word;
                        wordDB.saveWordLib(myWord);
                        mTextView.setText("词库下载成功");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                        mTextView.setText("词库下载出错");
                    }
                });
        if (isNetwork(this)) {
            mQueue.add(gsonRequest);
        } else {
            Toast.makeText(this, "网络连接错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断网络是否连接
     */
    public boolean isNetwork(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.v("error", e.toString());
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        wordDB.deleteAll();
        switch (v.getId()) {
            case R.id.get_CET4_btn:
                url = url4;
                mTextView.setText("正在下载词库。。。");
                break;
            case R.id.get_CET6_btn:
                url = url6;
                mTextView.setText("正在下载词库。。。");
                break;
            default:
                break;
        }
        wordDB.saveIndex(0);
        getWordLib();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (wordDB.loadMaxIndex() >= 0){
            learnMax.setText("总共学习了：" + wordDB.loadMaxIndex() + "个单词");
        }
    }

}
