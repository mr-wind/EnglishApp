package com.liaoyunan.englishapp.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Quhaofeng on 16-5-6.
 */
public class WordTest {
    private List<Word.RECORDSBean> mRECORDSBeanList = new ArrayList<Word.RECORDSBean>();
    private int mIndex = 0;
    /**
     * 缓存10道题的题目列表
     */
    private List<Test> mTestLists = new ArrayList<Test>();

    public WordTest(Context context, int mIndex, List<Word.RECORDSBean> wordlist) {
        mRECORDSBeanList = wordlist;
        this.mIndex = mIndex;
    }

    public List<Test> getWordFromLib() {
        if (mIndex < 10) {
            for (int i = 0; i < 10; i++) {
                Test test = new Test();
                test.setWord(mRECORDSBeanList.get(i).getWord());
                test.setRight(mRECORDSBeanList.get(i).getMeaning());
                get3Worng(test, mIndex);
                mTestLists.add(test);
            }
        } else {
            for (int i = (mIndex - 10); i < mIndex; i++) {
                Test test = new Test();
                test.setWord(mRECORDSBeanList.get(i).getWord());
                test.setRight(mRECORDSBeanList.get(i).getMeaning());
                get3Worng(test, mIndex);
                mTestLists.add(test);
            }
        }
        return mTestLists;
    }

    /**
     * 生成3个错误答案
     */
    public Test get3Worng(Test test, int mIndex) {
        String wrong1 = getWrong(mIndex);
        while (wrong1.equals(test.getRight())) {
            wrong1 = getWrong(mIndex);
        }
        test.setWrong1(wrong1);

        String wrong2 = getWrong(mIndex);
        while (wrong2.equals(test.getRight()) || wrong2.equals(test.getWrong1())) {
            wrong2 = getWrong(mIndex);
        }
        test.setWrong2(wrong2);

        String wrong3 = getWrong(mIndex);
        while (wrong3.equals(test.getRight()) || wrong3.equals(test.getWrong1()) || wrong3.equals(test.getWrong2())) {
            wrong3 = getWrong(mIndex);
        }
        test.setWrong3(wrong3);
        return test;
    }

    public String getWrong(int mIndex) {
        String wrong = "";
        int randomNum;
        Random random = new Random();
        if (mIndex < 10) {
            randomNum = random.nextInt(10);
        } else {
            randomNum = random.nextInt(10) + mIndex - 10;
        }
        wrong = mRECORDSBeanList.get(randomNum).getMeaning();
        return wrong;
    }

    @Override
    public String toString() {
        return "WordTest{" +
                "mTestLists=" + mTestLists +
                '}';
    }

    public class Test {
        private String word;
        private String right;
        private String wrong1;
        private String wrong2;
        private String wrong3;

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getWrong1() {
            return wrong1;
        }

        public void setWrong1(String wrong1) {
            this.wrong1 = wrong1;
        }

        public String getWrong2() {
            return wrong2;
        }

        public void setWrong2(String wrong2) {
            this.wrong2 = wrong2;
        }

        public String getWrong3() {
            return wrong3;
        }

        public void setWrong3(String wrong3) {
            this.wrong3 = wrong3;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "right='" + right + '\'' +
                    ", word='" + word + '\'' +
                    ", wrong1='" + wrong1 + '\'' +
                    ", wrong2='" + wrong2 + '\'' +
                    ", wrong3='" + wrong3 + '\'' +
                    '}';
        }
    }
}
