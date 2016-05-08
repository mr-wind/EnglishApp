package com.liaoyunan.englishapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.liaoyunan.englishapp.R;

/**
 * Created by Quhaofeng on 16-5-7.
 */
public class CalScoreActivity extends AppCompatActivity {
    private TextView showScore;

    private int score = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_score);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        showScore = (TextView) findViewById(R.id.show_score);
        showScore.setText("本次得分:" + score);
    }
}
