package com.example.dictionary.repository;

import com.example.dictionary.model.Words;

import java.util.List;

public interface IRepository {
    List<Words> getWords();
    Words getWord(long id);
    void insertWord(Words word);
    void updateWord (Words word);
    void deleteWord (Words word);
}
