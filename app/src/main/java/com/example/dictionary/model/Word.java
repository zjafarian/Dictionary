package com.example.dictionary.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "WordsTable")
public class Word {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdWord")
    private long mPrimaryId;

    @ColumnInfo(name = "Word")
    private String mWord;

    @ColumnInfo(name = "Meaning")
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
