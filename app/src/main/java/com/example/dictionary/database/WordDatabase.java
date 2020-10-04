package com.example.dictionary.database;

import androidx.room.Database;

import com.example.dictionary.model.Word;

@Database(entities = Word.class, version = 1)
public abstract class WordDatabase {

    public abstract WordDAO getWordTable();

}
