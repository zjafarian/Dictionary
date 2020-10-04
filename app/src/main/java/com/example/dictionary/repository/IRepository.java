package com.example.dictionary.repository;

import com.example.dictionary.model.Word;

import java.util.List;

public interface IRepository {
    List<Word> getWords();
    Word getWord(long id);
    void insertWord(Word word);
    void updateWord (Word word);
    void deleteWord (Word word);
}
