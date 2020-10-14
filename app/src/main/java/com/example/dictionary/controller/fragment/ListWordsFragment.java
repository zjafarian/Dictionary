package com.example.dictionary.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.IRepository;
import com.example.dictionary.repository.WordsDBRepository;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class ListWordsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private IRepository mWordRepository;
    private List<Word> mWords;
    private boolean mCheck;
    private WordAdapter mWordAdapter;


    public ListWordsFragment() {

    }


    public static ListWordsFragment newInstance() {
        ListWordsFragment fragment = new ListWordsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mWordRepository = WordsDBRepository.getInstance(getActivity());
        mWords = mWordRepository.getWords();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_words, container, false);
        findViews(view);
        initViews();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list_menu, menu);
        MenuItem itemSearch = menu.findItem(R.id.app_bar_search);
        MenuItem itemAdd = menu.findItem(R.id.add_word);
        MenuItem itemToEnglish = menu.findItem(R.id.switch_pr_to_en);
        MenuItem itemToPersian = menu.findItem(R.id.switch_en_to_pr);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_word:
                
                return true;
            case R.id.app_bar_search:
                // TODO:
                return true;
            case R.id.switch_en_to_pr:
                //todo
                return true;
            case R.id.switch_pr_to_en:
                //todo
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycle_view_words);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWordAdapter = new WordAdapter(mWords);
        mRecyclerView.setAdapter(mWordAdapter);
    }

    private class WordHolder extends RecyclerView.ViewHolder {

        private MaterialTextView mTextWord;
        private MaterialTextView mTextMeaning;
        private ImageButton mImgBtnEdit;
        private ImageButton mImgBtnRemove;
        private Word mWord;

        public WordHolder(@NonNull View itemView) {
            super(itemView);

            findViews(itemView);

            setListener();


        }

        private void setListener() {
            mImgBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            mImgBtnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        private void findViews(@NonNull View itemView) {
            mTextWord = itemView.findViewById(R.id.text_view_word);
            mTextMeaning = itemView.findViewById(R.id.text_view_meaning);
            mImgBtnEdit = itemView.findViewById(R.id.img_btn_edit);
            mImgBtnRemove = itemView.findViewById(R.id.img_btn_remove);
        }

        private void bindWord(Word word) {
            mWord = word;
            if (!mCheck) {
                mTextWord.setText(word.getWord());
                mTextMeaning.setText(word.getMeaning());
            } else {
                mTextWord.setText(word.getMeaning());
                mTextMeaning.setText(word.getWord());
            }
        }
    }


    private class WordAdapter extends RecyclerView.Adapter<WordHolder> {
        private List<Word> mWordList;

        public WordAdapter(List<Word> words) {
            mWordList = words;
        }


        public List<Word> getWordList() {
            return mWordList;
        }

        public void setWordList(List<Word> wordList) {
            mWordList = wordList;
        }

        @NonNull
        @Override
        public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.word_row_list, parent, false);
            WordHolder wordHolder = new WordHolder(view);
            return wordHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull WordHolder holder, int position) {
            Word word = mWordList.get(position);
            holder.bindWord(word);
        }

        @Override
        public int getItemCount() {
            return mWordList.size();
        }
    }


}