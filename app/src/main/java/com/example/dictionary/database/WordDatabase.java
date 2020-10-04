package com.example.dictionary.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dictionary.model.Word;

@Database(entities = Word.class, version = 1)
public abstract class WordDatabase extends RoomDatabase {

    public abstract WordDAO getWordTable();

}
