package com.example.dictionary.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.IRepository;
import com.example.dictionary.repository.WordsDBRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class EditWordFragment extends DialogFragment {


    public static final String ARGS_ID_WORD = "idWord";
    public static final String ARGS_CHECK_LANGUAGE = "checkLanguage";
    public static final String EXTRA_EDIT_WORD = "com.example.dictionary.editWord";
    private IRepository mRepository;
    private List<Word> mWordList;
    private Word mWord;
    private long mIdWord;
    private TextInputEditText mTextWordEdit;
    private TextInputEditText mTextMeaningEdit;
    private boolean mCheckLanguage;
    private String mWordStringEdit;
    private String mMeaningStringEdit;

    public EditWordFragment() {
        // Required empty public constructor
    }


    public static EditWordFragment newInstance(long id, boolean check) {
        EditWordFragment fragment = new EditWordFragment();
        Bundle args = new Bundle();
        args.putLong(ARGS_ID_WORD, id);
        args.putBoolean(ARGS_CHECK_LANGUAGE, check);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = WordsDBRepository.getInstance(getActivity());
        mWordList = mRepository.getWords();
        if (getArguments() != null) {
            mIdWord = getArguments().getLong(ARGS_ID_WORD);
            mCheckLanguage = getArguments().getBoolean(ARGS_CHECK_LANGUAGE);
        }
        for (Word wordFind : mWordList) {
            if (wordFind.getPrimaryId() == mIdWord)
                mWord = wordFind;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_edit_word, null);
        setViews(view);
        initViews();
        setListener();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_edit_word)
                .setView(view)
                .setPositiveButton(R.string.save_edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editWord();
                        updateWord();
                        sendResult();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        AlertDialog dialog = builder.create();

        return dialog;

    }

    private void sendResult() {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_EDIT_WORD, mWord.getPrimaryId());
        fragment.onActivityResult(requestCode, resultCode, intent);


    }

    private void editWord() {
        if (mCheckLanguage) {
            mWord.setWord(mWordStringEdit);
            mWord.setMeaning(mMeaningStringEdit);
        } else {
            mWord.setWord(mMeaningStringEdit);
            mWord.setMeaning(mWordStringEdit);
        }
    }

    private void initViews() {
        if (mCheckLanguage) {
            mTextWordEdit.setText(mWord.getWord());
            mTextMeaningEdit.setText(mWord.getMeaning());
            mWordStringEdit = mWord.getWord();
            mMeaningStringEdit = mWord.getMeaning();
        } else {
            mTextWordEdit.setText(mWord.getMeaning());
            mTextMeaningEdit.setText(mWord.getWord());
            mWordStringEdit = mWord.getMeaning();
            mMeaningStringEdit = mWord.getWord();
        }

    }

    private void setViews(View view) {
        mTextWordEdit = view.findViewById(R.id.edit_word);
        mTextMeaningEdit = view.findViewById(R.id.edit_meaning);
    }

    private void setListener() {

        mTextWordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWordStringEdit = mTextWordEdit.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTextMeaningEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMeaningStringEdit = mTextMeaningEdit.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void updateWord() {
        mRepository.updateWord(mWord);
    }
}