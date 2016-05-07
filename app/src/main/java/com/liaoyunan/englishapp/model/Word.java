package com.liaoyunan.englishapp.model;

import java.util.List;

/**
 * Created by Quhaofeng on 16-4-28.
 */
public class Word {

    /**
     * word : abandon
     * yinbiao : / ə’bændən/
     * meaning :  vt.丢弃；放弃，抛弃
     */

    private List<RECORDSBean> RECORDS;

    public List<RECORDSBean> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<RECORDSBean> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public static class RECORDSBean {
        private String word;
        private String yinbiao;
        private String meaning;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getYinbiao() {
            return yinbiao;
        }

        public void setYinbiao(String yinbiao) {
            this.yinbiao = yinbiao;
        }

        public String getMeaning() {
            return meaning;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }
    }
}
