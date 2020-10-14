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
import com.example.dictionary.database.WordDatabase;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.IRepository;
import com.example.dictionary.repository.WordsDBRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;


public class AddWordDialogFragment extends DialogFragment {
    public static final String ARGS_CHECK_LANGUAGE = "checkLanguage";
    public static final String EXTRA_SEND_ID_WORD = "sendIdWord";
    private IRepository mRepository;
    private TextInputEditText mTextWord;
    private TextInputEditText mTextMeaning;
    private boolean mCheckLanguage;
    private String mWordString;
    private String mMeaningString;
    private Word mWord;


    public AddWordDialogFragment() {
        // Required empty public constructor
    }


    public static AddWordDialogFragment newInstance(boolean check) {
        AddWordDialogFragment fragment = new AddWordDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARGS_CHECK_LANGUAGE, check);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = WordsDBRepository.getInstance(getActivity());
        if (getArguments() != null) {
            mCheckLanguage = getArguments().getBoolean(ARGS_CHECK_LANGUAGE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_add_word_dialog, null);
        setViews(view);
        setListener();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_add_word)
                .setView(view)
                .setPositiveButton(R.string.add_word, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createWord();
                        sendResult();

                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = builder.create();

        return dialog;
    }

    private void sendResult() {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SEND_ID_WORD, mWord.getPrimaryId());
        fragment.onActivityResult(requestCode, resultCode, intent);
    }

    private void createWord() {
        if (mCheckLanguage) {
            mWord= new Word(mWordString, mMeaningString);
            mRepository.insertWord(mWord);
        } else {
            mWord = new Word(mMeaningString, mWordString);
            mRepository.insertWord(mWord);
        }
    }

    private void setListener() {
        mTextWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWordString = mTextWord.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTextMeaning.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMeaningString = mTextMeaning.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setViews(View view) {
        mTextWord = view.findViewById(R.id.add_word);
        mTextMeaning = view.findViewById(R.id.add_meaning);
    }
}