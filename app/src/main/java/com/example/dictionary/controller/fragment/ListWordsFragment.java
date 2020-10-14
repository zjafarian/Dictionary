package com.example.dictionary.controller.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.IRepository;
import com.example.dictionary.repository.WordsDBRepository;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ListWordsFragment extends Fragment {
    public static final String TAG_ADD_WORD = "addWord";
    public static final int REQUEST_CODE_ADD_WORD = 0;
    public static final int REQUEST_CODE_EDIT_WORD = 1;
    public static final String TAG_EDIT_WORD = "editWord";
    private RecyclerView mRecyclerView;
    private IRepository mWordRepository;
    private List<Word> mWords;
    private boolean mCheck;
    private WordAdapter mWordAdapter;
    private MenuItem itemPrToEn;
    private MenuItem itemEnToPr;
    private Word mWord;
    private long mIdWord;


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
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));


        MenuItem itemSearch = menu.findItem(R.id.app_bar_search);
        MenuItem itemAdd = menu.findItem(R.id.add_word);
        itemPrToEn = menu.findItem(R.id.switch_pr_to_en);
        itemEnToPr = menu.findItem(R.id.switch_en_to_pr);
        if (itemPrToEn.isVisible())
            mCheck = true;
        else if (itemEnToPr.isVisible())
            mCheck = false;

        updateSubtitle();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_word:
                AddWordDialogFragment addWordDialogFragment =
                        AddWordDialogFragment.newInstance(mCheck);
                addWordDialogFragment.setTargetFragment(ListWordsFragment.this,
                        REQUEST_CODE_ADD_WORD);
                addWordDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_ADD_WORD);
                return true;
            case R.id.app_bar_search:
                // TODO:
                return true;
            case R.id.switch_en_to_pr:
                mCheck = false;
                itemEnToPr.setVisible(false);
                itemPrToEn.setVisible(true);
                return true;
            case R.id.switch_pr_to_en:
                mCheck = true;
                itemEnToPr.setVisible(true);
                itemPrToEn.setVisible(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        int numberOfWords = mWordRepository.getWords().size();
        String wordsText = numberOfWords + " words";
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(wordsText);
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
        private Button mButtonShare;
        private Word mWordHolder;

        public WordHolder(@NonNull View itemView) {
            super(itemView);

            findViews(itemView);

            setListener();
        }

        private void setListener() {
            mImgBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditWordFragment editWordFragment =
                            EditWordFragment.newInstance(mWordHolder.getPrimaryId(), mCheck);

                    editWordFragment.setTargetFragment(ListWordsFragment.this,
                            REQUEST_CODE_EDIT_WORD);
                    editWordFragment.show(getActivity().getSupportFragmentManager(),
                            TAG_EDIT_WORD);
                }
            });


            mImgBtnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWordRepository.deleteWord(mWordHolder);
                    mWords = mWordRepository.getWords();
                    initViews();

                }
            });

            mButtonShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareReportIntent();

                }
            });
        }

        private void shareReportIntent() {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getReport());
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.word_share_subject));
            sendIntent.setType("text/plain");

            Intent shareIntent =
                    Intent.createChooser(sendIntent, getString(R.string.send_report));

            //we prevent app from crash if the intent has no destination.
            if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null)
                startActivity(shareIntent);
        }

        private String getReport() {
            String word;
            String meaning;
            String report;
            if (mCheck){
                word = mWord.getWord();
                meaning = mWord.getMeaning();
            } else {
                word = mWord.getMeaning();
                meaning = mWord.getWord();
            }
            report = "Meaning of " + word + " is" + meaning;
            return report;
        }

        private void findViews(@NonNull View itemView) {
            mTextWord = itemView.findViewById(R.id.text_view_word);
            mTextMeaning = itemView.findViewById(R.id.text_view_meaning);
            mImgBtnEdit = itemView.findViewById(R.id.img_btn_edit);
            mImgBtnRemove = itemView.findViewById(R.id.img_btn_remove);
            mButtonShare = itemView.findViewById(R.id.btn_share);
        }

        private void bindWord(Word word) {
            mWordHolder = word;
            if (mCheck) {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_ADD_WORD) {
            mWords = mWordRepository.getWords();
            initViews();
        } else if (requestCode == REQUEST_CODE_EDIT_WORD) {
            mIdWord = data.getLongExtra(EditWordFragment.EXTRA_EDIT_WORD,0);
            for (Word wordFind:mWords) {
                if (wordFind.getPrimaryId()== mIdWord)
                    mWord = wordFind;
            }
            updateWord();
            mWords = mWordRepository.getWords();
            initViews();
        }
    }

    private void updateWord() {
        mWordRepository.updateWord(mWord);
    }



}