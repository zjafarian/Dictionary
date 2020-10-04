package com.example.dictionary.model;

public class Words {
    private long mPrimaryId;
    private String mWord;
    private String mMeaning;

    public long getPrimaryId() {
        return mPrimaryId;
    }

    public void setPrimaryId(long primaryId) {
        mPrimaryId = primaryId;
    }

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public void setMeaning(String meaning) {
        mMeaning = meaning;
    }
}
