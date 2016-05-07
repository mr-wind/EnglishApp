package com.liaoyunan.englishapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoyunan.englishapp.R;
import com.liaoyunan.englishapp.SlideCutListView;
import com.liaoyunan.englishapp.db.WordDB;
import com.liaoyunan.englishapp.model.Word;

import java.util.ArrayList;
import java.util.List;

import com.liaoyunan.englishapp.SlideCutListView.RemoveListener;
import com.liaoyunan.englishapp.SlideCutListView.RemoveDirection;

/**
 * Created by Quhaofeng on 16-5-1.
 */
public class WordBookActivity extends AppCompatActivity implements RemoveListener {
    private TextView wordView;
    private TextView meaningView;
    private TextView yinbiaoView;
    private SlideCutListView wordListView;
    private List<Word.RECORDSBean> wordList = new ArrayList<Word.RECORDSBean>();
    private List<String> dataList = new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;
    private WordDB wordDB;
    private Button showHideBtn;
    private WebView mWebView;
    private ImageButton add;
    private String readWord = "test";
    /**
     * 单词索引
     */
    private int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_book);

        wordView = (TextView) findViewById(R.id.word);
        meaningView = (TextView) findViewById(R.id.meaning);
        yinbiaoView = (TextView) findViewById(R.id.yinbiao);
        wordListView = (SlideCutListView) findViewById(R.id.word_list);
        add = (ImageButton) findViewById(R.id.add);
        add.setVisibility(View.GONE);
        showHideBtn = (Button) findViewById(R.id.show_hide_btn);
        showHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wordListView.getVisibility() == View.GONE) {
                    wordListView.setVisibility(View.VISIBLE);
                } else {
                    wordListView.setVisibility(View.GONE);
                }
            }
        });
        wordListView.setRemoveListener(this);
        mAdapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.list_item, dataList);
        wordListView.setAdapter(mAdapter);
        wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIndex = position;
                setWord(wordList.get(mIndex));
                wordListView.setVisibility(View.GONE);
            }
        });

        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
            }
        });

        init();
    }

    /**
     * 初始化函数
     */
    public void init() {
        wordDB = WordDB.getInstance(this);
        wordList = wordDB.loadWordCelect();//从单词本表获取单词
        if (wordList.size() <= 0) {
            Toast.makeText(this, "未添加任何单词", Toast.LENGTH_SHORT).show();
            finish();
        }
        dataList.clear();
        for (Word.RECORDSBean recordsBean : wordList) {
            dataList.add(recordsBean.getWord());
        }
        mAdapter.notifyDataSetChanged();
        setWord(wordList.get(mIndex));
    }


    /**
     * 显示单词
     */
    public void setWord(Word.RECORDSBean word) {
        try {
            String s = word.getWord();
            wordView.setText(s);
            readWord = s;
            s = word.getYinbiao();
            yinbiaoView.setText(s);
            s = word.getMeaning();
            meaningView.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下一个单词
     */
    public void nextWord(View view) {
        if (mIndex < wordList.size() - 1) {
            mIndex++;
            setWord(wordList.get(mIndex));
        }

    }

    /**
     * 上一个单词
     */
    public void previousWord(View view) {
        if (mIndex > 0) {
            mIndex--;
            setWord(wordList.get(mIndex));
        }
    }

    /**
     * 播放声音
     */
    public void play(View view) {
        mWebView.loadUrl("http://dict.youdao.com/dictvoice?type=2&audio=" + readWord);
    }

    /**
     * 删除单词
     */
    @Override
    public void removeItem(RemoveDirection direction, int position) {
        switch (direction) {
            case RIGHT:
                wordDB.deleteFromCol(mAdapter.getItem(position));
                break;
            case LEFT:
                wordDB.deleteFromCol(mAdapter.getItem(position));
                break;
            default:
                break;
        }
        mAdapter.remove(mAdapter.getItem(position));
        wordList.remove(wordList.get(position));
        if (wordList.size() <= 0) {
            Toast.makeText(this, "已删除所有单词", Toast.LENGTH_SHORT).show();
            finish();
        }
        setWord(wordList.get(mIndex));
    }
}
