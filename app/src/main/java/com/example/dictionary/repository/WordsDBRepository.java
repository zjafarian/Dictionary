package com.example.dictionary.repository;

import android.content.Context;

import com.example.dictionary.model.Words;

import java.util.List;

public class WordsDBRepository implements IRepository {
    private static WordsDBRepository sInstance;
    private Context mContext;

    private WordsDBRepository() {

    }

    public static WordsDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new WordsDBRepository(context);

        return sInstance;
    }

    @Override
    public List<Words> getWords() {
        return null;
    }

    @Override
    public Words getWord(long id) {
        return null;
    }

    @Override
    public void insertWord(Words word) {

    }

    @Override
    public void updateWord(Words word) {

    }

    @Override
    public void deleteWord(Words word) {

    }
}
