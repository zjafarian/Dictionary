package com.example.dictionary.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.dictionary.database.WordDAO;
import com.example.dictionary.database.WordDatabase;
import com.example.dictionary.model.Word;

import java.util.List;

public class WordsDBRepository implements IRepository {
    private static WordsDBRepository sInstance;
    private Context mContext;
    private WordDAO mWordDAO;

    private WordsDBRepository(Context context) {
        mContext = context.getApplicationContext();

        WordDatabase wordDatabase = Room.databaseBuilder(mContext,
                WordDatabase.class,
                "word.db")
                .allowMainThreadQueries()
                .build();

        mWordDAO = wordDatabase.getWordTable();
    }

    public static WordsDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new WordsDBRepository(context);

        return sInstance;
    }

    @Override
    public List<Word> getWords() {
        return mWordDAO.getWords();
    }

    @Override
    public Word getWord(long id) {
        return mWordDAO.getWord(id);
    }

    @Override
    public void insertWord(Word word) {
        mWordDAO.insertWord(word);

    }

    @Override
    public void updateWord(Word word) {
        mWordDAO.updateWord(word);

    }

    @Override
    public void deleteWord(Word word) {
        mWordDAO.deleteWord(word);
    }
}
