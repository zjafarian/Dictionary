package com.example.dictionary.repository;

import android.content.Context;

import com.example.dictionary.model.Word;

import java.util.List;

public class WordsDBRepository implements IRepository {
    private static WordsDBRepository sInstance;
    private Context mContext;

    private WordsDBRepository(Context context) {
        mContext = context.getApplicationContext();



    }

    public static WordsDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new WordsDBRepository(context);

        return sInstance;
    }

    @Override
    public List<Word> getWords() {
        return null;
    }

    @Override
    public Word getWord(long id) {
        return null;
    }

    @Override
    public void insertWord(Word word) {

    }

    @Override
    public void updateWord(Word word) {

    }

    @Override
    public void deleteWord(Word word) {

    }
}
