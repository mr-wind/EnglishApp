package com.liaoyunan.englishapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoyunan.englishapp.R;
import com.liaoyunan.englishapp.db.WordDB;
import com.liaoyunan.englishapp.model.Word;
import com.liaoyunan.englishapp.model.WordTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quhaofeng on 16-5-5.
 */
public class WordTestActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 数据库实例
     */
    private WordDB wordDB;

    private List<Word.RECORDSBean> mRECORDSBeanList = new ArrayList<Word.RECORDSBean>();

    private List<WordTest.Test> mTests = new ArrayList<WordTest.Test>();

    private int mIndex;

    /**
     * 计分
     */
    private int score = 0;
    /**
     * 十道题目起始索引，最小0，最大9
     */
    private int testIndex = 0;
    /**
     * 缓存正确选项ID
     */
    private int rightChoose = 0;

    private TextView wordView;

    private TextView chooseA;

    private TextView chooseB;

    private TextView chooseC;

    private TextView chooseD;

    private TextView scoreView;

    private String currectAnswer = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);
        wordDB = WordDB.getInstance(this);
        mRECORDSBeanList = wordDB.loadWordLib();
        mIndex = wordDB.loadIndex();
        chooseA = (TextView) findViewById(R.id.choose_a);
        chooseA.setOnClickListener(this);
        chooseB = (TextView) findViewById(R.id.choose_b);
        chooseB.setOnClickListener(this);
        chooseC = (TextView) findViewById(R.id.choose_c);
        chooseC.setOnClickListener(this);
        chooseD = (TextView) findViewById(R.id.choose_d);
        chooseD.setOnClickListener(this);
        wordView = (TextView) findViewById(R.id.test_word);
        scoreView = (TextView) findViewById(R.id.score_view);
        setScore(score);
        getTest();
    }

    private void setScore(int score) {
        scoreView.setText("Score:" + score);
    }

    /**
     * 获取十道题目
     */
    public void getTest() {
        WordTest wordTest = new WordTest(this, mIndex, mRECORDSBeanList);
        mTests = wordTest.getWordFromLib();
        displayTest(mTests.get(testIndex));
    }

    /**
     * 显示题目
     */
    public void displayTest(WordTest.Test test) {
        setRightChoose();//设置正确答案
        String s = "test";
        switch (rightChoose) {
            case R.id.choose_a:
                s = test.getWord();
                wordView.setText(s);
                s = test.getRight();
                currectAnswer = s;
                chooseA.setText(s);
                s = test.getWrong1();
                chooseB.setText(s);
                s = test.getWrong2();
                chooseC.setText(s);
                s = test.getWrong3();
                chooseD.setText(s);
                break;
            case R.id.choose_b:
                s = test.getWord();
                wordView.setText(s);
                s = test.getWrong1();
                chooseA.setText(s);
                s = test.getRight();
                chooseB.setText(s);
                currectAnswer = s;
                s = test.getWrong2();
                chooseC.setText(s);
                s = test.getWrong3();
                chooseD.setText(s);
                break;
            case R.id.choose_c:
                s = test.getWord();
                wordView.setText(s);
                s = test.getWrong2();
                chooseA.setText(s);
                s = test.getWrong1();
                chooseB.setText(s);
                s = test.getRight();
                chooseC.setText(s);
                currectAnswer = s;
                s = test.getWrong3();
                chooseD.setText(s);
                break;
            case R.id.choose_d:
                s = test.getWord();
                wordView.setText(s);
                s = test.getWrong3();
                chooseA.setText(s);
                s = test.getWrong1();
                chooseB.setText(s);
                s = test.getWrong2();
                chooseC.setText(s);
                s = test.getRight();
                chooseD.setText(s);
                currectAnswer = s;
                break;
            default:
                break;
        }
    }

    private void setRightChoose() {
        double random = Math.random() * 10;
        if (random >= 0 && random < 2.5) {
            rightChoose = R.id.choose_a;
        } else if (random >= 2.5 && random < 5.0) {
            rightChoose = R.id.choose_b;
        } else if (random >= 5.0 && random < 7.5) {
            rightChoose = R.id.choose_c;
        } else if (random >= 7.5 && random < 10.0) {
            rightChoose = R.id.choose_d;
        }
    }

    /**
     * 跳转下一题
     */
    public void nextTest() {
        testIndex = testIndex + 1;
        if (testIndex >= 0 && testIndex < 10) {
            displayTest(mTests.get(testIndex));
        } else {
            Intent intent = new Intent(WordTestActivity.this, CalScoreActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();//答完退出或者跳转
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_a:
                testAnswer(R.id.choose_a);
                break;
            case R.id.choose_b:
                testAnswer(R.id.choose_b);
                break;
            case R.id.choose_c:
                testAnswer(R.id.choose_c);
                break;
            case R.id.choose_d:
                testAnswer(R.id.choose_d);
                break;
            default:
                break;
        }
    }

    private void testAnswer(int choose) {
        if (choose == rightChoose) {
            score += 10;
            setScore(score);
            //Toast.makeText(this, "正确+10",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "正确答案：" + currectAnswer, Toast.LENGTH_SHORT).show();
        }
        nextTest();//下一题
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
